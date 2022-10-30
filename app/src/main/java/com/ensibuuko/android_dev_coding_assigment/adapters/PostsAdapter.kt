package com.ensibuuko.android_dev_coding_assigment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.databinding.PostRowBinding

class PostsAdapter(val postSelection: PostSelection) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.equals(newItem)
        }
    }

    var posts: List<Post> = emptyList()
    private val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: PostRowBinding = PostRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostItem(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostItem).bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun emptyPosts(): Boolean {
        return differ.currentList.isEmpty()
    }

    fun submitList(list: List<Post>) {
        posts = list
        differ.submitList(list)
    }

    inner class PostItem internal constructor(binding: PostRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mBinding: PostRowBinding = binding
        fun bind(post: Post) = with(itemView) {
            mBinding.root.setOnClickListener { postSelection.selectedPost(post) }

            mBinding.body.text = post.body
            mBinding.title.text = post.title
            mBinding.comments.text = "0 Comments"
        }
    }
}

interface PostSelection {
    fun selectedPost(post: Post)
}