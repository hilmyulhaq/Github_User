package com.example.githubuser.data.ui.userinterface

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.ui.datas.response.GithubUsers
import com.example.githubuser.databinding.ItemUsersBinding

class UsersAdapter : ListAdapter<GithubUsers, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                putExtra("username", users.login)
            }
            holder.itemView.context.startActivity(intent)
        }
    }
    class MyViewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUsers){
            binding.tvItemUsername.text = "${user.login}"
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUsers>() {
            override fun areItemsTheSame(oldItem: GithubUsers, newItem: GithubUsers): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: GithubUsers, newItem: GithubUsers): Boolean {
                return oldItem == newItem
            }
        }
    }
}