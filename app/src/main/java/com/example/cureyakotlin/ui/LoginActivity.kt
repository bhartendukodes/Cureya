package com.example.cureyakotlin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cureyakotlin.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    val TAG = "TAG"
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUi()
        checkSignedinornot()
    }

    private fun checkSignedinornot() {
        if (FirebaseAuth.getInstance().currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }

    private fun initUi() {
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, CreateNewAccount::class.java)
            startActivity(intent)
        }

        binding.edEmail.setOnClickListener {
            binding.layoutEmailInput.error = ""
        }
        binding.edPassword.setOnClickListener {
            binding.layoutPasswordInput.error = ""
        }

        binding.btnLogin.setOnClickListener {
            validateEmailAndPassword()
        }
    }

    private fun validateEmailAndPassword() {
        if (binding.edEmail.text.toString().trim().isEmpty()) {
            binding.layoutEmailInput.error = "Empty"
        } else if (binding.edPassword.text.toString().trim().isEmpty()) {
            binding.layoutPasswordInput.error = "Empty"
        } else {
            binding.layoutEmailInput.refreshErrorIconDrawableState()
            initEmailLogin(binding.edEmail.text.toString().trim(), binding.edPassword.text.toString().trim())
        }
    }

    private fun initEmailLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, "Login Error: " + task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user!=null){
            startActivity(Intent(this, MainActivity::class.java))
            this.finishAffinity()
        }
    }
}