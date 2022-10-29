package com.ensibuuko.android_dev_coding_assigment.api

import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts")
    suspend fun addPost(@QueryMap options: Map<String, Any>): Post
}