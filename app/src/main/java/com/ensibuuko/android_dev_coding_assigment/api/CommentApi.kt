package com.ensibuuko.android_dev_coding_assigment.api

import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import okhttp3.Response
import retrofit2.http.*

interface CommentApi {
    @GET("comments")
    suspend fun getComments(): List<Comment>

    @GET("posts/{postId}/comments")
    suspend fun getPostComments(@Path("postId") postId: String): List<Comment>

    @POST("posts/{postId}/comments")
    suspend fun postComment(@Path("postId") postId: String, @Body comment: Comment): Comment

    @PUT("comments/{commentId}")
    suspend fun updateComment(@Path("commentId") commentId: String, @Body comment: Comment): Comment

    @DELETE("comments/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: String) : Response
}