package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.AddCommentBottomsheetBinding
import com.ensibuuko.android_dev_coding_assigment.viewmodels.CommentsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCommentFragment : BottomSheetDialogFragment() {

    private var _binding: AddCommentBottomsheetBinding? = null

    private val binding get() = _binding!!

    private val commentsViewModel by viewModel<CommentsViewModel>()

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AddCommentBottomsheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sendCommentBtn.setOnClickListener {
            val body = binding.body.text.toString()

            if (body.isEmpty()) {
                Snackbar.make(it, "Comment body can not be empty!", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
            } else {

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