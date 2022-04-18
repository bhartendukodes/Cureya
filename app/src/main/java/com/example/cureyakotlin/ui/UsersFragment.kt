package com.example.cureyakotlin.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cureyakotlin.model.Model
import com.example.cureyakotlin.adapter.UsersAdapter
import com.example.cureyakotlin.databinding.FragmentUsersBinding
import com.google.firebase.firestore.*

class UsersFragment : Fragment() {

    private val binding by lazy {
        FragmentUsersBinding.inflate(layoutInflater)
    }

    private val usersAdapter by lazy {
        UsersAdapter {
            val action = UsersFragmentDirections.actionUsersFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    private lateinit var db:FirebaseFirestore
    private lateinit var userArrayList: ArrayList<Model>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventChangeListener()
        setLayout()
        return binding.root
    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        userArrayList = ArrayList()
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    document.apply {
                        userArrayList.add(
                            Model(
                            id = document.id,
                            name = data["fullName"].toString(),
                            gmail = data["email"].toString()
                        )
                        )
                    }
                }
                usersAdapter.submitList(userArrayList)
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)

            }
    }

    private fun setLayout() {
        binding.apply {
            rvMain.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvMain.adapter = usersAdapter
        }
    }
}