package com.ensibuuko.android_dev_coding_assigment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.databinding.CommentRowBinding

class CommentsAdapter(val commentListener: CommentListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.equals(newItem)
        }
    }

    var comments: List<Comment> = emptyList()
    private val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: CommentRowBinding = CommentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentItem(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CommentItem).bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun emptyComments(): Boolean {
        return differ.currentList.isEmpty()
    }

    fun submitList(list: List<Comment>) {
        comments = list
        differ.submitList(list)
    }

    inner class CommentItem internal constructor(binding: CommentRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mBinding: CommentRowBinding = binding
        fun bind(comment: Comment) = with(itemView) {
            mBinding.name.text = comment.name
            mBinding.email.text = comment.email
            mBinding.authorInitial.text = comment.name.first().toString()
            mBinding.comment.text = comment.body

            mBinding.editComment.setOnClickListener { commentListener.editComment(comment) }
            mBinding.deleteComment.setOnClickListener { commentListener.deleteComment(comment) }
        }
    }
}

interface CommentListener {
    fun editComment(comment: Comment)
    fun deleteComment(comment: Comment)
}