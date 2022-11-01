package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ensibuuko.android_dev_coding_assigment.databinding.PostDetailsBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ensibuuko.android_dev_coding_assigment.adapters.CommentListener
import com.ensibuuko.android_dev_coding_assigment.adapters.CommentsAdapter
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.data.models.User
import com.ensibuuko.android_dev_coding_assigment.utils.Constants
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import com.ensibuuko.android_dev_coding_assigment.utils.gone
import com.ensibuuko.android_dev_coding_assigment.utils.visible
import com.ensibuuko.android_dev_coding_assigment.viewmodels.CommentsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PostDetailsFragment : Fragment() {

    private var _binding: PostDetailsBinding? = null

    private val binding get() = _binding!!

    private val commentsViewModel by viewModel<CommentsViewModel>()

    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var selectedPost: Post
    private lateinit var user: User
    private var localComments: List<Comment> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PostDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCommentsViews()
        initPostData()
        commentsViewModel.resetPostComments()
        observePostComments()
        observeCommentOperations()
        commentsViewModel.fetchPostComments(selectedPost.id.toString())
        binding.addComments.setOnClickListener { findNavController().navigate(PostDetailsFragmentDirections.actionPostDetailsToAddComment(user, selectedPost.id, null)) }
        binding.author.setOnClickListener { viewPostAuthorDetails() }
        binding.name.setOnClickListener { viewPostAuthorDetails() }
        binding.username.setOnClickListener { viewPostAuthorDetails() }
    }

    private fun viewPostAuthorDetails() {
        findNavController().navigate(PostDetailsFragmentDirections.actionPostDetailsToAuthorProfile(user))
    }

    private fun initCommentsViews() {
        commentsAdapter = CommentsAdapter(commentsListener)
        binding.commentsList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = commentsAdapter
        }
    }

    private val commentsListener = object : CommentListener {
        override fun editComment(comment: Comment) {
            findNavController().navigate(PostDetailsFragmentDirections.actionPostDetailsToAddComment(user, selectedPost.id, comment))
        }

        override fun deleteComment(comment: Comment) {
            commentsViewModel.deleteComment(comment)
        }
    }

    private fun initPostData() {
        arguments?.let { mArgs ->
            selectedPost = PostDetailsFragmentArgs.fromBundle(mArgs).post
            binding.title.text = selectedPost.title
            binding.body.text = selectedPost.body

            PostDetailsFragmentArgs.fromBundle(mArgs).user?.let { _user ->
                user = _user

                binding.username.text = _user.username
                val nameLabel = "${_user.name} - ${_user.email}"
                binding.name.text = nameLabel
                binding.authorInitial.text = _user.username.first().toString()
            }

            localComments = PostDetailsFragmentArgs.fromBundle(mArgs).comments.toList()
                .mapNotNull { it }
        }
    }

    private fun observePostComments() {
        commentsViewModel.postComments.observe(viewLifecycleOwner) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {
                    binding.commentsList.gone()
                }
                Resource.Status.SUCCESS -> {
                    data?.let { mData ->
                        binding.commentsList.visible()
                        if (mData.isNotEmpty()) {
                            refreshComments(mData)
                        } else {
                            val localPostComments = localComments.filter { it.postId == selectedPost.id }
                            refreshComments(localPostComments)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    binding.commentsList.gone()
                    error?.let { mError ->
                        Log.d(Constants.TAG, mError)
                    }
                }
            }
        }
    }

    private fun observeCommentOperations() {
        commentsViewModel.commentOperations.observe(viewLifecycleOwner) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    data?.let { _ ->
                        commentsViewModel.fetchPostComments(selectedPost.id.toString())
                        Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error, Try again!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun refreshComments(comments: List<Comment>) {
        val commentCount = "${comments.size} Comments"
        binding.comments.text = commentCount
        commentsAdapter.submitList(comments)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}