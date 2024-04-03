package com.example.githubuser.data.ui.userinterface

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.ui.datas.database.FavoriteUser
import com.example.githubuser.data.ui.modelView.FavoriteUserViewModel
import com.example.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity(), FavoriteAdapter.OnItemClickCallback {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAdapter = FavoriteAdapter()
        binding.rvlistFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvlistFavorite.adapter = favoriteAdapter
        binding.rvlistFavorite.setHasFixedSize(true)

        favoriteAdapter.setOnItemCLickCallback(this)

        favoriteUserViewModel.getAllFav().observe(this) { users ->
            val items = arrayListOf<FavoriteUser>()
            users.map {
                val user = FavoriteUser(username = it.username, avatarUrl = it.avatarUrl)
                items.add(user)
            }
            favoriteAdapter.submitList(items)
        }
    }

    override fun onItemClicked(fav: FavoriteUser, position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("username", fav.username)
        startActivity(intent)
    }

}