package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.enum.ErrorsEnum
import com.listen.to.miskiatty.model.provider.ErrorsStringProvider
import com.listen.to.miskiatty.model.provider.PreferenceProvider
import com.listen.to.miskiatty.model.validation.DataValidation
import java.util.regex.Pattern

class LoginRepositoryImpl(): LoginRepository {

    private val errorsStringProvider = ErrorsStringProvider()

    override fun getEmailInputError(context: Context, email: String): String {
        return  when{
            email.isEmpty() -> errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.EMPTY_FIELD)

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> errorsStringProvider
                    .getErrorString(
                            context,
                            ErrorsEnum.INVALID_EMAIL)

            else -> errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.INCORRECT_EMAIL)
        }
    }


    override fun getPasswordInputError(context: Context, password: String): String {
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^^+=])(?=.*[\\S+$]).{8,}$"
        val PASSWORD_PATTERN: Pattern = Pattern.compile(pattern)

        return when {
            password.isEmpty() -> errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.EMPTY_FIELD)
            !PASSWORD_PATTERN.matcher(password).matches() -> errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.INVALID_PASSWORD)
            else -> errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.INCORRECT_PASSWORD)
        }
    }
}