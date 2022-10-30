package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ensibuuko.android_dev_coding_assigment.databinding.PostDetailsBinding
import androidx.navigation.fragment.findNavController
import com.ensibuuko.android_dev_coding_assigment.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PostDetailsFragment : Fragment() {

    private var _binding: PostDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PostDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPostData()

        binding.addComments.setOnClickListener {
            findNavController().navigate(R.id.action_PostDetails_to_AddComment)
        }
    }

    private fun initPostData() {
        arguments?.let { mArgs ->
            val selectedPost = PostDetailsFragmentArgs.fromBundle(mArgs).post
            binding.title.text = selectedPost.title
            binding.body.text = selectedPost.body
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}