package com.ensibuuko.android_dev_coding_assigment.data.repository

import com.ensibuuko.android_dev_coding_assigment.api.UserApi
import com.ensibuuko.android_dev_coding_assigment.data.models.User
import com.ensibuuko.android_dev_coding_assigment.data.models.UserDao
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val connectionDetector: ConnectionDetector
) : UserRepository {
    override suspend fun fetchUsers(): Resource<List<User>> {
        return if (connectionDetector.isNetworkAvailable()) {
            try {
                val users = userApi.getUsers()
                withContext(Dispatchers.IO) {
                    for (user in users)
                        userDao.insertUser(user)
                }
                Resource.success(users)
            } catch (e: Exception) {
                Resource.error(e.message?:"Fetching Users Error!")
            }
        } else {
            val data = getLocalUsers()
            Resource.success(data)
        }
    }

    override suspend fun getLocalUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            userDao.getUsers()
        }
    }
}