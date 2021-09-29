package com.listen.to.miskiatty.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.repository.login.LoginObservable

class LoginViewModel: ViewModel() {

    private var loginObservable = LoginObservable()
    private var _loginClicked = MutableLiveData<Boolean>()
    val loginClicked: LiveData<Boolean> = _loginClicked

    fun onClickLoginListener(tl_email: TextInputLayout, tl_password: TextInputLayout){
        _loginClicked.value = true
    }

    fun getLoginErrors(tl_email: TextInputLayout, tl_password: TextInputLayout){
        loginObservable.loginErrors(tl_email, tl_password)
    }
}