package com.ensibuuko.android_dev_coding_assigment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.data.models.Comment
import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import com.ensibuuko.android_dev_coding_assigment.data.models.User
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

    var users: List<User> = emptyList()
    fun submitUsers(list: List<User>) {
        users = list
    }

    var comments: List<Comment> = emptyList()
    fun submitComments(list: List<Comment>) {
        comments = list
    }

    inner class PostItem internal constructor(binding: PostRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mBinding: PostRowBinding = binding
        fun bind(post: Post) = with(itemView) {
            mBinding.root.setOnClickListener { postSelection.selectedPost(post) }
            mBinding.deletePost.setOnClickListener { postSelection.deletePost(post) }
            mBinding.editPost.setOnClickListener { postSelection.updatePost(post) }

            val user = users.find { it.id == post.userId }
            val postComments = comments.filter { it.postId == post.id }
            val commentCount = "${postComments.size} Comments"

            mBinding.body.text = post.body
            mBinding.title.text = post.title.replaceFirstChar { it.uppercase() }
            mBinding.titleInitial.text = post.title.first().toString()
            mBinding.comments.text = commentCount

            user?.let { _user ->
                mBinding.user.text = _user.name
            }

        }
    }
}

interface PostSelection {
    fun selectedPost(post: Post)
    fun updatePost(post: Post)
    fun deletePost(post: Post)
}