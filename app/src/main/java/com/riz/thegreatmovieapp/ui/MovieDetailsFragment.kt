package com.riz.thegreatmovieapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.riz.thegreatmovieapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import kotlinx.android.synthetic.main.popular_movie_rc_adapter.view.*

class MovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        val tvOverView = view.findViewById<TextView>(R.id.tv_movie_overview)
        val tvTitle = view.findViewById<TextView>(R.id.tv_movie_title)
        val tvGenre = view.findViewById<TextView>(R.id.tv_movie_genres)
        val imageUrl = arguments?.getString("imageUrl")
        val overView = arguments?.getString("overView")
        val movieTile = arguments?.getString("movieTitle")
        val movieGenreList = arguments?.getStringArrayList("genre")
        tvOverView.text = overView
        tvTitle.text = movieTile
        tvGenre.text = movieGenreList.toString()
        Picasso.get().load(imageUrl).into(view.movie_poster)
        return view
    }

}
