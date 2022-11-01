package com.ensibuuko.android_dev_coding_assigment.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.*

@Entity(tableName = "users")
@Serializable
@Parcelize
data class User (
    @PrimaryKey val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
) : Parcelable

@Serializable
@Parcelize
data class Address (
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String
) : Parcelable

@Serializable
@Parcelize
data class Company (
    val name: String,
    val catchPhrase: String,
    val bs: String
) : Parcelable

