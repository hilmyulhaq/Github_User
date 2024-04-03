package com.example.githubuser.data.ui.modelView

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.data.ui.datas.database.FavoriteUser
import com.example.githubuser.data.ui.datas.repository.FavoriteUserRepository
import com.example.githubuser.data.ui.datas.response.DetailUserResponse
import com.example.githubuser.data.ui.datas.response.FollowResponseItem
import com.example.githubuser.data.ui.datas.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application) : AndroidViewModel(application) {

    var uname : String =""
        set(value) { field = value
            detailUsers()
        }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val mFavoriteRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _detailUserResponse = MutableLiveData<DetailUserResponse>()
    val detailUsers: LiveData<DetailUserResponse> = _detailUserResponse

    private val _followerResponse = MutableLiveData<List<FollowResponseItem>>()
    val followerUsers: LiveData<List<FollowResponseItem>> = _followerResponse

    private val _followingResponse = MutableLiveData<List<FollowResponseItem>>()
    val followingUsers: LiveData<List<FollowResponseItem>> = _followingResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun detailUsers() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(uname)

        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUserResponse.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollower(follow : String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowers(follow)

        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followerResponse.value = responseBody
                    }
                } else {
                    Log.e(DetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e(DetailViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(follow : String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowing(follow)

        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followingResponse.value = responseBody
                    }
                } else {
                    Log.e(DetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e(DetailViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun insert(favorite: FavoriteUser) {
        mFavoriteRepository.insert(favorite)
    }
    fun delete(favorite: FavoriteUser) {
        mFavoriteRepository.delete(favorite)
    }
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return mFavoriteRepository.getFavoriteUserByUsername(username)
    }

}