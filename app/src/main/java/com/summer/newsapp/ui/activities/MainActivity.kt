package com.summer.newsapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.summer.newsapp.databinding.ActivityMainBinding
import com.summer.newsapp.ui.adapter.NewsAdapter
import com.summer.newsapp.utils.ServiceAlarmHandler
import com.summer.newsapp.viewmodels.NewsViewModel
import com.summer.newsapp.viewmodels.factory.NewsViewModelFactory

class MainActivity : AppCompatActivity() {

    //private val tag = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var newsAdapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initViewModel()
        observeViewModel()
        initFetchServiceAlarmManger()
    }

    private fun initFetchServiceAlarmManger() {
        val alarmManager = ServiceAlarmHandler(this)
        alarmManager.cancelAlarmManager()
        alarmManager.setAlarmManager()
    }

    private fun initViews() {
        newsAdapter = NewsAdapter(this)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMainFeed.adapter = newsAdapter
        binding.rvMainFeed.layoutManager = layoutManager
    }

    private fun observeViewModel() {
        newsViewModel.getAllNews().observe(this, {
            newsAdapter.submitList(it)
        })
    }

    private fun initViewModel() {
        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(application)
        ).get(NewsViewModel::class.java)
    }


}