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
                                .collectLatest { postValue(Resource.success(it.reversed())) }
                        }
                        .collectLatest {
                            postsRepository.insertPosts(it)
                            postValue(Resource.success(it.reversed()))
                        }
                } else {
                    postsRepository.getLocalPosts()
                        .collectLatest { postValue(Resource.success(it.reversed())) }
                }
            }
        }
    }

    private val _postOperations = MutableLiveData<Resource<Boolean>>()
    val postOperations : LiveData<Resource<Boolean>>
        get() = _postOperations

    fun addPost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            _postOperations.run {
                postValue(Resource.loading())
                if (connectionDetector.isNetworkAvailable()) {
                    postsRepository.addRemotePost(post)
                        .catch {
                            postsRepository.insertPost(post)
                            postValue(Resource.success(true))
                        }
                        .collectLatest { postValue(Resource.success(true)) }
                } else {
                    postsRepository.insertPost(post)
                    postValue(Resource.success(true))
                }
            }
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            _postOperations.run {
                postValue(Resource.loading())
                if (connectionDetector.isNetworkAvailable()) {
                    postsRepository.updateRemotePost(post)
                        .catch {
                            postsRepository.updateLocalPost(post)
                            postValue(Resource.success(true))
                        }
                        .collectLatest { postValue(Resource.success(true)) }
                } else {
                    postsRepository.updateLocalPost(post)
                    postValue(Resource.success(true))
                }
            }
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            _postOperations.run {
                if (connectionDetector.isNetworkAvailable()) {
                    postsRepository.deleteRemotePost(post.id.toString())
                        .catch {
                            postsRepository.deleteLocalPost(post)
                            postValue(Resource.success(true))
                        }
                        .collectLatest { postValue(Resource.success(true)) }
                } else {
                    postsRepository.deleteLocalPost(post)
                    postValue(Resource.success(true))
                }
            }
        }
    }

    fun resetPostOperations() {
        _postOperations.postValue(Resource.success(false))
    }
}