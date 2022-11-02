package com.ensibuuko.android_dev_coding_assigment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.data.models.User
import com.ensibuuko.android_dev_coding_assigment.data.repository.UserRepository
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import com.ensibuuko.android_dev_coding_assigment.utils.CoroutineDispatcher
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel(
    private val userRepository: UserRepository,
    private val connectionDetector: ConnectionDetector,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _users = MutableLiveData<Resource<List<User>>>()
    val users : LiveData<Resource<List<User>>>
        get() = _users

    fun fetchUsers() {
        viewModelScope.launch(coroutineDispatcher.io) {
            _users.run {
                postValue(Resource.loading())

                if (connectionDetector.isNetworkAvailable()) {
                    userRepository.fetchUsers()
                        .catch { _ ->
                            userRepository.getLocalUsers()
                                .collectLatest { postValue(Resource.success(it)) }
                        }
                        .collectLatest {
                            userRepository.insertUsers(it)
                            postValue(Resource.success(it))
                        }
                } else {
                    userRepository.getLocalUsers()
                        .collectLatest { postValue(Resource.success(it)) }
                }
            }
        }
    }

}