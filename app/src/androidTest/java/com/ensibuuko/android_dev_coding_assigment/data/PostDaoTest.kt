package com.ensibuuko.android_dev_coding_assigment.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ensibuuko.android_dev_coding_assigment.data.models.PostDao
import com.ensibuuko.android_dev_coding_assigment.dummyPost
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PostDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var postDao: PostDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        postDao = appDatabase.postDao()
    }

    @Test
    fun tests_insert_and_get_posts() = runBlocking {
        postDao.insertPosts(listOf(dummyPost))
        val results = postDao.getPosts().first()
        Assert.assertEquals(1, results.size)
    }

    @Test
    fun tests_inserts_post() = runBlocking {
        postDao.insertPost(dummyPost)
        val results = postDao.getPosts().first()
        Assert.assertEquals(1, results.size)
    }

    @Test
    fun tests_delete_post() = runBlocking {
        postDao.insertPost(dummyPost)
        postDao.deletePost(dummyPost)
        val results = postDao.getPosts().first()
        Assert.assertEquals(0, results.size)
    }

    @Test
    fun tests_update_post() = runBlocking {
        postDao.insertPost(dummyPost)

        val updatePost = dummyPost
        updatePost.synced = true
        postDao.updatePost(updatePost)
        val results = postDao.getPosts().first()

        Assert.assertEquals(true, results.first().synced)
    }

    @After
    fun teardown() {
        appDatabase.close()
    }

}