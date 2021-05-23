package com.listen.to.miskiatty.view.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityLoginBinding
import com.listen.to.miskiatty.model.database.room.FirebaseBackup
import com.listen.to.miskiatty.model.network.auth.FireAuthService
import com.listen.to.miskiatty.model.network.firestore.FireStoreService
import com.listen.to.miskiatty.model.provider.PreferenceProvider
import com.listen.to.miskiatty.view.ui.activities.MainActivity
import com.listen.to.miskiatty.viewmodel.LoginViewModel
import com.listen.to.wave.viewmodel.CallbackFireAuth
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var fireAuthService: FireAuthService
    lateinit var fireStoreService: FireStoreService

    private var loginViewModel: LoginViewModel? = null
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupBinding()
        setUpFirebase()

        val preferenceProvider = PreferenceProvider(this)

        loginViewModel?.loginClicked?.observe(this, {
            if (it) {
                val email = binding.user.editText?.text.toString()
                val password = binding.password.editText?.text.toString()

                preferenceProvider.setEmailLogin(email)

                fireAuthService.userLogin(email, password, object: CallbackFireAuth<FirebaseUser> {
                    override fun onSucces(result: FirebaseUser?) {

                        val firebaseBackup = FirebaseBackup(this@LoginActivity, lifecycle)
                        firebaseBackup.chargeBackup(email)

                        if(preferenceProvider.isPinLoginSaved()){
                            preferenceProvider.saveLogin()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java).addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            finish()
                        }else {
                            startActivity(Intent(this@LoginActivity, SetPinLoginActivity::class.java))
                        }
                    }

                    override fun onFailure(e: Exception) {
                        loginViewModel!!.getLoginErrors(binding.user, binding.password)
                    }
                })
            }
        })
    }

    private fun setupBinding(){
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_login)

        loginViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
    }

    private fun setUpFirebase(){
        auth = Firebase.auth
        fireAuthService = FireAuthService(auth)
        fireStoreService = FireStoreService(FirebaseFirestore.getInstance())
    }
}