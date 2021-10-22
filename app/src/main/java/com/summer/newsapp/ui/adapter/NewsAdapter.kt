package com.summer.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.summer.newsapp.R
import com.summer.newsapp.data.room.model.NewsEntity

class NewsAdapter(private val context: Context) :
    ListAdapter<NewsEntity, NewsAdapter.NewsHolder>(DIFF_CALLBACK) {

    private lateinit var newsAdapterClickListener: NewsAdapterClickListener

    fun setNewsRecyclerViewClickListener(newsAdapterClickListener: NewsAdapterClickListener) {
        this.newsAdapterClickListener = newsAdapterClickListener
    }

    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rlItemNews = itemView.findViewById<RelativeLayout>(R.id.rl_item_news)
        private val ivNewsImage = itemView.findViewById<ShapeableImageView>(R.id.iv_news_image)
        private val tvNewsTitle = itemView.findViewById<AppCompatTextView>(R.id.tv_news_title)
        private val tvNewsDate = itemView.findViewById<AppCompatTextView>(R.id.tv_news_date)
        private val tvNewsDescription =
            itemView.findViewById<AppCompatTextView>(R.id.tv_news_description)

        fun bind(newsEntity: NewsEntity) {
            if (newsEntity.read){
                rlItemNews.alpha = 0.35F
            }else{
                rlItemNews.alpha = 1.0F
            }
            tvNewsTitle.text = newsEntity.title
            tvNewsDate.text = newsEntity.publishedDate
            tvNewsDescription.text = newsEntity.description
            Glide.with(context)
                .load(newsEntity.imageUrl)
                .error(R.drawable.error_image)
                .into(ivNewsImage)

            //handling recyclerview item events
            itemView.setOnClickListener {
                newsAdapterClickListener.onItemClick(ivNewsImage, newsEntity)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsHolder(LayoutInflater.from(context).inflate(R.layout.item_news, parent, false))

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(
                    oldItem: NewsEntity,
                    newItem: NewsEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                            && oldItem.read == newItem.read
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
                            && oldItem.read == newItem.read
                            && oldItem.articleUrl == newItem.articleUrl
                }
            }
    }

    interface NewsAdapterClickListener {
        fun onItemClick(view: View, newsEntity: NewsEntity)
    }
}