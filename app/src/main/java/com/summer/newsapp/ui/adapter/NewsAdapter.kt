package com.summer.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.summer.newsapp.R
import com.summer.newsapp.data.room.model.NewsEntity
import com.summer.newsapp.databinding.NewsItemBinding

class NewsAdapter(private val context: Context) :
    ListAdapter<NewsEntity, NewsAdapter.NewsHolder>(DIFF_CALLBACK) {

    inner class NewsHolder(val newsItemBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(newsItemBinding.root){
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.newsItemBinding.newsItemEntity = getItem(position)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(
                    oldItem: NewsEntity,
                    newItem: NewsEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: NewsEntity,
                    newItem: NewsEntity
                ): Boolean {
                    return oldItem.description == newItem.description
                            && oldItem.imageUrl == newItem.imageUrl
                            && oldItem.publishedDate == newItem.publishedDate
                            && oldItem.timestamp == newItem.timestamp
                            && oldItem.title == newItem.title
                }
            }
    }
}