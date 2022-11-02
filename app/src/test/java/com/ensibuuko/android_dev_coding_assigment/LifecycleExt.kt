package com.ensibuuko.android_dev_coding_assigment

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent

/**
 * Observes a [LiveData] until the `block` is done executing.
 */
fun <T> LiveData<T>.observeForTesting(block: (LiveData<T>) -> Unit) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block(this)
    } finally {
        removeObserver(observer)
    }
}

@ExperimentalCoroutinesApi
fun TestScope.advanceTimeByAndRun(delayTimeMillis: Long) {
    testScheduler.advanceTimeBy(delayTimeMillis)
    runCurrent()
}