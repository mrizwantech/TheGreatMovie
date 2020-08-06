package com.riz.thegreatmovieapp.network

import com.riz.thegreatmovieapp.model.MovieConfigApi
import com.riz.thegreatmovieapp.model.MovieGenreList
import com.riz.thegreatmovieapp.model.PopularMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApiInterface {
    @GET("/3/configuration")
   suspend fun getMovieConfig(@Query("api_key") key: String): MovieConfigApi

    @GET("/3/movie/popular")
  suspend  fun getPopularMovies(@Query("api_key") key: String): PopularMovies

    @GET("/3/genre/movie/list")
   suspend fun getMoviesGenreList(@Query("api_key") key: String): MovieGenreList
}