package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    suspend fun fetchPosts(): Flow<List<Post>>
    suspend fun getLocalPosts(): Flow<List<Post>>
    suspend fun insertPosts(posts: List<Post>)
    suspend fun insertPost(post: Post)
}