package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.utils.Resource

interface CommentRepository {
    suspend fun fetchComments(): Resource<List<Comment>>
    suspend fun getLocalComments(): List<Comment>
}