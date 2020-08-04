package com.riz.thegreatmovieapp.util

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riz.thegreatmovieapp.R
import com.riz.thegreatmovieapp.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.popular_movie_rc_adapter.view.*

class MovieMainScreenRCAdapter(private val popularMovies: List<Result>, private val baseUrl: String,
private val posterSize: String) :
    RecyclerView.Adapter<MovieMainScreenRCAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieMainScreenRCAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_movie_rc_adapter, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return popularMovies.size
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.tvMovieTitle.text = popularMovies[position].original_title
        val url  = baseUrl+posterSize+popularMovies[position].poster_path
        val aUrl = url.replace("http", "https")
        Log.d("imageurl", aUrl)
        Picasso.get().load(aUrl).into(viewHolder.ivMoviePoster)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMovieTitle: TextView = itemView.tv_movie_title
        var ivMoviePoster: ImageView = itemView.iv_movie_poster

    }
}