package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.UserApi
import com.ensibuuko.android_dev_coding_assigment.data.models.UserDao
import com.ensibuuko.android_dev_coding_assigment.dummyUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest {

    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Mock
    lateinit var userApi: UserApi

    @Mock
    lateinit var userDao: UserDao

    @Before
    fun setUp() {
        userRepositoryImpl = UserRepositoryImpl(userApi, userDao)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_fetch_remote_users() = runTest {
        Mockito.`when`(userApi.getUsers())
            .thenReturn(listOf(dummyUser))

        val result = userRepositoryImpl.fetchUsers().first()

        // assertions
        assertEquals(100, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_fetch_local_users() = runTest {
        Mockito.`when`(userDao.getUsers())
            .thenReturn(listOf(dummyUser))

        val result = userRepositoryImpl.getLocalUsers().first()

        // assertions
        assertEquals(100, result.first().id)
    }

    @ExperimentalCoroutinesApi
    @Test()
    fun tests_insert_local_users() = runTest {
        Mockito.`when`(userDao.insertUsers(Mockito.anyList()))
            .thenReturn(Unit)

        val result = userRepositoryImpl.insertUsers(listOf(dummyUser))

        // assertions
        assertEquals(Unit, result)
    }

}