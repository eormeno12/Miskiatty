package com.listen.to.miskiatty.view.ui.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Position
import com.anychart.graphics.vector.Anchor
import com.anychart.palettes.RangeColors
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.FragmentStatisticsBinding
import com.listen.to.miskiatty.viewmodel.StatisticsViewModel
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class StatisticsFragment : Fragment() {

    private var statisticsViewModel: StatisticsViewModel? = null
    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setUpBinding(inflater, container)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWeekProfit()
        setUpDailyEarningsChart()
        setUpMostSoldProductsChart()
        setUpClientsRetentionChart()
    }

    override fun onStart() {
        super.onStart()
        callData()
    }

    private fun callData(){
        statisticsViewModel?.let{ vm ->
            context?.applicationContext?.let {
                vm.callOrders(it, lifecycle)
                vm.callClients(it, lifecycle)
            }
        }
    }

    private fun setWeekProfit(){
        statisticsViewModel?.getOrders()?.observe(viewLifecycleOwner, { orders ->
            if(orders != null){
                var weekProfit = 0.0
                val dateTime = DateTime.now()
                val format = DateTimeFormat.forPattern("dd/MM/yyyy");
                val currentDate = format.parseLocalDate(format.print(dateTime))

                Log.d("date", currentDate.toString())
                for (order in orders){
                    val orderDate = order.deliveryDate.split(" ").chunked(2)[0][0]
                    val date = format.parseLocalDate(orderDate)
                    Log.d("date", date.toString())

                    Log.d("days", Days.daysBetween(currentDate, date).toString())
                    if(Days.daysBetween(date, currentDate) <= Days.SEVEN && order.state == "Entregado"){
                        weekProfit += order.profit
                    }
                }

                Log.d("Calendar", Calendar.WEEK_OF_MONTH.toString())
                binding.tvWeekEarnings.text = "S/.$weekProfit"
                binding.executePendingBindings()
            }
        })
    }

    private fun setUpDailyEarningsChart() {
        statisticsViewModel?.getOrders()?.observe(viewLifecycleOwner, { orders ->
            if(orders != null){
                val chart = binding.dailyEarningsChart
                APIlib.getInstance().setActiveAnyChartView(chart)
                chart.setProgressBar(binding.dailyEarningsChartBar)

                val cartesian = AnyChart.column()
                val dataEntries: ArrayList<DataEntry> = ArrayList()

                for (order in orders)
                    if(order.state == "Entregado"){
                        val date = order.deliveryDate.split(" ").chunked(2)[0][0]
                        dataEntries.add(ValueDataEntry(date, order.totalPrice))
                    }


                cartesian.data(dataEntries)

                val column = cartesian.column(dataEntries)

                column.tooltip()
                    .titleFormat("{%X}")
                    .position(Position.CENTER_BOTTOM)
                    .anchor(Anchor.CENTER_BOTTOM.toString())
                    .offsetX(0.0)
                    .offsetY(5.0)
                    .format("\${%Value}{groupsSeparator: }")

                val palette = RangeColors.instantiate()
                palette.items("#f48fb1", "#ffc1e3")
                palette.count(10)

                cartesian.apply {
                    animation(true)
                    title("Ingresos Diarios")
                    data(dataEntries)
                    xAxis(0).title("Fecha")
                    yAxis(0).title("Monto")
                    palette(palette)
                }

                Log.d("Chart", cartesian.toString())
                chart.setChart(cartesian)
                binding.executePendingBindings()
            }
        })
    }

    private fun setUpMostSoldProductsChart() {
        statisticsViewModel?.getOrders()?.observe(viewLifecycleOwner, { orders ->
            if(orders != null){
                val chart = binding.mostSoldProductsChart
                APIlib.getInstance().setActiveAnyChartView(chart)
                chart.setProgressBar(binding.mostSoldProductsChartBar)

                val pie = AnyChart.pie()
                val dataEntries: ArrayList<DataEntry> = ArrayList()

                val productsIdHashMap = HashMap<Int, Int>()

                for(order in orders)
                    for(id in order.products){
                        if(productsIdHashMap.containsKey(id))
                            productsIdHashMap[id] =
                                productsIdHashMap[id]?.plus(
                                    order.productsQuantity[order.products.indexOf(id)]
                                )!!.toInt()
                        else
                            productsIdHashMap[id] = order.productsQuantity[order.products.indexOf(id)]
                    }

                statisticsViewModel?.callProductsById(this.requireContext(), lifecycle, productsIdHashMap.keys.toList())

                statisticsViewModel?.getProductsById()?.observe(viewLifecycleOwner, { products ->
                    if(products != null){
                        for (product in products)
                            dataEntries.add(ValueDataEntry(
                                product.name,
                                productsIdHashMap[product.id])
                            )

                        val palette = RangeColors.instantiate()
                        palette.items("#f48fb1", "#ffc1e3")
                        palette.count(10)

                        pie.apply {
                            animation(true)
                            title("Productos Más Vendidos")
                            data(dataEntries)
                            palette(palette)
                        }

                        chart.setChart(pie)
                        binding.executePendingBindings()
                    }
                })
            }
        })
    }

    private fun setUpClientsRetentionChart() {
        statisticsViewModel?.getClients()?.observe(viewLifecycleOwner, { clients ->
            if(clients != null){
                val chart = binding.clientsRetentionChart
                APIlib.getInstance().setActiveAnyChartView(chart)
                chart.setProgressBar(binding.clientsRetentionChartBar)

                val cartesian = AnyChart.column()
                val dataEntries: ArrayList<DataEntry> = ArrayList()

                for (client in clients)
                    dataEntries.add(ValueDataEntry(client.name, client.orders.count()))

                val column = cartesian.column(dataEntries)

                column.tooltip()
                    .titleFormat("{%X}")
                    .position(Position.CENTER_BOTTOM)
                    .anchor(Anchor.CENTER_BOTTOM.toString())
                    .offsetX(0.0)
                    .offsetY(5.0)
                    .format("\${%Value}{groupsSeparator: }")

                val palette = RangeColors.instantiate()
                palette.items("#f48fb1", "#ffc1e3")
                palette.count(10)

                cartesian.apply {
                    animation(true)
                    title("Retención de Clientes")
                    data(dataEntries)
                    xAxis(0).title("Cliente")
                    yAxis(0).title("# de Pedidos")
                    yScale().ticks().interval(1)
                    palette(palette)
                }

                chart.setChart(cartesian)
                binding.executePendingBindings()
            }
        })
    }

    private fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil
            .inflate(
                    inflater,
                    R.layout.fragment_statistics,
                    container,
                    false
            )

        statisticsViewModel = ViewModelProvider
            .NewInstanceFactory()
            .create(StatisticsViewModel::class.java)
        binding.statisticsViewModel = statisticsViewModel
    }
}