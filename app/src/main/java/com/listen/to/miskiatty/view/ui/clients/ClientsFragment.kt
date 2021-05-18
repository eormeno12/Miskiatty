package com.listen.to.miskiatty.view.ui.clients

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.FragmentClientsBinding
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.viewmodel.ClientViewModel

class ClientsFragment : Fragment() {

    private var clientViewModel: ClientViewModel? = null
    private lateinit var binding: FragmentClientsBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setUpBinding(inflater, container)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerClientsAdapters()
        setUpListsUpdate()
        setUpOnClickProduct()
        setUpRefresh()
    }

    override fun onStart() {
        super.onStart()
        callClients()
    }

    private fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil
                .inflate(
                        inflater,
                        R.layout.fragment_clients,
                        container,
                        false
                )

        clientViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(ClientViewModel::class.java)

        binding.clientViewModel = clientViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, infater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_clients, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search_client)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = "Buscar Cliente"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(product: String?): Boolean {
                if (product != null) clientViewModel?.searchClient(product)
                return true
            }
        })
    }

    private fun setRecyclerClientsAdapters() {
        clientViewModel?.setRecyclerTopClientsAdapter()
        clientViewModel?.setRecyclerClientsAdapter()
    }

    private fun setUpListsUpdate() {
        clientViewModel?.getTopClients()?.observe(viewLifecycleOwner, { clients: List<Client> ->
            var topClients = ArrayList<Client>()

            if(clients.count() > 3)
                topClients = arrayListOf(clients[0], clients[1], clients[2])
            else
                topClients.addAll(clients)

            clientViewModel?.setClientsInRecyclerAdapter(
                    clientViewModel?.getRecyclerTopClientsAdapter(),
                    topClients)
        })

        clientViewModel?.getClients()?.observe(viewLifecycleOwner, { clients: List<Client> ->
            clientViewModel?.setClientsInRecyclerAdapter(
                    clientViewModel?.getRecyclerClientsAdapter(),
                    clients)
            binding.rvRefresh.isRefreshing = false
        })
    }

    private fun callClients() {
        this.context?.let {
            clientViewModel?.callTopClients(it, lifecycle)
            clientViewModel?.callClients(it, lifecycle)
        }
    }

    private fun setUpRefresh(){
        binding.rvRefresh.apply {
            setColorSchemeResources(
                    R.color.colorPrimary,
                    R.color.colorAccent,
                    R.color.colorPrimaryDark)

            setOnRefreshListener {
                callClients()
            }
        }
    }

    private fun setUpOnClickProduct() {
        fun startActivityWithExtra(extra: Int)= startActivity(
                Intent(this.activity?.applicationContext,
                        ClientDetailsActivity::class.java).apply {
                    putExtra("com.listen.to.miskiatty.view.ui.clients.ID", extra)}
        )

        clientViewModel?.topClientClicked?.observe(viewLifecycleOwner, {
           startActivityWithExtra(it.id)
        })

        clientViewModel?.clientClicked?.observe(viewLifecycleOwner, {
            startActivityWithExtra(it.id)
        })
    }
}