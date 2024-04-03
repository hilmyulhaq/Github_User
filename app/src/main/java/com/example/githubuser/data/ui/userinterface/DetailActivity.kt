package com.example.githubuser.data.ui.userinterface

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.ui.adapter.SectionPagerAdapter
import com.example.githubuser.data.ui.datas.database.FavoriteUser
import com.example.githubuser.data.ui.datas.response.DetailUserResponse
import com.example.githubuser.data.ui.helper.ViewModelFactory
import com.example.githubuser.data.ui.modelView.DetailViewModel
import com.example.githubuser.data.ui.modelView.FavoriteAddUpdateViewModel
import com.example.githubuser.data.ui.modelView.FavoriteUserViewModel
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    private var isFavorite = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        val viewModelFactory = ViewModelFactory.getInstance(application)


        detailViewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        favoriteAddUpdateViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoriteAddUpdateViewModel::class.java)
        favoriteUserViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoriteUserViewModel::class.java)


        val username = intent.getStringExtra("username")
        if (username != null) {
            detailViewModel.uname = username
        }


        detailViewModel.detailUsers.observe(this, Observer { data ->
            setDetailData(data)
        })


        favoriteUserViewModel.getFav(username ?: "").observe(this, Observer { favoriteUser ->
            isFavorite = favoriteUser != null
            updateFavoriteButtonUI(isFavorite)
        })

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.fab.setOnClickListener {

            if (isFavorite) {
                favoriteAddUpdateViewModel.delete(FavoriteUser(username ?: ""))
            } else {
                detailViewModel.detailUsers.value?.let {
                    val favoriteUser = FavoriteUser(username ?: "", it.avatarUrl)
                    favoriteAddUpdateViewModel.insert(favoriteUser)
                }
            }
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        val adapter = SectionPagerAdapter.SectionsPagerAdapter(this)
        adapter.username = username ?: ""

        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        updateShareIconTint()


        binding.icShare.setOnClickListener {

            val githubLink = "https://github.com/$username"
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, githubLink)
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"))
        }
    }

    private fun setDetailData(detailData: DetailUserResponse) {
        binding.tvUsername.text = detailData.login
        binding.tvName.text = detailData.name
        binding.tvFollower.text = "${detailData.followers} Followers"
        binding.tvFollowing.text = "${detailData.following} Following"
        Glide.with(this)
            .load(detailData.avatarUrl)
            .into(binding.imgItemPhoto)
    }

    private fun updateFavoriteButtonUI(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fab.setImageResource(R.drawable.ic_favorite_baseline)
        } else {
            binding.fab.setImageResource(R.drawable.ic_favorite_border)
        }
    }
    private fun updateShareIconTint() {
        val isDarkModeActive = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        val shareIconResId = if (isDarkModeActive) {
            R.drawable.ic_share_white
        } else {
            R.drawable.ic_share
        }
        binding.icShare.setImageResource(shareIconResId)
    }



    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
