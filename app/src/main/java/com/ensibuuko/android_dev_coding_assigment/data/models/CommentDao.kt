package com.ensibuuko.android_dev_coding_assigment.data.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments")
    fun getComments(): Flow<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<Comment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment)

    @Delete
    fun deleteComment(comment: Comment)

    @Update
    fun updateComment(comment: Comment)
}