package com.listen.to.miskiatty.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.repository.login.PinLoginObservable

class PinLoginViewModel: ViewModel() {

    private var _pinValid = MutableLiveData<Boolean>()
    val pinValid: LiveData<Boolean> = _pinValid

    fun onClickPinLoginListener(tl_pin: TextInputLayout){
        _pinValid.value = PinLoginObservable().validatePin(tl_pin)
    }
}