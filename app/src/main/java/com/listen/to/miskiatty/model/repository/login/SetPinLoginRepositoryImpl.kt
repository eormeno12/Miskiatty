package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.enum.ErrorsEnum
import com.listen.to.miskiatty.model.provider.ErrorsStringProvider
import com.listen.to.miskiatty.model.validation.DataValidation

class SetPinLoginRepositoryImpl: SetPinLoginRepository {

    private val errorsStringProvider = ErrorsStringProvider()

    override fun pinsValidation(
        tl_pin: TextInputLayout,
        tl_confirmPin: TextInputLayout
    ): Boolean = DataValidation().validatePinAndConfirmed(tl_pin, tl_confirmPin)

    override fun getPinsInputError(context: Context, pin: String, confrimPin: String): String {
        return when {
            pin.isEmpty() -> {
                errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.EMPTY_FIELD)
            }
            pin != confrimPin -> {
                errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.DIFFERENT_PINS)
            }
            else -> {
                errorsStringProvider.getErrorString(
                    context,
                    ErrorsEnum.INVALID_PIN)
            }
        }
    }
}