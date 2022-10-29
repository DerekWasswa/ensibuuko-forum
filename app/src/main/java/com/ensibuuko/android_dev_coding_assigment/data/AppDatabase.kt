package com.ensibuuko.android_dev_coding_assigment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ensibuuko.android_dev_coding_assigment.data.models.*

@Database(entities = [User::class, Post::class, Comment::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}