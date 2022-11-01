package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.CommentApi
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.data.models.CommentDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Response

class CommentRepositoryImpl(
    private val commentApi: CommentApi,
    private val commentDao: CommentDao,
): CommentRepository {
    override suspend fun fetchComments(): Flow<List<Comment>> = flow { emit(commentApi.getComments()) }

    override suspend fun fetchPostComments(postId: String): Flow<List<Comment>> = flow { emit(commentApi.getPostComments(postId)) }

    override suspend fun addRemoteComment(comment: Comment): Flow<Comment> = flow { emit(commentApi.postComment(comment.postId.toString(), comment)) }

    override suspend fun updateRemoteComment(comment: Comment): Flow<Comment> = flow { emit(commentApi.updateComment(comment.id.toString(), comment)) }

    override suspend fun deleteRemoteComment(commentId: String): Flow<Response> = flow { emit(commentApi.deleteComment(commentId)) }

    override suspend fun getLocalComments(): Flow<List<Comment>> = commentDao.getComments()

    override suspend fun getLocalPostComments(postId: Long): Flow<List<Comment>> = commentDao.getPostComments(postId)

    override suspend fun insertComments(comments: List<Comment>) = commentDao.insertComments(comments)

    override suspend fun insertComment(comment: Comment) = commentDao.insertComment(comment)

    override suspend fun updateLocalComment(comment: Comment) = commentDao.updateComment(comment)

    override suspend fun deleteLocalComment(comment: Comment) = commentDao.deleteComment(comment)

}