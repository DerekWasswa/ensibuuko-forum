package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.PostApi
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.data.models.PostDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(
    private val postApi: PostApi,
    private val postDao: PostDao,
): PostsRepository {

    override suspend fun fetchPosts(): Flow<List<Post>> = flow { emit(postApi.getPosts()) }

    override suspend fun getLocalPosts(): Flow<List<Post>> = postDao.getPosts()

    override suspend fun insertPosts(posts: List<Post>) {
        postDao.insertPosts(posts)
    }

    override suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }
}