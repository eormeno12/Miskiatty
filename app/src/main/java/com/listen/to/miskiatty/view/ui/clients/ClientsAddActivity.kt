package com.listen.to.miskiatty.view.ui.clients

import android.app.SearchManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityClientsAddBinding
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.network.storage.FireStorageService
import com.listen.to.miskiatty.model.repository.clients.Contact
import com.listen.to.miskiatty.viewmodel.CallbackFireStorage
import com.listen.to.miskiatty.viewmodel.clients.ClientsAddViewModel

class ClientsAddActivity : AppCompatActivity() {

    private var clientsAddViewModel: ClientsAddViewModel? = null
    private lateinit var binding: ActivityClientsAddBinding
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients_add)

        setUpBinding()
        setUpToolbar()
        setRecyclerClientsAdapters()
        setUpListsUpdate()
    }

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = this
        callClients()
    }

    private fun setUpBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_clients_add)

        clientsAddViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(ClientsAddViewModel::class.java)

        binding.clientAddViewModel = clientsAddViewModel
    }

    private fun setUpToolbar(){
        toolbar = binding.toolbarClientsAdd
        setSupportActionBar(toolbar)

        toolbarNavigationOnClickListener()
    }

    private fun toolbarNavigationOnClickListener(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerClientsAdapters() {
        clientsAddViewModel?.setRecyclerContactsAdapter()
    }

    private fun setUpListsUpdate() {
        clientsAddViewModel?.getContacts()?.observe(this, { contacts: List<Contact> ->
            clientsAddViewModel?.setContactsInRecyclerAdapter(
                clientsAddViewModel?.getRecyclerContactsAdapter(),
                contacts
            )
        })
    }

    private fun callClients() = clientsAddViewModel?.callContacts(this, this.lifecycle)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_clients_add, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_contact)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar Contacto"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(contact: String?): Boolean {
                if (contact != null) clientsAddViewModel?.searchContact(contact)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.confirm_clients -> {
                val checkedContacts = clientsAddViewModel?.getCheckedContacts()

                if (checkedContacts != null && checkedContacts.isNotEmpty()) {
                    val clientsToAdd = ArrayList<Client>()
                    val imageUri = "android.resource://com.listen.to.miskiatty/drawable/" + R.drawable.default_client_96

                    FireStorageService().uploadFileFromUri(Uri.parse(imageUri),
                        object: CallbackFireStorage<String> {
                            override fun onSucces(result: String?) {
                                if(result != null){
                                    for (contact in checkedContacts) {
                                        val newClient = Client(
                                            name = contact.name,
                                            phone = contact.phone,
                                            image = result,
                                            address = "",
                                            orders = ArrayList()
                                        )

                                        clientsToAdd.add(newClient)
                                        clientsAddViewModel?.addClientsRoom(this@ClientsAddActivity, lifecycle, clientsToAdd)
                                        onBackPressed()
                                        finish()
                                    }

                                    binding.progressCircular.visibility = View.INVISIBLE
                                }
                            }

                            override fun onProgress(progress: Double) {
                                binding.progressCircular.visibility = View.VISIBLE
                            }

                            override fun onFailure(e: Exception) {
                                binding.progressCircular.visibility = View.INVISIBLE
                            }

                        })
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}