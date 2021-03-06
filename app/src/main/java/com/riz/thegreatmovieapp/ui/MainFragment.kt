package com.riz.thegreatmovieapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riz.thegreatmovieapp.R
import com.riz.thegreatmovieapp.model.Images
import com.riz.thegreatmovieapp.model.Result
import com.riz.thegreatmovieapp.util.MovieMainScreenRCAdapter

class MainFragment : Fragment() {
    lateinit var model: MainFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        model = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        model.getMovieConfig()
        model.getLiveDataImages().observe(viewLifecycleOwner, Observer<Images> { images: Images ->

            model.getPopularMovies()
            model.getLiveDataPopularMovies()
                .observe(viewLifecycleOwner, Observer<List<Result>> { result: List<Result> ->
                    initRCAdapter(view, result, images.base_url, images.poster_sizes[3])
                })
        })

        model.getMoviesGenre()


        return view
    }

    private fun initRCAdapter(
        view: View, popularMoviesList: List<Result>, baseUrl: String, imageSize: String
    ) {
        viewManager = LinearLayoutManager(context)
        viewAdapter = MovieMainScreenRCAdapter(popularMoviesList, baseUrl, imageSize)
        recyclerView = view.findViewById<RecyclerView>(R.id.rc_movie_popular).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        (viewAdapter as MovieMainScreenRCAdapter).getClickedPosition()
            .observe(viewLifecycleOwner, Observer<Int> { position: Int ->

                val url = baseUrl + imageSize + popularMoviesList[position].poster_path
                val imageUrl = url.replace("http", "https")
                val genreList = popularMoviesList[position].genre_ids
                val genreNameList: MutableList<String> = ArrayList()
                model.getGenreLiveData().observe(
                    viewLifecycleOwner,
                    Observer<HashMap<Int, String>> {
                        for (genre in genreList) {
                            Log.d("Genre", "" + genre)
                            it[genre]?.let { genres -> genreNameList.add(genres) }
                        }
                    })

                val bundle = bundleOf(
                    "imageUrl" to imageUrl,
                    "overView" to popularMoviesList[position].overview,
                    "movieTitle" to popularMoviesList[position].title,
                    "genre" to genreNameList
                )
                view.findNavController().navigate(R.id.movie_details_action, bundle)
            })
    }


}
