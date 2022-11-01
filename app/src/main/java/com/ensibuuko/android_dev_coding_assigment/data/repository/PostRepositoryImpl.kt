package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.PostApi
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.data.models.PostDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Response

class PostRepositoryImpl(
    private val postApi: PostApi,
    private val postDao: PostDao,
): PostsRepository {

    override suspend fun fetchPosts(): Flow<List<Post>> = flow { emit(postApi.getPosts()) }

    override suspend fun addRemotePost(post: Post): Flow<Post> = flow { emit(postApi.addPost(post)) }

    override suspend fun updateRemotePost(post: Post): Flow<Post> = flow { emit(postApi.updatePost(post.id.toString(), post)) }

    override suspend fun deleteRemotePost(postId: String): Flow<Response> = flow { emit(postApi.deletePost(postId)) }

    override suspend fun getLocalPosts(): Flow<List<Post>> = postDao.getPosts()

    override suspend fun insertPosts(posts: List<Post>) {
        postDao.insertPosts(posts)
    }

    override suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }

    override suspend fun updateLocalPost(post: Post) = postDao.updatePost(post)

    override suspend fun deleteLocalPost(post: Post) = postDao.deletePost(post)
}