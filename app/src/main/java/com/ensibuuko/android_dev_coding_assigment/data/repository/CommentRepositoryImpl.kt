package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.CommentApi
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.data.models.CommentDao
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentRepositoryImpl(
    private val commentApi: CommentApi,
    private val commentDao: CommentDao,
    private val connectionDetector: ConnectionDetector
): CommentRepository {
    override suspend fun fetchComments(): Resource<List<Comment>> {
        return if (connectionDetector.isNetworkAvailable()) {
            try {
                val comments = commentApi.getComments()
                withContext(Dispatchers.IO) {
                    for (comment in comments)
                        commentDao.insertComment(comment)
                }
                Resource.success(comments)
            } catch (e: Exception) {
                Resource.error(e.message?:"Fetching Comments Error!")
            }
        } else {
            val data = getLocalComments()
            Resource.success(data)
        }
    }

    override suspend fun getLocalComments(): List<Comment> {
        return withContext(Dispatchers.IO) {
            commentDao.getComments()
        }
    }
}