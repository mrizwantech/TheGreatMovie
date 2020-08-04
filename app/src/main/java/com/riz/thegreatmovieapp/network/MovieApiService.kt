package com.riz.thegreatmovieapp.network

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
val BASE_URL = "https://api.themoviedb.org/"
class MovieApiService {
    companion object {
        fun movieApi():MovieApiInterface {

            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client =
                OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
           return  retrofit.create(MovieApiInterface::class.java)
        }
    }
}