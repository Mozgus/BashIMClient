package com.berryjam.bashim.data

import com.berryjam.bashim.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BashImApiService {

// http://umorili.herokuapp.com/api

    @GET("api/get")
    fun searchQuotes(
            @Query("site") site: String,
            @Query("name") name: String,
            @Query("num") num: Int
    ): Observable<List<Quote>>

    @GET("api/sources")
    fun searchSources(): Observable<List<List<SourceOfQuotes>>>

    companion object Factory {
        fun create(): BashImApiService {
            val httpClient = OkHttpClient().newBuilder()
            httpClient.addInterceptor(HttpLoggingInterceptor().apply {
                level = when {
                    BuildConfig.DEBUG -> Level.BODY
                    else -> Level.NONE
                }
            })
            val gson: Gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    // .baseUrl("http://www.umori.li/")
                    // You can access the page without frames with this
                    .baseUrl("http://umorili.herokuapp.com/")
                    .client(httpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create(BashImApiService::class.java)
        }
    }

}
