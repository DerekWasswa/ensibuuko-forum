package com.ensibuuko.android_dev_coding_assigment.api

import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CommentApi {
    @GET("comments")
    suspend fun getComments(): List<Comment>

    @GET("comments")
    suspend fun postComment(@QueryMap options: Map<String, Any>): Comment
}