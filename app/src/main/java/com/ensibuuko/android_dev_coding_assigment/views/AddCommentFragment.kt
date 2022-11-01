package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.data.models.User
import com.ensibuuko.android_dev_coding_assigment.databinding.AddCommentBottomsheetBinding
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import com.ensibuuko.android_dev_coding_assigment.viewmodels.CommentsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCommentFragment : BottomSheetDialogFragment() {

    private var _binding: AddCommentBottomsheetBinding? = null

    private val binding get() = _binding!!

    private val commentsViewModel by viewModel<CommentsViewModel>()

    private var postId: Long = 0L
    private lateinit var user: User
    private var updateComment: Comment? = null

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AddCommentBottomsheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let { mArgs ->
            postId = AddCommentFragmentArgs.fromBundle(mArgs).postId
            user = AddCommentFragmentArgs.fromBundle(mArgs).user
            updateComment = AddCommentFragmentArgs.fromBundle(mArgs).comment

            updateComment?.let { _updateComment ->
                binding.body.text = Editable.Factory.getInstance().newEditable(_updateComment.body)
                val txtUpdateComment = "Update Comment"
                binding.sendCommentBtn.text = txtUpdateComment
            }
        }

        initViews()
        observeAddComments()
    }

    private fun observeAddComments() {
        commentsViewModel.commentOperations.observe(viewLifecycleOwner) { (status, data, error) ->
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
        binding.sendCommentBtn.setOnClickListener {
            val body = binding.body.text.toString()

            if (body.isEmpty()) {
                Toast.makeText(requireContext(), "Comment body can not be empty!", Toast.LENGTH_SHORT).show()
            } else {
                updateComment?.let { _updateComment ->
                    val comment = Comment(_updateComment.id,
                        postId, user.name, user.email, body, false)
                    commentsViewModel.resetCommentOperations()
                    commentsViewModel.updateComment(comment)
                }?: kotlin.run {
                    val comment = Comment(System.currentTimeMillis(),
                        postId, user.name, user.email, body, false)
                    commentsViewModel.addComment(comment)
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