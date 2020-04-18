package com.hammak.kp.ytwatcher

import Root
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlistItems")
    fun getPlaylistItemsInfo(
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String
    ): Observable<Root>

    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}