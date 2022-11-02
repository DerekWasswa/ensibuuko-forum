package com.ensibuuko.android_dev_coding_assigment

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class EnsibuukoTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, EnsibuukoTestApplication::class.java.name, context)
    }

}