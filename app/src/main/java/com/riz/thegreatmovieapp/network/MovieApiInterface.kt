package com.riz.thegreatmovieapp.network

import com.riz.thegreatmovieapp.model.MovieConfigApi
import com.riz.thegreatmovieapp.model.PopularMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApiInterface {
    @GET("/3/configuration")
    fun getMovieConfig(@Query("api_key") key: String): Call<MovieConfigApi>

    @GET("/3/movie/popular")
    fun getPopularMovies(@Query("api_key") key: String): Call<PopularMovies>
}