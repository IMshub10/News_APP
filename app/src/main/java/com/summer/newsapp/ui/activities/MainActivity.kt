package com.summer.newsapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.summer.newsapp.data.room.model.NewsEntity
import com.summer.newsapp.databinding.ActivityMainBinding
import com.summer.newsapp.ui.adapter.NewsAdapter
import com.summer.newsapp.utils.ServiceAlarmHandler
import com.summer.newsapp.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@AndroidEntryPoint
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
        clickListeners()
        setUpItemTouchHelper()
        initFetchArticles()
    }

    private fun initViewModel() {
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    private fun initFetchArticles() {
        binding.pgFetchNews.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            newsViewModel.getAllArticles()
            withContext(Dispatchers.Main) {
                binding.pgFetchNews.isVisible = false
            }
        }
    }

    private fun clickListeners() {
        newsAdapter.setNewsRecyclerViewClickListener(object : NewsAdapter.NewsAdapterClickListener {
            override fun onItemClick(view: View, newsEntity: NewsEntity) {
                val actvityIntent = Intent(this@MainActivity, FullNewsActivity::class.java)
                actvityIntent.putExtra("title", newsEntity.title)
                if (newsEntity.content.trim().isEmpty()) {
                    actvityIntent.putExtra(
                        "content",
                        newsEntity.description + "\n" + newsEntity.articleUrl
                    )
                } else {
                    actvityIntent.putExtra(
                        "content",
                        newsEntity.content + "\n" + newsEntity.articleUrl
                    )
                }
                actvityIntent.putExtra("imageUrl", newsEntity.imageUrl)
                actvityIntent.putExtra("publishedDate", newsEntity.publishedDate)
                val options: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@MainActivity,
                        view,
                        "image_transition"
                    )
                startActivity(actvityIntent, options.toBundle())
            }
        })
    }

    private fun updateArticleInDB(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                newsViewModel.updateArticle(
                    id,
                    true
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteArticleInDB(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                newsViewModel.deleteArticle(
                    id,
                    true
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setUpItemTouchHelper() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    val swipedPosition = viewHolder.adapterPosition
                    if (swipeDir == ItemTouchHelper.LEFT) {
                        updateArticleInDB(newsAdapter.currentList[0].id)
                    } else {
                        deleteArticleInDB(
                            newsAdapter.currentList[swipedPosition].id
                        )
                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMainNews)
    }

    private fun initFetchServiceAlarmManger() {
        val alarmManager = ServiceAlarmHandler(this)
        alarmManager.cancelAlarmManager()
        alarmManager.setAlarmManager()
    }

    private fun initViews() {
        newsAdapter = NewsAdapter(this)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMainNews.adapter = newsAdapter
        binding.rvMainNews.layoutManager = layoutManager
    }

    private fun observeViewModel() {
        newsViewModel.getAllArticles(false).observe(this, {
            newsAdapter.submitList(it)
        })
    }

}