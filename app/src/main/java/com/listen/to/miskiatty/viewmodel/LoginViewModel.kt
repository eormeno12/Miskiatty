package com.listen.to.miskiatty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.observables.LoginObservable

class LoginViewModel: ViewModel() {

    private var loginObservable = LoginObservable()
    private var _successLogin = MutableLiveData<Boolean>()
    val successLogin: LiveData<Boolean> = _successLogin

    fun onClickLoginListener(user: String, password: String){
        _successLogin.value = loginObservable.userLogin(user, password)
    }

}