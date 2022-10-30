package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun fetchComments(): Flow<List<Comment>>
    suspend fun getLocalComments(): Flow<List<Comment>>
    suspend fun insertComments(comments: List<Comment>)
    suspend fun insertComment(comment: Comment)
}