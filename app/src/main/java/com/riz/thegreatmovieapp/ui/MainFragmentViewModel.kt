package com.riz.thegreatmovieapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riz.thegreatmovieapp.model.*
import com.riz.thegreatmovieapp.network.MovieApiInterface
import com.riz.thegreatmovieapp.network.MovieApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragmentViewModel : ViewModel() {
    var apiService: MovieApiInterface = MovieApiService.movieApi()
    var mutableLiveDataImages = MutableLiveData<Images>()
    var popularMoviesMutableLiveDataList = MutableLiveData<List<Result>>()
    var genreMutableLiveData = MutableLiveData<HashMap<Int, String>>()
    fun getMovieConfig(apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Call<MovieConfigApi> = apiService.getMovieConfig(apiKey);
            call.enqueue(object : Callback<MovieConfigApi> {
                override fun onResponse(
                    call: Call<MovieConfigApi>,
                    response: Response<MovieConfigApi>
                ) {
                    if (response.isSuccessful) {
                        mutableLiveDataImages.postValue(response.body()!!.images)
                    }
                }

                override fun onFailure(
                    call: Call<MovieConfigApi>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            })

        }
    }

    fun getPopularMovies(apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val call: Call<PopularMovies> = apiService.getPopularMovies(apiKey);
            call.enqueue(object : Callback<PopularMovies> {
                override fun onResponse(
                    call: Call<PopularMovies>,
                    response: Response<PopularMovies>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Genre", response.body()!!.results[0].overview)
                        popularMoviesMutableLiveDataList.postValue(response.body()!!.results)
                    }
                }


                override fun onFailure(
                    call: Call<PopularMovies>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            })

        }
    }

    fun getMoviesGenre(apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Call<MovieGenreList> = apiService.getMoviesGenreList(apiKey);
            call.enqueue(object : Callback<MovieGenreList> {
                override fun onResponse(
                    call: Call<MovieGenreList>,
                    response: Response<MovieGenreList>
                ) {
                    if (response.isSuccessful) {
                        val genreList: List<Genre> = response.body()!!.genres
                        val genreHashMap: HashMap<Int, String> = HashMap()

                        for (genre in genreList) {
                            Log.d("Geattt", "" + genre)
                            genreHashMap[genre.id] = genre.name
                        }
                        genreMutableLiveData.postValue(genreHashMap)
                    }
                }


                override fun onFailure(
                    call: Call<MovieGenreList>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            })

        }
    }

    fun getLiveDataImages(): LiveData<Images> {
        return mutableLiveDataImages
    }

    fun getLiveDataPopularMovies(): LiveData<List<Result>> {
        return popularMoviesMutableLiveDataList
    }
    fun getGenreLiveData():LiveData<HashMap<Int, String>>{
        return genreMutableLiveData
    }
}