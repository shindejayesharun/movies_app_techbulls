package com.shindejayesharun.moviesapptechbulls.ui.component.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shindejayesharun.moviesapptechbulls.R
import com.shindejayesharun.moviesapptechbulls.data.dto.movies.Movies
import com.shindejayesharun.moviesapptechbulls.databinding.MovieItemBinding
import com.squareup.picasso.Picasso

/**
 * Created by shindejayesharun
 */

class MoviesAdapter(private val moviesListViewModel: MoviesListViewModel, private val movies: List<Movies.Movie>) : RecyclerView.Adapter<MovieViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }


}

class MovieViewHolder(private val itemBinding: MovieItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(moviesItem: Movies.Movie) {
        itemBinding.tvCaption.text = moviesItem.Year
        itemBinding.tvName.text = moviesItem.Title
        Picasso.get().load(moviesItem.Poster).placeholder(R.drawable.ic_healthy_food).error(R.drawable.ic_healthy_food).into(itemBinding.ivMovieItemImage)
    }
}


