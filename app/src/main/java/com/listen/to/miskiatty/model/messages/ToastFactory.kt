package com.listen.to.wave.view.message

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.messages.ErrorsEnum

class ToastFactory() {
    fun makeSuccessToast(context: Context, successesEnum: SuccessesEnum): Toast{
        val message: String = when(successesEnum){
            SuccessesEnum.SIGNIN_SUCCESS -> {
                "Cuenta creada correctamente."
            }
        }

        return Toast.makeText(context, message, Toast.LENGTH_LONG)
    }

    fun displayErrorToast(context: Context, errorsEnum: ErrorsEnum){
        val message: String = when(errorsEnum){
            ErrorsEnum.EMPTY_TEXT_FIELD -> {
                "Por favor, complete todos los campos."
            }

            ErrorsEnum.SAVE_ERROR -> {
                "Ocurrió un error al guardar el producto."
            }
        }

        createCustomToast(context, message, R.id.toast_container, R.layout.template_toast_error, R.id.textToast)
    }

    private fun createCustomToast(context: Context, message:String, container:Int, template:Int, textField:Int){
        val activity = context as Activity

        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val containerToast = activity.findViewById(container) as? ViewGroup
        val layout = inflater.inflate(template, containerToast) as? ViewGroup
        val text = layout?.findViewById(textField) as? TextView

        text?.text = message
        with (Toast(context)) {
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }
}