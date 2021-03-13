package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import com.google.android.material.textfield.TextInputLayout

class SetPinLoginObservable {

    private val setPinLoginRepository = SetPinLoginRepositoryImpl()

    fun validatePins(tl_pin: TextInputLayout, tl_confirmPin: TextInputLayout): Boolean{
        val result = setPinLoginRepository.pinsValidation(tl_pin, tl_confirmPin)

        tl_pin.isErrorEnabled = false
        if (!result){
            tl_pin.isErrorEnabled = true
            tl_pin.error = getPinInputError(tl_pin.context.applicationContext,
                    tl_pin.editText?.text.toString(),
                    tl_confirmPin.editText?.text.toString())
        }

        return result
    }


    private fun getPinInputError(context: Context, pin: String, confirmPin: String): String =
        setPinLoginRepository.getPinsInputError(context, pin, confirmPin)
}