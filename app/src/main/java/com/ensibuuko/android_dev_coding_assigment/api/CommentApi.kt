package com.ensibuuko.android_dev_coding_assigment.api

import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import okhttp3.Response
import retrofit2.http.*

interface CommentApi {
    @GET("comments")
    suspend fun getComments(): List<Comment>

    @GET("post/{postId}/comment")
    suspend fun getPostComments(@Path("postId") postId: String): List<Comment>

    @POST("post/{postId}/comment")
    suspend fun postComment(@Path("postId") postId: String, @Body comment: Comment): Comment

    @PUT("comment/{commentId}")
    suspend fun updateComment(@Path("commentId") commentId: String, @Body comment: Comment): Comment

    @DELETE("comment/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: String) : Response
}