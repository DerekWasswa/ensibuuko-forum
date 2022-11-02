package com.ensibuuko.android_dev_coding_assigment.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ensibuuko.android_dev_coding_assigment.data.models.CommentDao
import com.ensibuuko.android_dev_coding_assigment.dummyComment
import com.ensibuuko.android_dev_coding_assigment.dummyLocalComment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CommentDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var commentDao: CommentDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        commentDao = appDatabase.commentDao()
    }

    @Test
    fun tests_insert_and_get_comments() = runBlocking {
        commentDao.insertComments(listOf(dummyLocalComment))
        val results = commentDao.getComments().first()
        Assert.assertEquals(1, results.size)
    }

    @Test
    fun tests_inserts_comment() = runBlocking {
        commentDao.insertComment(dummyComment)
        val results = commentDao.getComments().first()
        Assert.assertEquals(1, results.size)
    }

    @Test
    fun tests_delete_comment() = runBlocking {
        commentDao.insertComment(dummyComment)
        commentDao.deleteComment(dummyComment)
        val results = commentDao.getComments().first()
        Assert.assertEquals(0, results.size)
    }

    @Test
    fun tests_update_post() = runBlocking {
        commentDao.insertComment(dummyComment)

        val updateComment = dummyComment
        updateComment.synced = true
        commentDao.updateComment(updateComment)
        val results = commentDao.getComments().first()

        Assert.assertEquals(true, results.first().synced)
    }

    @Test
    fun tests_insert_and_get_post_comments() = runBlocking {
        commentDao.insertComments(listOf(dummyLocalComment))
        val results = commentDao.getPostComments(101).first()
        Assert.assertEquals(4, results.first().id)
    }

    @After
    fun teardown() {
        appDatabase.close()
    }

}