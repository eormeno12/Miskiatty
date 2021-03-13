package com.listen.to.miskiatty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.repository.login.LoginObservable

class LoginViewModel: ViewModel() {

    private var _successLogin = MutableLiveData<Boolean>()
    val successLogin: LiveData<Boolean> = _successLogin

    fun onClickLoginListener(tl_email: TextInputLayout, tl_password: TextInputLayout){
        _successLogin.value = LoginObservable().loginValidation(tl_email, tl_password)
    }

}