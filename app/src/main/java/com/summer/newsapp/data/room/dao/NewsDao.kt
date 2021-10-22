package com.summer.newsapp.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.summer.newsapp.data.room.model.NewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(newsEntity: NewsEntity)

    @Update
    fun update(newsEntity: NewsEntity)

    @Query("UPDATE news_table SET deleted=:deleted WHERE id=:id")
    fun delete(id: String, deleted: Boolean)

    @Query("DELETE FROM news_table")
    fun deleteAll()

    @Query("UPDATE news_table SET read=:read WHERE id=:id")
    fun updateArticle(id: String, read: Boolean)

    @Query("SELECT * FROM news_table WHERE deleted=:deleted ORDER BY  read, timestamp DESC  LIMIT 50")
    fun getAllArticles(deleted: Boolean): LiveData<List<NewsEntity>>
}