package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.UserApi
import com.ensibuuko.android_dev_coding_assigment.data.models.User
import com.ensibuuko.android_dev_coding_assigment.data.models.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userDao: UserDao,
) : UserRepository {
    override suspend fun fetchUsers(): Flow<List<User>> = flow { emit(userApi.getUsers()) }

    override suspend fun getLocalUsers(): Flow<List<User>> = flow { emit(userDao.getUsers()) }

    override suspend fun insertUsers(users: List<User>): Flow<Unit> = flow {
        userDao.insertUsers(users)
        emit(Unit)
    }
}