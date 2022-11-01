package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import kotlinx.coroutines.flow.Flow
import okhttp3.Response

interface CommentRepository {
    suspend fun fetchComments(): Flow<List<Comment>>
    suspend fun fetchPostComments(postId: String): Flow<List<Comment>>
    suspend fun addRemoteComment(comment: Comment): Flow<Comment>
    suspend fun updateRemoteComment(comment: Comment): Flow<Comment>
    suspend fun deleteRemoteComment(commentId: String): Flow<Response>

    suspend fun getLocalComments(): Flow<List<Comment>>
    suspend fun getLocalPostComments(postId: Long): Flow<List<Comment>>
    suspend fun insertComments(comments: List<Comment>)
    suspend fun insertComment(comment: Comment)
    suspend fun updateLocalComment(comment: Comment)
    suspend fun deleteLocalComment(comment: Comment)
}