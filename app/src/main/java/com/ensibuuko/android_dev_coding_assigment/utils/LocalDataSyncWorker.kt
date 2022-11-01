package com.ensibuuko.android_dev_coding_assigment.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ensibuuko.android_dev_coding_assigment.api.CommentApi
import com.ensibuuko.android_dev_coding_assigment.api.PostApi
import com.ensibuuko.android_dev_coding_assigment.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocalDataSyncWorker(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams), KoinComponent {

    private val appDatabase: AppDatabase by inject()
    private val postApi: PostApi by inject()
    private val commentApi: CommentApi by inject()

    override suspend fun doWork(): Result {
        syncLocalPostWithComments()
        return Result.success()
    }

    private suspend fun syncLocalPostWithComments() {
        withContext(Dispatchers.IO) {
            appDatabase.postDao().getPosts().zip(appDatabase.commentDao().getComments()) { posts, comments ->
                posts.forEach { _post ->
                    if (!_post.synced) {
                        val postComments = comments.filter { it.postId == _post.id && !it.synced}
                        val post = postApi.addPost(_post)
                        postComments.map { comment ->
                            comment.postId = post.id
                        }

                        postComments.forEach { comment ->
                            commentApi.postComment(comment.postId.toString(), comment)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

}