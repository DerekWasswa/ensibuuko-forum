package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.CommentApi
import com.ensibuuko.android_dev_coding_assigment.data.models.CommentDao
import com.ensibuuko.android_dev_coding_assigment.dummyComment
import com.ensibuuko.android_dev_coding_assigment.dummyLocalComment
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
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CommentRepositoryImplTest {

    private lateinit var commentRepositoryImpl: CommentRepositoryImpl

    @Mock
    lateinit var commentApi: CommentApi

    @Mock
    lateinit var commentDao: CommentDao

    @Before
    fun setUp() {
        commentRepositoryImpl = CommentRepositoryImpl(commentApi, commentDao)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_fetch_remote_comments() = runTest {
        Mockito.`when`(commentApi.getComments())
            .thenReturn(listOf(dummyComment))

        val result = commentRepositoryImpl.fetchComments().first()

        // assertions
        assertEquals(dummyComment.id, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_fetch_remote_post_comments() = runTest {
        Mockito.`when`(commentApi.getPostComments(anyString()))
            .thenReturn(listOf(dummyComment))

        val result = commentRepositoryImpl.fetchPostComments("101").first()

        // assertions
        assertEquals(dummyComment.id, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_post_remote_comment() = runTest {
        Mockito.`when`(commentApi.postComment(anyString(), anyOrNull()))
            .thenReturn(dummyComment)

        val result = commentRepositoryImpl.addRemoteComment(dummyComment)

        // assertions
        assertEquals(dummyComment.id, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_update_remote_comment() = runTest {
        Mockito.`when`(commentApi.updateComment(anyString(), anyOrNull()))
            .thenReturn(dummyComment)

        val result = commentRepositoryImpl.updateRemoteComment(dummyComment)

        // assertions
        assertEquals(dummyComment.id, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_delete_remote_comment() = runTest {
        Mockito.`when`(commentApi.deleteComment(anyString()))
            .thenReturn(dummyComment)

        val result = commentRepositoryImpl.deleteRemoteComment("3")

        // assertions
        assertEquals(dummyComment.id, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_fetch_local_comments() = runTest {
        Mockito.`when`(commentDao.getComments())
            .thenReturn(flow { emit(listOf(dummyLocalComment)) })

        val result = commentRepositoryImpl.getLocalComments().first()

        // assertions
        assertEquals(4, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_fetch_local_post_comments() = runTest {
        Mockito.`when`(commentDao.getPostComments(anyLong()))
            .thenReturn(flow { emit(listOf(dummyLocalComment)) })

        val result = commentRepositoryImpl.getLocalPostComments(101).first()

        // assertions
        assertEquals(4, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_insert_local_comment() = runTest {
        Mockito.`when`(commentDao.insertComment(anyOrNull()))
            .thenReturn(null)

        val result = commentRepositoryImpl.insertComment(dummyLocalComment)

        // assertions
        assertEquals(Unit, result)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_insert_local_comments() = runTest {
        Mockito.`when`(commentDao.insertComments(anyList()))
            .thenReturn(Unit)

        val result = commentRepositoryImpl.insertComments(listOf(dummyLocalComment))

        // assertions
        assertEquals(Unit, result)
    }

}