package com.listen.to.miskiatty.model.provider

import android.content.Context
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.enum.ErrorsEnum

class ErrorsStringProvider {

    fun getErrorString(context: Context, error: ErrorsEnum): String{
        return when (error){
            ErrorsEnum.EMPTY_FIELD -> context.getString(R.string.EMPTY_FIELD)
            ErrorsEnum.INVALID_EMAIL -> context.getString(R.string.INVALID_EMAIL)
            ErrorsEnum.INCORRECT_EMAIL -> context.getString(R.string.INCORRECT_EMAIL)
            ErrorsEnum.INVALID_PASSWORD -> context.getString(R.string.INVALID_PASSWORD)
            ErrorsEnum.INCORRECT_PASSWORD -> context.getString(R.string.INCORRECT_PASSWORD)
            ErrorsEnum.INVALID_PIN -> context.getString(R.string.INVALID_PIN)
            ErrorsEnum.INCORRECT_PIN -> context.getString(R.string.INCORRECT_PIN)
            ErrorsEnum.DIFFERENT_PINS -> context.getString(R.string.DIFFERENT_PINS)
        }
    }
}