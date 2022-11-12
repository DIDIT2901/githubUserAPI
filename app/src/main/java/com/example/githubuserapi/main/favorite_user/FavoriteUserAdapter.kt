package com.example.githubuserapi.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapi.databinding.UserItemBinding
import com.example.githubuserapi.main.database.FavoriteUser

class FavoriteUserAdapter(private val listData : List<FavoriteUser>) : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {
    private var onItemClickCallBack: OnItemClickCallBack? = null
    fun setOnItemClickCallBack (onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    class FavoriteUserViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder((view))
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val data = listData[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar_url)
            .circleCrop()
            .into(holder.binding.ivProfilepic)
        holder.binding.tvUsername.text = data.login
        holder.itemView.setOnClickListener {
            onItemClickCallBack?.onItemClicked(listData[position])
        }
    }

    override fun getItemCount(): Int = listData.size

    interface OnItemClickCallBack{
        fun onItemClicked(data: FavoriteUser)
    }

}