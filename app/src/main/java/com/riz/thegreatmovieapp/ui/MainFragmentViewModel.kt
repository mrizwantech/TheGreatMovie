package com.riz.thegreatmovieapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.riz.thegreatmovieapp.model.*
import com.riz.thegreatmovieapp.network.MovieApiInterface
import com.riz.thegreatmovieapp.network.MovieApiService

import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val apiKey: String = application.getString(com.riz.thegreatmovieapp.R.string.api_key)

    var apiService: MovieApiInterface = MovieApiService.movieApi()
    var mutableLiveDataImages = MutableLiveData<Images>()
    var popularMoviesMutableLiveDataList = MutableLiveData<List<Result>>()
    var genreMutableLiveData = MutableLiveData<HashMap<Int, String>>()
    fun getMovieConfig() {
        viewModelScope.launch {
          val  movieConfigApi= async { apiService.getMovieConfig(apiKey) }
            mutableLiveDataImages.postValue(movieConfigApi.await().images)

        }
    }

    fun getPopularMovies() {
     viewModelScope.launch {

          val popularMovies =async { apiService.getPopularMovies(apiKey)}
         popularMoviesMutableLiveDataList.postValue(popularMovies.await().results)
        }
    }

    fun getMoviesGenre() {
        viewModelScope.launch {
            async {

            }
            val genres = apiService.getMoviesGenreList(apiKey)
            val genreList: List<Genre> = genres.genres
            val genreHashMap: HashMap<Int, String> = HashMap()

            for (genre in genreList) {
                Log.d("Geattt", "" + genre)
                genreHashMap[genre.id] = genre.name
            }

            genreMutableLiveData.postValue(genreHashMap)
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