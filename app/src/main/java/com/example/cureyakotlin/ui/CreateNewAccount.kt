package com.example.cureyakotlin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cureyakotlin.databinding.ActivityCreateNewAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class CreateNewAccount : AppCompatActivity() {

    private val binding by lazy {
        ActivityCreateNewAccountBinding.inflate(layoutInflater)
    }

    private lateinit var fStore: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {

        fStore = FirebaseFirestore.getInstance()

        binding.alreadyHaveAnAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnSignUp.setOnClickListener {
            val fullName = binding.edName.text.toString().trim()
            val email = binding.edEmail.text.toString().trim()
            val password = binding.edPassword.text.toString().trim()
            if (email.isNullOrEmpty())
                binding.layoutEmailInput.error = "Empty"
            else if (password.isNullOrEmpty())
                binding.layoutPasswordInput.error = "Empty"
            else if (fullName.isNullOrEmpty())
                binding.layoutNameInput.error = "Empty"
            else {
                initEmailSignUp(fullName, email, password)
            }
        }
    }

    private fun initEmailSignUp(fullName: String, email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = FirebaseAuth.getInstance().currentUser
                    val updateProfile =
                        UserProfileChangeRequest.Builder().setDisplayName(fullName).build()
                    user?.updateProfile(updateProfile)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            updateUI(user)
                        }
                    }
                    val users= hashMapOf(
                        "fullName" to fullName,
                        "email" to email,
                        "password" to password
                    )
                    fStore.collection("users")
                        .add(users)
                        .addOnSuccessListener {  documentReference ->
                            Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("TAG", "Error adding document", e)
                        }
                } else {
                    Toast.makeText(
                        this, "Authentication failed due to : ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            this.finishAffinity()
        }
    }
}