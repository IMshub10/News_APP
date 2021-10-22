package com.summer.newsapp.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
class NewsEntity(
    @PrimaryKey(autoGenerate = false) var id: String,
    var title: String,
    var description: String,
    var imageUrl: String,
    var publishedDate: String,
    var timestamp:Long
)