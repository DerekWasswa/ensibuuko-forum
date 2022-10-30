package com.ensibuuko.android_dev_coding_assigment

import android.app.Application
import com.ensibuuko.android_dev_coding_assigment.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EnsibuukoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@EnsibuukoApplication)
            modules(listOf(databaseModule, apiModule, networkModule, viewModelModule))
        }
    }
}