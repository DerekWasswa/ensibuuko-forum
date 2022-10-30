package com.ensibuuko.android_dev_coding_assigment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.data.repository.PostsRepository
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsViewModel(
    private val postsRepository: PostsRepository,
    private val connectionDetector: ConnectionDetector
) : ViewModel() {

    private val _posts = MutableLiveData<Resource<List<Post>>>()
    val posts : LiveData<Resource<List<Post>>>
        get() = _posts

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _posts.run {
                postValue(Resource.loading())

                if (connectionDetector.isNetworkAvailable()) {
                    postsRepository.fetchPosts()
                        .catch { _ ->
                            postsRepository.getLocalPosts()
                                .collectLatest { postValue(Resource.success(it)) }
                        }
                        .collectLatest {
                            withContext(Dispatchers.IO) { postsRepository.insertPosts(it) }
                            postValue(Resource.success(it))
                        }
                } else {
                    postsRepository.getLocalPosts()
                        .collectLatest { postValue(Resource.success(it)) }
                }
            }
        }
    }
}