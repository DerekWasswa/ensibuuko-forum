package com.ensibuuko.android_dev_coding_assigment.data.models

import kotlinx.serialization.*

@Serializable
data class Comment (
    @SerialName("postId")
    val postID: Long,

    val id: Long,
    val name: String,
    val email: String,
    val body: String
)
