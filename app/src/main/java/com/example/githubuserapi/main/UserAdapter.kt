package com.example.githubuserapi.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapi.databinding.UserItemBinding
import com.example.githubuserapi.main.data.ItemsItem

class UserAdapter(private val listData : List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var onItemClickCallBack: OnItemClickCallBack? = null
    fun setOnItemClickCallBack (onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    class UserViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = listData[position]
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .circleCrop()
            .into(holder.binding.ivProfilepic)
        holder.binding.tvUsername.text = data.login
        holder.itemView.setOnClickListener {
            onItemClickCallBack?.onItemClicked(listData[position])
        }
    }

    override fun getItemCount(): Int = listData.size

    interface OnItemClickCallBack{
        fun onItemClicked(data: ItemsItem)
    }
}