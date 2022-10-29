package com.ensibuuko.android_dev_coding_assigment.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.*

@Entity(tableName = "comments")
@Serializable
data class Comment (
    @ColumnInfo(name = "postId")
    @SerialName("postId")
    val postID: Long,

    @PrimaryKey val id: Long,

    val name: String,
    val email: String,
    val body: String,
    val synced: Boolean = false,
)
