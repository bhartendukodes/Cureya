package com.example.cureyakotlin.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cureyakotlin.databinding.ItemUsersBinding
import com.example.cureyakotlin.model.Model

class UsersAdapter(val onItemClick:(Model)->Unit) : ListAdapter<Model, UsersAdapter.ViewHolder>(BannerDiffUtil) {


    inner class ViewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root)


    object BannerDiffUtil : DiffUtil.ItemCallback<Model>() {
        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = getItem(position)

        holder.binding.apply {
            tvUserName.text=value.name
            tvUserGmail.text=value.gmail
            Log.d("Tag","value $value")

            root.setOnClickListener {
                onItemClick.invoke(value)
            }
        }
    }
}
