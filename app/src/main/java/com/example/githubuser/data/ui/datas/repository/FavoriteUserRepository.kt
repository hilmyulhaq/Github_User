package com.example.githubuser.data.ui.datas.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.data.ui.datas.database.FavoriteUser
import com.example.githubuser.data.ui.datas.database.FavoriteUserDao
import com.example.githubuser.data.ui.datas.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }
    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavorite()
    fun insert(favorite: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(favorite) }
    }
    fun delete(favorite: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(favorite) }
    }
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserDao.getFavoriteUserByUsername(username)
}