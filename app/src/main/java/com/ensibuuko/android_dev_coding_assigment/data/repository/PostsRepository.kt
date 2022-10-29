package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.utils.Resource

interface PostsRepository {
    suspend fun fetchPosts(): Resource<List<Post>>
    suspend fun getLocalPosts(): List<Post>
}