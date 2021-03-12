package com.listen.to.miskiatty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.observables.PinLoginObservable

class PinLoginViewModel: ViewModel() {

    private var _successPinLogin = MutableLiveData<Boolean>()
    val successPinLogin = _successPinLogin

    fun onClickPinLoginListener(pin: String){
        _successPinLogin.value = PinLoginObservable().pinLogin(pin)
    }
}