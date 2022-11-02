package com.ensibuuko.android_dev_coding_assigment


import com.ensibuuko.android_dev_coding_assigment.utils.CoroutineDispatcher
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun MainCoroutineRule.getTestCoroutineDispatcherProvider() = mockk<CoroutineDispatcher> {
    every { main } returns testCoroutineDispatcher
    every { io } returns testCoroutineDispatcher
}