package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.AddPostBottomsheetBinding
import com.ensibuuko.android_dev_coding_assigment.viewmodels.PostsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPostFragment : BottomSheetDialogFragment() {

    private var _binding: AddPostBottomsheetBinding? = null

    private val binding get() = _binding!!

    private val postsViewModel by viewModel<PostsViewModel>()

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AddPostBottomsheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sendPostBtn.setOnClickListener {
            val title = binding.title.text.toString()
            val body = binding.body.text.toString()

            if (body.isEmpty() || title.isEmpty()) {
                Snackbar.make(it, "Post title or body can not be empty!", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
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