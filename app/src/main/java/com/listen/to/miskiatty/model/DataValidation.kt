package com.listen.to.miskiatty.model

import java.util.regex.Pattern

class DataValidation {

    fun validateLogin(email: String, password: String): Boolean = (validateEmail(email) &&
                                                                    validatePassword(password))


    private fun validateEmail(email: String): Boolean{

        if(email.isEmpty()){
            return false
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false
        }

        return true
    }

    private fun validatePassword(password: String): Boolean{
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^^+=])(?=.*[\\S+$]).{8,}$"
        val PASSWORD_PATTERN: Pattern = Pattern.compile(pattern)

        if(password.isEmpty()){
            return false
        }else if(!PASSWORD_PATTERN.matcher(password).matches()){
            return false
        }

        return true
    }
}