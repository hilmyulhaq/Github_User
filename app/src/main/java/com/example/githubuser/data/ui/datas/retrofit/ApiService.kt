package com.example.githubuser.data.ui.datas.retrofit

import com.example.githubuser.data.ui.datas.response.DetailUserResponse
import com.example.githubuser.data.ui.datas.response.FollowResponseItem
import com.example.githubuser.data.ui.datas.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") login: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowResponseItem>>

}