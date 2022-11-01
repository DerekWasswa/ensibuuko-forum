package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ensibuuko.android_dev_coding_assigment.adapters.PostSelection
import com.ensibuuko.android_dev_coding_assigment.adapters.PostsAdapter
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.databinding.PostsBinding
import com.ensibuuko.android_dev_coding_assigment.utils.Constants.TAG
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import com.ensibuuko.android_dev_coding_assigment.utils.gone
import com.ensibuuko.android_dev_coding_assigment.utils.visible
import com.ensibuuko.android_dev_coding_assigment.viewmodels.CommentsViewModel
import com.ensibuuko.android_dev_coding_assigment.viewmodels.PostsViewModel
import com.ensibuuko.android_dev_coding_assigment.viewmodels.UsersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PostsFragment : Fragment() {

    private var _binding: PostsBinding? = null
    private val binding get() = _binding!!

    private val usersViewModel by viewModel<UsersViewModel>()
    private val commentsViewModel by viewModel<CommentsViewModel>()
    private val postsViewModel by viewModel<PostsViewModel>()

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PostsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPostViews()

        observePostOperation()
        observePosts()
        observeComments()
        observeUsers()
        usersViewModel.fetchUsers()
    }

    private fun initPostViews() {
        postsAdapter = PostsAdapter(postSelection)
        binding.postsList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = postsAdapter
        }

        binding.addPostBtn.setOnClickListener {
            if (postsAdapter.users.isNotEmpty()) {
                findNavController().navigate(PostsFragmentDirections.actionPostToAddPost(postsAdapter.users.first().id, null))
            }
        }
    }

    private val postSelection: PostSelection = object : PostSelection {
        override fun selectedPost(post: Post) {
            val user = postsAdapter.users.find { it.id == post.userId }
            findNavController().navigate(PostsFragmentDirections.actionPostToPostDetails(post, user, postsAdapter.comments.toTypedArray()))
        }

        override fun updatePost(post: Post) {
            findNavController().navigate(PostsFragmentDirections.actionPostToAddPost(postsAdapter.users.first().id, post))
        }

        override fun deletePost(post: Post) {
            postsViewModel.deletePost(post)
        }
    }

    private fun observePosts() {
        postsViewModel.posts.observe(viewLifecycleOwner) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {
                    binding.addPostBtn.gone()
                    binding.postsList.gone()
                    binding.shimmerViewContainer.visible()
                    binding.shimmerViewContainer.startShimmer()
                }
                Resource.Status.SUCCESS -> {
                    data?.let { mData ->
                        binding.shimmerViewContainer.stopShimmer()
                        binding.shimmerViewContainer.gone()
                        binding.addPostBtn.visible()
                        binding.postsList.visible()

                        postsAdapter.submitList(mData)
                        emptyPosts()
                    }
                }
                Resource.Status.ERROR -> {
                    binding.shimmerViewContainer.gone()
                    binding.postsList.gone()
                    binding.addPostBtn.visible()
                    binding.emptyPosts.visible()
                    error?.let { mError ->
                        Log.d(TAG, mError)
                    }
                }
            }
        }
    }

    private fun emptyPosts() {
        if (postsAdapter.emptyPosts()) {
            binding.shimmerViewContainer.gone()
            binding.postsList.gone()
            binding.addPostBtn.visible()
            binding.emptyPosts.visible()
        }
    }

    private fun observeComments() {
        commentsViewModel.comments.observe(viewLifecycleOwner) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {
                    binding.addPostBtn.gone()
                    binding.postsList.gone()
                    binding.shimmerViewContainer.visible()
                    binding.shimmerViewContainer.startShimmer()
                }
                Resource.Status.SUCCESS -> {
                    data?.let { mData ->
                        postsAdapter.submitComments(mData)
                        postsViewModel.fetchPosts()
                    }
                }
                Resource.Status.ERROR -> {
                    binding.shimmerViewContainer.gone()
                    binding.postsList.gone()
                    binding.addPostBtn.visible()
                    binding.emptyPosts.visible()
                    error?.let { mError ->
                        Log.d(TAG, mError)
                    }
                }
            }
        }
    }

    private fun observeUsers() {
        usersViewModel.users.observe(viewLifecycleOwner) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {
                    binding.addPostBtn.gone()
                    binding.postsList.gone()
                    binding.shimmerViewContainer.visible()
                    binding.shimmerViewContainer.startShimmer()
                }
                Resource.Status.SUCCESS -> {
                    data?.let { mData ->
                        postsAdapter.submitUsers(mData)
                        commentsViewModel.fetchComments()
                    }
                }
                Resource.Status.ERROR -> {
                    binding.shimmerViewContainer.gone()
                    binding.postsList.gone()
                    binding.addPostBtn.visible()
                    binding.emptyPosts.visible()
                    error?.let { mError ->
                        Log.d(TAG, mError)
                    }
                }
            }
        }
    }

    private fun observePostOperation() {
        postsViewModel.postOperations.observe(viewLifecycleOwner) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    data?.let { _ ->
                        postsViewModel.fetchPosts()
                        Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error, Try again!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}