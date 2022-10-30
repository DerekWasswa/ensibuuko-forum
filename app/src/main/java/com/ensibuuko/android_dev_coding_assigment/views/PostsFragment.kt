package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.adapters.PostSelection
import com.ensibuuko.android_dev_coding_assigment.adapters.PostsAdapter
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.databinding.PostsBinding
import com.ensibuuko.android_dev_coding_assigment.utils.Constants.TAG
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import com.ensibuuko.android_dev_coding_assigment.utils.gone
import com.ensibuuko.android_dev_coding_assigment.utils.visible
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
        observePosts()
    }

    private fun initPostViews() {
        postsAdapter = PostsAdapter(postSelection)
        binding.postsList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = postsAdapter
        }

        binding.addPostBtn.setOnClickListener { findNavController().navigate(PostsFragmentDirections.actionPostToAddPost()) }
    }

    private val postSelection: PostSelection = object : PostSelection {
        override fun selectedPost(post: Post) {
            findNavController().navigate(PostsFragmentDirections.actionPostToPostDetails(post))
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
        postsViewModel.fetchPosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}