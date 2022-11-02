package com.ensibuuko.android_dev_coding_assigment.repository

import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.data.repository.CommentRepository
import com.ensibuuko.android_dev_coding_assigment.dummyComment
import com.ensibuuko.android_dev_coding_assigment.dummyLocalComment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCommentRepository: CommentRepository {
    override suspend fun fetchComments(): Flow<List<Comment>> = flow { emit(listOf(dummyComment)) }

    override suspend fun fetchPostComments(postId: String): Flow<List<Comment>> = flow {
        emit(listOf(dummyComment))
    }

    override suspend fun addRemoteComment(comment: Comment): Flow<Comment> = flow { emit(dummyComment) }

    override suspend fun updateRemoteComment(comment: Comment): Flow<Comment> = flow { emit(dummyComment) }

    override suspend fun deleteRemoteComment(commentId: String): Flow<Comment> = flow { emit(dummyComment) }

    override suspend fun getLocalComments(): Flow<List<Comment>> = flow { emit(listOf(
        dummyLocalComment)) }

    override suspend fun getLocalPostComments(postId: Long): Flow<List<Comment>> = flow { emit(listOf(dummyComment)) }

    override suspend fun insertComments(comments: List<Comment>) = Unit

    override suspend fun insertComment(comment: Comment) = Unit

    override suspend fun updateLocalComment(comment: Comment) = Unit

    override suspend fun deleteLocalComment(comment: Comment) = Unit
}