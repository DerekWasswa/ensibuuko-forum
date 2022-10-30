package com.ensibuuko.android_dev_coding_assigment.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.*

@Entity(tableName = "posts")
@Serializable
@Parcelize
data class Post (
    @ColumnInfo(name = "userId")
    @SerialName("userId")
    val userID: Long,

    @PrimaryKey val id: Long,

    val title: String,
    val body: String,
    val synced: Boolean = false,
) : Parcelable
