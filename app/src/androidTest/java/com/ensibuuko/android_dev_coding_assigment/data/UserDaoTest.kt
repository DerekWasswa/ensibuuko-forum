package com.ensibuuko.android_dev_coding_assigment.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ensibuuko.android_dev_coding_assigment.data.models.UserDao
import com.ensibuuko.android_dev_coding_assigment.dummyUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = appDatabase.userDao()
    }

    @Test
    fun tests_insert_and_get_users() = runBlocking {
        userDao.insertUsers(listOf(dummyUser))
        val results = userDao.getUsers()
        Assert.assertEquals(1, results.size)
    }

    @After
    fun teardown() {
        appDatabase.close()
    }
}