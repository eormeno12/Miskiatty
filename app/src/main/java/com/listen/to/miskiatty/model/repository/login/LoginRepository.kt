package com.listen.to.miskiatty.model.repository.login

import android.content.Context

interface LoginRepository {
    fun getEmailInputError(context: Context, email: String): String
    fun getPasswordInputError(context: Context, password: String):  String
}