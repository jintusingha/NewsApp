package com.jintu.news_app.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService{


    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("country") country:String="us",
        @Query("apiKey") apikey:String
    ):NewsResponse








}