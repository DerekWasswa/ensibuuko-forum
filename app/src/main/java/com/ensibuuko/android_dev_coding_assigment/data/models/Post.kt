package com.ensibuuko.android_dev_coding_assigment.data.models

import kotlinx.serialization.*

@Serializable
data class Post (
    @SerialName("userId")
    val userID: Long,
    val id: Long,
    val title: String,
    val body: String
)