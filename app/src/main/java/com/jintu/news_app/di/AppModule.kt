
package com.jintu.news_app.di

import com.jintu.news_app.data.local.dao.ArticleDao
import com.jintu.news_app.data.remote.NewsApiService
import com.jintu.news_app.data.repository.NewsRepositoryImpl
import com.jintu.news_app.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApiService {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        api: NewsApiService,
        localArticleDao: ArticleDao
    ): NewsRepository {
        return NewsRepositoryImpl(api, localArticleDao)
    }
}
