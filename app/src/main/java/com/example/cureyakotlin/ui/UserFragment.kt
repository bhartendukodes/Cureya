package com.example.cureyakotlin.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.cureyakotlin.R
import com.example.cureyakotlin.databinding.FragmentUserBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class UserFragment : Fragment() {

    private val binding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
    }

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setData()
        return binding.root
    }

    private fun setData() {
        firebaseAuth.currentUser?.let {
            binding.userName.text = it.displayName
            binding.userEmail.text = it.email
            binding.profileImg.load(it.photoUrl) {
                placeholder(R.drawable.profile)
                error(R.drawable.profile)
            }
        }

        binding.btnSignOut.setOnClickListener {
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener { // user is now signed out
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                }
        }
    }
}
