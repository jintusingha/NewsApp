package com.jintu.news_app.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey


import androidx.room.Database
import androidx.room.RoomDatabase
import com.jintu.news_app.data.local.dao.ArticleDao
import com.jintu.news_app.data.local.entity.ArticleEntity


@Database(entities = [ArticleEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}