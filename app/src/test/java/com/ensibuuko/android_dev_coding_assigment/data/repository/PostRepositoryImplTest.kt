package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.PostApi
import com.ensibuuko.android_dev_coding_assigment.data.models.PostDao
import com.ensibuuko.android_dev_coding_assigment.dummyLocalPost
import com.ensibuuko.android_dev_coding_assigment.dummyPost
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostRepositoryImplTest {

    private lateinit var postRepositoryImpl: PostRepositoryImpl

    @Mock
    lateinit var postApi: PostApi

    @Mock
    lateinit var postDao: PostDao

    @Before
    fun setUp() {
        postRepositoryImpl = PostRepositoryImpl(postApi, postDao)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_fetch_remote_posts() = runTest {
        Mockito.`when`(postApi.getPosts())
            .thenReturn(listOf(dummyPost))

        val result = postRepositoryImpl.fetchPosts().first()

        // assertions
        assertEquals(101, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_add_remote_post() = runTest {
        Mockito.`when`(postApi.addPost(anyOrNull()))
            .thenReturn(dummyPost)

        val result = postRepositoryImpl.addRemotePost(dummyPost)

        // assertions
        assertEquals(dummyPost.id, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_update_remote_post() = runTest {
        Mockito.`when`(postApi.updatePost(Mockito.anyString(), anyOrNull()))
            .thenReturn(dummyPost)

        val result = postRepositoryImpl.updateRemotePost(dummyPost)

        // assertions
        assertEquals(dummyPost.id, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_delete_remote_post() = runTest {
        Mockito.`when`(postApi.deletePost(Mockito.anyString()))
            .thenReturn(dummyPost)

        val result = postRepositoryImpl.deleteRemotePost("3")

        // assertions
        assertEquals(dummyPost.id, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_fetch_local_posts() = runTest {
        Mockito.`when`(postDao.getPosts())
            .thenReturn(flow { emit(listOf(dummyLocalPost)) })

        val result = postRepositoryImpl.getLocalPosts().first()

        // assertions
        assertEquals(102, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_insert_local_post() = runTest {
        Mockito.`when`(postDao.insertPost(anyOrNull()))
            .thenReturn(Unit)

        val result = postRepositoryImpl.insertPost(dummyLocalPost)

        // assertions
        assertEquals(Unit, result)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_insert_local_posts() = runTest {
        Mockito.`when`(postDao.insertPosts(Mockito.anyList()))
            .thenReturn(Unit)

        val result = postRepositoryImpl.insertPosts(listOf(dummyLocalPost))

        // assertions
        assertEquals(Unit, result)
    }

}