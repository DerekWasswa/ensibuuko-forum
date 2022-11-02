package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.databinding.AddPostBottomsheetBinding
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import com.ensibuuko.android_dev_coding_assigment.viewmodels.PostsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPostFragment : BottomSheetDialogFragment() {

    private var _binding: AddPostBottomsheetBinding? = null

    private val binding get() = _binding!!

    private val postsViewModel by viewModel<PostsViewModel>()

    private var userId: Long = 0L
    private var updatePost: Post? = null

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AddPostBottomsheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let { mArgs ->
            userId = AddPostFragmentArgs.fromBundle(mArgs).userId
            updatePost = AddPostFragmentArgs.fromBundle(mArgs).post

            updatePost?.let { _updatePost ->
                binding.title.text = Editable.Factory.getInstance().newEditable(_updatePost.title)
                binding.body.text = Editable.Factory.getInstance().newEditable(_updatePost.body)
                val txtUpdatePost = "Update Post"
                binding.sendPostBtn.text = txtUpdatePost
            }
        }

        initViews()
        observeAddingPost()
    }

    private fun observeAddingPost() {
        postsViewModel.postOperations.observe(viewLifecycleOwner) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {
                    binding.loading = true
                }
                Resource.Status.SUCCESS -> {
                    data?.let { mData ->
                        binding.sentSuccessful = mData
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error, Try again!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViews() {
        binding.sendPostBtn.setOnClickListener {
            val title = binding.title.text.toString()
            val body = binding.body.text.toString()

            if (body.isEmpty() || title.isEmpty()) {
                Toast.makeText(requireContext(), "Post title or body can not be empty!", Toast.LENGTH_SHORT).show()
            } else {
                updatePost?.let { _updatePost ->
                    val post = Post(_updatePost.id, userId, title, body, false)
                    postsViewModel.resetPostOperations()
                    postsViewModel.updatePost(post)
                }?: kotlin.run {
                    val post = Post(System.currentTimeMillis(), userId, title, body, false)
                    binding.loading = true
                    postsViewModel.addPost(post)
                }
            }
        }

        binding.closeBtn.setOnClickListener { dismiss() }
        binding.close.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}