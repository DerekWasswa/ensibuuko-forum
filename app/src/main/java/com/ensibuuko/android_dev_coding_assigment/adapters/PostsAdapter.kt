package com.ensibuuko.android_dev_coding_assigment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.models.Post

class PostsAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.equals(newItem)
        }
    }

    var reviews: List<Post> = emptyList()
    private val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostItem(LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false), context)
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
        reviews = list
        differ.submitList(list)
    }

    class PostItem constructor(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {

        fun bind(review: Post) = with(itemView) {

        }
    }
}