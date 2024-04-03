package com.example.githubuser.data.ui.userinterface

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.ui.datas.response.GithubUsers
import com.example.githubuser.data.ui.helper.ModeSettingModelFactory
import com.example.githubuser.data.ui.helper.SettingPreferences
import com.example.githubuser.data.ui.helper.dataStore
import com.example.githubuser.data.ui.modelView.MainViewModel
import com.example.githubuser.data.ui.modelView.ModeSettingViewModel
import com.example.githubuser.databinding.ActivityMainBinding
import com.google.android.material.search.SearchBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        mainViewModel.githubUsers.observe(this) { data ->
            setReviewData(data.items)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val modeSettingViewModel = ViewModelProvider(
            this,
            ModeSettingModelFactory(pref)
        ).get(ModeSettingViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { userlist ->
            setReviewData(userlist)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        modeSettingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            updateMenuIconTint(isDarkModeActive)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    val username = searchView.text.toString()
                    mainViewModel.searchUser(username)
                    false
                }

        }
        val searchBar = findViewById<SearchBar>(R.id.searchBar)
        searchBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuFav -> {
                    openFavoriteActivity()
                    true
                }

                R.id.menuMode -> {
                    openModeSettingActivity()
                    true
                }

                else -> false
            }
        }
    }

    private fun setReviewData(usersData: List<GithubUsers>) {
        val adapter = UsersAdapter()
        adapter.submitList(usersData)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun openFavoriteActivity() {
        val intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
    }

    private fun openModeSettingActivity() {
        val intent = Intent(this, ModeSettingActivity::class.java)
        startActivity(intent)
    }

    private fun updateMenuIconTint(isDarkModeActive: Boolean) {
        val menu = binding.searchBar.menu
        val favoriteMenuItem = menu.findItem(R.id.menuFav)
        val modeMenuItem = menu.findItem(R.id.menuMode)


        val iconResId = if (isDarkModeActive) {
            R.drawable.ic_mode
        } else {
            R.drawable.ic_mode_new
        }


        favoriteMenuItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_baseline)
        modeMenuItem.icon = ContextCompat.getDrawable(this, iconResId)
    }

}
