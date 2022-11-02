package com.ensibuuko.android_dev_coding_assigment.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ensibuuko.android_dev_coding_assigment.MainCoroutineRule
import com.ensibuuko.android_dev_coding_assigment.advanceTimeByAndRun
import com.ensibuuko.android_dev_coding_assigment.getTestCoroutineDispatcherProvider
import com.ensibuuko.android_dev_coding_assigment.observeForTesting
import com.ensibuuko.android_dev_coding_assigment.repository.FakeUserRepository
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
class UsersViewModelTest {

    private lateinit var usersViewModel: UsersViewModel
    private val userRepository = FakeUserRepository()

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
        usersViewModel = UsersViewModel(userRepository, connectionDetector, mainCoroutineRule.getTestCoroutineDispatcherProvider())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_fetch_remote_users() = mainCoroutineRule.runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(true)

        usersViewModel.fetchUsers()
        usersViewModel.users.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(true, it.value?.data?.isNotEmpty() ?: false)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun tests_fetch_local_users() = runTest {
        Mockito.`when`(connectionDetector.isNetworkAvailable())
            .thenReturn(false)

        usersViewModel.fetchUsers()
        usersViewModel.users.observeForTesting {
            advanceTimeByAndRun(100)
            Assert.assertEquals(100, it.value!!.data!!.first().id)
        }
    }

}