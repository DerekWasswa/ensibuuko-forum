package com.ensibuuko.android_dev_coding_assigment.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ensibuuko.android_dev_coding_assigment.*
import com.ensibuuko.android_dev_coding_assigment.repository.FakePostRepository
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest{

    private lateinit var postsViewModel: PostsViewModel
    private lateinit var postRepository: FakePostRepository

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
        postRepository = FakePostRepository()
        postsViewModel = PostsViewModel(
            postRepository,
            connectionDetector,
            mainCoroutineRule.getTestCoroutineDispatcherProvider()
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_fetch_remote_posts() = mainCoroutineRule.runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        postsViewModel.fetchPosts()
        postsViewModel.posts.observeForTesting {
            advanceTimeByAndRun(100)
            assertEquals(true, it.value?.data?.isNotEmpty() ?: false)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_fetch_local_posts() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        postsViewModel.fetchPosts()
        postsViewModel.posts.observeForTesting {
            advanceTimeByAndRun(100)
            assertEquals(102, it.value!!.data!!.first().id)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_add_remote_post() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        postsViewModel.addPost(dummyPost)
        postsViewModel.postOperations.observeForTesting {
            advanceTimeByAndRun(100)
            assertEquals(true, it.value!!.data)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(java.lang.Exception::class)
    fun tests_update_remote_post() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        postsViewModel.updatePost(dummyPost)
        postsViewModel.postOperations.observeForTesting {
            advanceTimeByAndRun(100)
            assertEquals(true, it.value!!.data)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(java.lang.Exception::class)
    fun tests_delete_remote_post() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        postsViewModel.deletePost(dummyPost)
        postsViewModel.postOperations.observeForTesting {
            advanceTimeByAndRun(100)
            assertEquals(true, it.value!!.data)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun tests_add_local_post() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        postsViewModel.addPost(dummyPost)
        postsViewModel.postOperations.observeForTesting {
            advanceTimeByAndRun(100)
            assertEquals(true, it.value!!.data)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_update_local_post() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        postsViewModel.updatePost(dummyPost)
        postsViewModel.postOperations.observeForTesting {
            advanceTimeByAndRun(100)
            assertEquals(true, it.value!!.data)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_delete_local_post() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        postsViewModel.deletePost(dummyPost)
        postsViewModel.postOperations.observeForTesting {
            advanceTimeByAndRun(100)
            assertEquals(true, it.value!!.data)
        }
    }

}