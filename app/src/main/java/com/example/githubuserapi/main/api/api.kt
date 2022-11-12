package com.example.githubuserapi.main.api

import com.example.githubuserapi.main.data.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q")query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username : String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}