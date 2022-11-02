package com.ensibuuko.android_dev_coding_assigment.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatcher {
    val main : CoroutineDispatcher = Dispatchers.Main
    val io : CoroutineDispatcher = Dispatchers.IO
}