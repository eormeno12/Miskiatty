package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.enum.ErrorsEnum
import com.listen.to.miskiatty.model.provider.ErrorsStringProvider
import com.listen.to.miskiatty.model.validation.DataValidation

class PinLoginRepositoryImpl: PinLoginRepository {

    private val errorsStringProvider = ErrorsStringProvider()

    override fun pinValidation(tl_pin: TextInputLayout): Boolean =
        DataValidation().validatePin(tl_pin)

    override fun getPinInputError(context: Context, pin: String): String {
        return when {
            pin.isEmpty() -> {
                errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.EMPTY_FIELD)
            }
            else -> {
                errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.INCORRECT_PIN)
            }
        }
    }
}