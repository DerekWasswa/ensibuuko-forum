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

class CommentsViewModel(
    private val commentRepository: CommentRepository,
    private val connectionDetector: ConnectionDetector
) : ViewModel() {

    private val _comments = MutableLiveData<Resource<List<Comment>>>()
    val comments : LiveData<Resource<List<Comment>>>
        get() = _comments

    fun fetchComments() {
        viewModelScope.launch(Dispatchers.IO) {
            _comments.run {
                postValue(Resource.loading())

                if (connectionDetector.isNetworkAvailable()) {
                    commentRepository.fetchComments()
                        .catch { _ ->
                            commentRepository.getLocalComments()
                                .collectLatest { postValue(Resource.success(it)) }
                        }
                        .collectLatest {
                            commentRepository.insertComments(it)
                            postValue(Resource.success(it))
                        }
                } else {
                    commentRepository.getLocalComments()
                        .collectLatest { postValue(Resource.success(it)) }
                }
            }
        }
    }

    private val _postComments = MutableLiveData<Resource<List<Comment>>>()
    val postComments : LiveData<Resource<List<Comment>>>
        get() = _postComments

    fun resetPostComments() {
        _postComments.postValue(Resource.success(emptyList()))
    }

    fun fetchPostComments(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _postComments.run {
                postValue(Resource.loading())

                if (connectionDetector.isNetworkAvailable()) {
                    commentRepository.fetchPostComments(postId.toString())
                        .catch { _ ->
                            commentRepository.getLocalPostComments(postId)
                                .collectLatest { postValue(Resource.success(it)) }
                        }
                        .collectLatest {
                            commentRepository.insertComments(it)
                            postValue(Resource.success(it))
                        }
                } else {
                    commentRepository.getLocalPostComments(postId)
                        .collectLatest { postValue(Resource.success(it)) }
                }
            }
        }
    }

    private val _commentOperations = MutableLiveData<Resource<Boolean>>()
    val commentOperations : LiveData<Resource<Boolean>>
        get() = _commentOperations

    fun addComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            _commentOperations.run {
                postValue(Resource.loading())
                if (connectionDetector.isNetworkAvailable()) {
                    commentRepository.addRemoteComment(comment)
                        .catch {
                            commentRepository.insertComment(comment)
                            postValue(Resource.success(true))
                        }
                        .collectLatest { postValue(Resource.success(true)) }
                } else {
                    commentRepository.insertComment(comment)
                    postValue(Resource.success(true))
                }
            }
        }

    }

    fun updateComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            _commentOperations.run {
                postValue(Resource.loading())
                if (connectionDetector.isNetworkAvailable()) {
                    commentRepository.updateRemoteComment(comment)
                        .catch {
                            commentRepository.updateLocalComment(comment)
                            postValue(Resource.success(true))
                        }
                        .collectLatest { postValue(Resource.success(true)) }
                } else {
                    commentRepository.updateLocalComment(comment)
                    postValue(Resource.success(true))
                }
            }
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            _commentOperations.run {
                if (connectionDetector.isNetworkAvailable()) {
                    commentRepository.deleteRemoteComment(comment.id.toString())
                        .catch {
                            commentRepository.deleteLocalComment(comment)
                            postValue(Resource.success(true))
                        }
                        .collectLatest { postValue(Resource.success(true)) }
                } else {
                    commentRepository.deleteLocalComment(comment)
                    postValue(Resource.success(true))
                }
            }
        }
    }

    fun resetCommentOperations() {
        _commentOperations.postValue(Resource.success(false))
    }

}