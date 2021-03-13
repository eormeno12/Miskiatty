package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import androidx.databinding.BaseObservable
import com.google.android.material.textfield.TextInputLayout

class PinLoginObservable: BaseObservable() {

    private val pinLoginRepository = PinLoginRepositoryImpl()

    fun validatePin(tl_pin: TextInputLayout): Boolean{
        val result = pinLoginRepository.pinValidation(tl_pin)

        tl_pin.isErrorEnabled = false
        if(!result){
            tl_pin.isErrorEnabled = true
            tl_pin.error = getPinInputError(
                    tl_pin.context.applicationContext,
                    tl_pin.editText?.text.toString()
            )
        }

        return result
    }

    private fun getPinInputError(context: Context, pin: String): String =
        pinLoginRepository.getPinInputError(context, pin)
}