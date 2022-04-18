package com.example.cureyakotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.cureyakotlin.R
import com.example.cureyakotlin.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val binding by lazy {
        FragmentDetailBinding.inflate(layoutInflater)
    }

    private val args:DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            userName.text=args.data.name
            userEmail.text=args.data.gmail
        }
    }
}