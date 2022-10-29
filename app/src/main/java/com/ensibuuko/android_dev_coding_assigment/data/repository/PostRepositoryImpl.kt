package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.PostApi
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.data.models.PostDao
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepositoryImpl(
    private val postApi: PostApi,
    private val postDao: PostDao,
    private val connectionDetector: ConnectionDetector
): PostsRepository {
    override suspend fun fetchPosts(): Resource<List<Post>> {
        return if (connectionDetector.isNetworkAvailable()) {
            try {
                val posts = postApi.getPosts()
                withContext(Dispatchers.IO) {
                    for (post in posts)
                        postDao.insertPost(post)
                }
                Resource.success(posts)
            } catch (e: Exception) {
                Resource.error(e.message?:"Fetching Posts Error!")
            }
        } else {
            val data = getLocalPosts()
            Resource.success(data)
        }
    }

    override suspend fun getLocalPosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            postDao.getPosts()
        }
    }
}