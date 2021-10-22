package com.summer.newsapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.summer.newsapp.R
import com.summer.newsapp.databinding.ActivityNewsFullBinding

class FullNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsFullBinding
    private lateinit var mTitle: String
    private lateinit var mContent: String
    private lateinit var mImageUrl: String
    private lateinit var mPublishedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsFullBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundling()
        initViews()
        initListeners()
    }

    private fun initListeners() {
        binding.btNewsClose.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews() {
        binding.tvFullNewsTitle.text = mTitle
        binding.tvNewsDescription.text = mContent
        binding.tvNewsDate.text = mPublishedDate
        Glide.with(this)
            .load(mImageUrl)
            .error(R.drawable.error_image)
            .into(binding.ivNewsImage)
    }

    private fun initBundling() {
        mTitle = intent.getStringExtra("title").orEmpty()
        mContent = intent.getStringExtra("content").orEmpty()
        mImageUrl = intent.getStringExtra("imageUrl").orEmpty()
        mPublishedDate = intent.getStringExtra("publishedDate").orEmpty()
    }


}