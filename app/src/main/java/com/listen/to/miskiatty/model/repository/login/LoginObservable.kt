package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import androidx.databinding.BaseObservable
import com.google.android.material.textfield.TextInputLayout

class LoginObservable: BaseObservable(){

    private val loginRepository = LoginRepositoryImpl()

    fun loginErrors(tl_email: TextInputLayout, tl_password: TextInputLayout) {
        val appContext = tl_email.context.applicationContext

        tl_email.isErrorEnabled = true
        tl_email.error = getEmailInputError(
                appContext,
                tl_email.editText?.text.toString())

        tl_password.isErrorEnabled = true
        tl_password.error = getPasswordInputError(
                appContext,
                tl_password.editText?.text.toString())

    }

    private fun getEmailInputError(context: Context, email: String): String =
        loginRepository
        .getEmailInputError(context, email)

    private fun getPasswordInputError(context: Context, password: String):  String  =
        loginRepository
            .getPasswordInputError(context, password)
}