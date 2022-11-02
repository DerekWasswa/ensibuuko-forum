package com.ensibuuko.android_dev_coding_assigment.repository

import com.ensibuuko.android_dev_coding_assigment.data.models.User
import com.ensibuuko.android_dev_coding_assigment.data.repository.UserRepository
import com.ensibuuko.android_dev_coding_assigment.dummyUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository: UserRepository {
    override suspend fun fetchUsers(): Flow<List<User>> = flow { emit(listOf(dummyUser)) }

    override suspend fun getLocalUsers(): Flow<List<User>> = flow { emit(listOf(dummyUser)) }

    override suspend fun insertUsers(users: List<User>) = Unit
}