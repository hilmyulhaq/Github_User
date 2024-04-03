package com.example.githubuser.data.ui.modelView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuser.data.ui.datas.database.FavoriteUser
import com.example.githubuser.data.ui.datas.repository.FavoriteUserRepository

class FavoriteUserViewModel (application: Application) : AndroidViewModel(application){
    private val repositoryFav : FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFav(): LiveData<List<FavoriteUser>> {
        return repositoryFav.getAllFavorite()
    }
    fun getFav(username: String): LiveData<FavoriteUser> = repositoryFav.getFavoriteUserByUsername(username)
}