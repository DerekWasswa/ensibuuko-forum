package com.ensibuuko.android_dev_coding_assigment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment

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
        return CommentItem(LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false))
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

    inner class CommentItem internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Comment) = with(itemView) {

        }
    }
}

interface CommentListener {
    fun editComment(comment: Comment)
    fun deleteComment(comment: Comment)
}