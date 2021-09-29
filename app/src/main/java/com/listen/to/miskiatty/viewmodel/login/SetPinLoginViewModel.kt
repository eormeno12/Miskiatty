package com.listen.to.miskiatty.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.repository.login.SetPinLoginObservable

class SetPinLoginViewModel: ViewModel() {
    private var _validPin = MutableLiveData<Boolean>()
    val validPin: LiveData<Boolean> = _validPin

    lateinit var pin: String

    fun onClickPinLoginListener(tl_pin: TextInputLayout, tl_confirmPin: TextInputLayout){
        pin = tl_pin.editText?.text.toString()
        _validPin.value = validatePin(tl_pin, tl_confirmPin)
    }

    private fun validatePin(tl_pin: TextInputLayout, tl_confirmPin: TextInputLayout): Boolean =
        SetPinLoginObservable().validatePins(tl_pin, tl_confirmPin)
}