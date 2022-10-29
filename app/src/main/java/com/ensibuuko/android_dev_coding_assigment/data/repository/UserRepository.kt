package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.data.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun fetchUsers(): Flow<List<User>>
    suspend fun getLocalUsers(): Flow<List<User>>
    suspend fun insertUsers(users: List<User>): Flow<Unit>
}