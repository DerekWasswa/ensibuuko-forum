package com.ensibuuko.android_dev_coding_assigment.data.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getPosts(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>): Flow<Unit>

    @Delete
    fun deletePost(post: Post)

    @Update
    fun updatePost(post: Post)

}