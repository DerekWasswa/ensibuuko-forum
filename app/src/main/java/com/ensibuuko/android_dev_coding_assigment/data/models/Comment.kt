package com.ensibuuko.android_dev_coding_assigment.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.*

@Entity(tableName = "comments")
@Serializable
@Parcelize
data class Comment (
    @PrimaryKey val id: Long,

    var postId: Long,
    val name: String,
    val email: String,
    val body: String,
    val synced: Boolean = true,
) : Parcelable
