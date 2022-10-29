package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.data.models.User
import com.ensibuuko.android_dev_coding_assigment.utils.Resource

interface UserRepository {
    suspend fun fetchUsers(): Resource<List<User>>
    suspend fun getLocalUsers(): List<User>
}