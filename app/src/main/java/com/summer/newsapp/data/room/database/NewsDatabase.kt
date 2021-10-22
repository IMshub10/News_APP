package com.summer.newsapp.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.summer.newsapp.data.room.dao.NewsDao
import com.summer.newsapp.data.room.model.NewsEntity

@Database(entities = [NewsEntity::class], version = 7, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao

    companion object {
        private var instance: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {
            val temp = instance
            if (temp != null) {
                return temp
            } else {
                synchronized(this) {
                    val inst = Room.databaseBuilder(
                        context,
                        NewsDatabase::class.java,
                        "news_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    instance = inst
                    return inst
                }
            }
        }
    }
}