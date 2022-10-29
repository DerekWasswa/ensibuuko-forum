package com.ensibuuko.android_dev_coding_assigment.data.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments")
    fun getComments(): List<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<Comment>): Flow<Unit>

    @Delete
    fun deleteComment(comment: Comment)

    @Update
    fun updateComment(comment: Comment)
}