package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.CommentApi
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.data.models.CommentDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommentRepositoryImpl(
    private val commentApi: CommentApi,
    private val commentDao: CommentDao,
): CommentRepository {
    override suspend fun fetchComments(): Flow<List<Comment>> = flow { emit(commentApi.getComments()) }

    override suspend fun getLocalComments(): Flow<List<Comment>> = commentDao.getComments()

    override suspend fun insertComments(comments: List<Comment>) {
        commentDao.insertComments(comments)
    }

    override suspend fun insertComment(comment: Comment) {
        commentDao.insertComment(comment)
    }

}