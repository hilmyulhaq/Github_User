package com.example.githubuser.data.ui.modelView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubuser.data.ui.datas.database.FavoriteUser
import com.example.githubuser.data.ui.datas.repository.FavoriteUserRepository

class FavoriteAddUpdateViewModel (application: Application) : AndroidViewModel(application) {
    private val mFavoriteRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun insert(favorite: FavoriteUser) {
        mFavoriteRepository.insert(favorite)
    }
    fun delete(favorite: FavoriteUser) {
        mFavoriteRepository.delete(favorite)
    }
}