package com.ensibuuko.android_dev_coding_assigment.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ensibuuko.android_dev_coding_assigment.*
import com.ensibuuko.android_dev_coding_assigment.repository.FakeCommentRepository
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CommentsViewModelTest{

    private lateinit var commentsViewModel: CommentsViewModel
    private val commentRepository = FakeCommentRepository()

    @Mock
    lateinit var connectionDetector: ConnectionDetector

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        commentsViewModel = CommentsViewModel(commentRepository, connectionDetector, mainCoroutineRule.getTestCoroutineDispatcherProvider())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_fetch_remote_comments() = mainCoroutineRule.runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        commentsViewModel.fetchComments()
        commentsViewModel.comments.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(3, it.value!!.data!!.first().id)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_fetch_local_comments() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        commentsViewModel.fetchComments()
        commentsViewModel.comments.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(4, it.value!!.data!!.first().id)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_fetch_remote_post_comments() = mainCoroutineRule.runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        commentsViewModel.fetchPostComments(101)
        commentsViewModel.postComments.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(true, it.value!!.data!!.isNotEmpty())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_fetch_local_post_comments() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        commentsViewModel.fetchPostComments(101)
        commentsViewModel.postComments.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(true, it.value!!.data!!.isNotEmpty())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_add_remote_comment() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        commentsViewModel.addComment(dummyComment)
        commentsViewModel.commentOperations.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(true, it.value!!.data)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(java.lang.Exception::class)
    fun tests_update_remote_comment() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        commentsViewModel.updateComment(dummyComment)
        commentsViewModel.commentOperations.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(true, it.value!!.data)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(java.lang.Exception::class)
    fun tests_delete_remote_comment() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        commentsViewModel.deleteComment(dummyComment)
        commentsViewModel.commentOperations.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(true, it.value!!.data)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun tests_add_local_comment() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        commentsViewModel.addComment(dummyLocalComment)
        commentsViewModel.commentOperations.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(true, it.value!!.data)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_update_local_comment() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        commentsViewModel.updateComment(dummyLocalComment)
        commentsViewModel.commentOperations.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(true, it.value!!.data)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_delete_local_comment() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        commentsViewModel.deleteComment(dummyLocalComment)
        commentsViewModel.commentOperations.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(true, it.value!!.data)
        }
    }

}