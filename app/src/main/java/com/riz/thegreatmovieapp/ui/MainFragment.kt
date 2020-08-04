package com.riz.thegreatmovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var imageBaseUrl: String
    private lateinit var imageSize: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        model = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        val apiKey: String = getString(R.string.api_key)
        model.getMovieConfig(apiKey)
        model.getLiveDataImages().observe(viewLifecycleOwner, Observer<Images> { images: Images ->

            model.getPopularMovies(apiKey)
            model.getLiveDataPopularMovies()
                .observe(viewLifecycleOwner, Observer<List<Result>> { result: List<Result> ->
                    initRCAdapter(view, result, images.base_url, images.poster_sizes[3])
                })
        })




        return view
    }

    private fun initRCAdapter(view: View, popularMoviesList: List<Result>, baseUrl:String, imageSize:String
    ) {
        viewManager = LinearLayoutManager(context)
        viewAdapter = MovieMainScreenRCAdapter(popularMoviesList, baseUrl, imageSize)
        recyclerView = view.findViewById<RecyclerView>(R.id.rc_movie_popular).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }


}
