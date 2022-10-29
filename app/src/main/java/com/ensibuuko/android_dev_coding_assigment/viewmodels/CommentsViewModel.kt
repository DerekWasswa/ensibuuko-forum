package com.ensibuuko.android_dev_coding_assigment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.data.repository.CommentRepository
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsViewModel(
    private val commentRepository: CommentRepository,
    private val connectionDetector: ConnectionDetector
) : ViewModel() {

    private val _comments = MutableLiveData<Resource<List<Comment>>>()
    val comments : LiveData<Resource<List<Comment>>>
        get() = _comments

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _comments.run {
                postValue(Resource.loading())

                if (connectionDetector.isNetworkAvailable()) {
                    commentRepository.fetchComments()
                        .catch { e ->
                            postValue(Resource.error(e.message?: "fetching comments error"))
                        }
                        .collectLatest {
                            withContext(Dispatchers.IO) { commentRepository.insertComments(it) }
                            postValue(Resource.success(it))
                        }
                } else {
                    commentRepository.getLocalComments()
                        .collectLatest { postValue(Resource.success(it)) }
                }
            }
        }
    }

}