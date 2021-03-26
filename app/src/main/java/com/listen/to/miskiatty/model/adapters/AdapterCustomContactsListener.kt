package com.listen.to.miskiatty.model.adapters

import com.listen.to.miskiatty.model.repository.clients.Contact

interface AdapterCustomContactsListener {
    fun onClickListenerCheckBox(contact: Contact)
}