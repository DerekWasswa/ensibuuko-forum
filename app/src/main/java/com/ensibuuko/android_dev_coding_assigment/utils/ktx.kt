package com.ensibuuko.android_dev_coding_assigment.utils

import androidx.work.*
import java.util.concurrent.TimeUnit

private fun WorkManager.syncLocalPostsWithComments() {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    enqueueUniquePeriodicWork(
        "SYNC_LOCAL_POSTS_WITH_COMMENTS",
        ExistingPeriodicWorkPolicy.REPLACE,
        PeriodicWorkRequest.Builder(LocalDataSyncWorker::class.java, 1, TimeUnit.HOURS).setConstraints(constraints).build()
    )
}
