package com.ensibuuko.android_dev_coding_assigment

import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.data.repository.PostsRepository
import com.ensibuuko.android_dev_coding_assigment.dummyLocalPost
import com.ensibuuko.android_dev_coding_assigment.dummyPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePostRepository: PostsRepository {
    override suspend fun fetchPosts(): Flow<List<Post>> = flow { emit(listOf(dummyPost)) }

    override suspend fun addRemotePost(post: Post): Flow<Post> = flow { emit(dummyPost) }

    override suspend fun updateRemotePost(post: Post): Flow<Post> = flow { emit(dummyPost) }

    override suspend fun deleteRemotePost(postId: String): Flow<Post> = flow { emit(dummyPost) }

    override suspend fun getLocalPosts(): Flow<List<Post>> = flow { emit(listOf(dummyLocalPost)) }

    override suspend fun insertPosts(posts: List<Post>) = Unit

    override suspend fun insertPost(post: Post) = Unit

    override suspend fun updateLocalPost(post: Post) = Unit

    override suspend fun deleteLocalPost(post: Post) = Unit
}