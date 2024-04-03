package com.example.githubuser.data.ui.userinterface

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.ui.datas.response.FollowResponseItem
import com.example.githubuser.databinding.ItemFollowsBinding

class FollowAdapter : ListAdapter<FollowResponseItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFollowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val follower = getItem(position)
        holder.bind(follower)
    }

    inner class MyViewHolder(private val binding: ItemFollowsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: FollowResponseItem) {
            binding.tvFollowsUsername.text = follower.login
            Glide.with(binding.root)
                .load(follower.avatarUrl)
                .into(binding.imgFollowsPhoto)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowResponseItem, newItem: FollowResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FollowResponseItem, newItem: FollowResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
