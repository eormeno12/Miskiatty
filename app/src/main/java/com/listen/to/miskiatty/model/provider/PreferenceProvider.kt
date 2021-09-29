package com.listen.to.miskiatty.model.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.listen.to.miskiatty.model.utils.edit

private const val KEY_LOGIN_SP = "loginPref"
private const val KEY_LOGIN_CREDENTIALS_SP = "loginCredentialsPref"

class PreferenceProvider(context: Context) {

    private val appContext: Context = context.applicationContext
    private val mainKey = MasterKey.Builder(appContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    private val loginPreferences: SharedPreferences = appContext.getSharedPreferences(
            KEY_LOGIN_SP,
            Context.MODE_PRIVATE)

    private val loginCredentialsPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            KEY_LOGIN_CREDENTIALS_SP,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveLogin() {
        loginPreferences.edit {
            putBoolean("logged", true)
        }
    }

    fun isLogged(): Boolean {
        return loginPreferences.getBoolean("logged", false)
    }

    fun setEmailLogin(email: String){
        loginCredentialsPreferences.edit {
            putString("email", email)
        }
    }

    fun getEmailLogin(): String?{
        return loginCredentialsPreferences.getString("email", null)
    }

    fun setPinLogin(pin: String){
        loginCredentialsPreferences.edit {
            putString("pin", pin)
        }
    }

    fun getPinLogin(): String?{
        return loginCredentialsPreferences.getString("pin", null)
    }

    fun isPinLoginSaved(): Boolean {
        return !loginCredentialsPreferences.getString("pin", null).isNullOrEmpty()
    }
}