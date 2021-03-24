package com.listen.to.miskiatty.view.ui.products

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.FragmentProductsBinding
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.viewmodel.ProductViewModel

class ProductsFragment : Fragment() {

    private var productViewModel: ProductViewModel? = null
    private lateinit var binding: FragmentProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpBinding(inflater, container)
        return  binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerProductsAdapter()
        setUpListUpdate()
        setUpOnClickProduct()
    }

    override fun onStart() {
        super.onStart()
        callProducts()
    }

    private fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil
            .inflate(
                inflater,
                R.layout.fragment_products,
                container,
                false
            )

        productViewModel = ViewModelProvider
            .NewInstanceFactory()
            .create(ProductViewModel::class.java)
        binding.productViewModel = productViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, infater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_products, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search_product)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = "Buscar producto"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(product: String?): Boolean {
                if (product != null) productViewModel?.searchProduct(product)
                return true
            }
        })
    }

    private fun setRecyclerProductsAdapter(){
        productViewModel?.setRecyclerProductsAdapter()
    }

    private fun setUpListUpdate(){
        productViewModel?.getProducts()?.observe(viewLifecycleOwner, {
            products: List<Product> ->
            Log.d("products", products.toString())
            productViewModel?.setProductsInRecyclerAdapter(products)
        })
    }

    private fun callProducts(){
        this.context?.let {
            productViewModel?.callProducts(it, lifecycle)
        }
    }

    private fun setUpOnClickProduct(){
        productViewModel?.productClicked?.observe(viewLifecycleOwner, {
            startActivity(Intent(
                    this.activity?.applicationContext,
                    ProductDetailsActivity::class.java)
                    .apply {
                        putExtra("com.listen.to.miskiatty.view.ui.products.ID", it.id)
                    })
        })
    }
}