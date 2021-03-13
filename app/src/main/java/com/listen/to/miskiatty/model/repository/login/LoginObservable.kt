package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import androidx.databinding.BaseObservable
import com.google.android.material.textfield.TextInputLayout

class LoginObservable: BaseObservable(){

    private val loginRepository = LoginRepositoryImpl()

    fun loginValidation(tl_email: TextInputLayout, tl_password: TextInputLayout): Boolean {
        val emailResult = validateEmail(tl_email)
        val passwordResult = validatePassword(tl_password)
        val appContext = tl_email.context.applicationContext

        tl_email.isErrorEnabled = false
        tl_email.isErrorEnabled = false

        if (!emailResult) {
            tl_email.isErrorEnabled = true
            tl_email.error = getEmailInputError(
                    appContext,
                    tl_email.editText?.text.toString())
        }

        if (!passwordResult) {
            tl_password.isErrorEnabled = true
            tl_password.error = getPasswordInputError(
                    appContext,
                    tl_password.editText?.text.toString())
        }

        return (emailResult && passwordResult)
    }

    private fun validateEmail(tl_email: TextInputLayout): Boolean =
        loginRepository.emailValidation(tl_email)

    private fun validatePassword(tl_password: TextInputLayout):  Boolean =
        loginRepository.passwordValidation(tl_password)

    private fun getEmailInputError(context: Context, email: String): String =
        loginRepository
        .getEmailInputError(context, email)

    private fun getPasswordInputError(context: Context, password: String):  String  =
        loginRepository
            .getPasswordInputError(context, password)
}