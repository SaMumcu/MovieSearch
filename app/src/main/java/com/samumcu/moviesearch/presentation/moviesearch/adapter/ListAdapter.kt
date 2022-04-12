package com.samumcu.moviesearch.presentation.moviesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samumcu.moviesearch.data.response.MovieSearchResponse
import com.samumcu.moviesearch.databinding.ListItemRowBinding
import com.samumcu.moviesearch.presentation.moviesearch.MovieSearchFragmentDirections

class ListAdapter(
    var movies: List<MovieSearchResponse>
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRowBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(val binding: ListItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieSearchResponse) {
            with(binding) {
                movieData = movie
                row.setOnClickListener {
                    val direction =
                        MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetailFragment(
                            movie.imdbID
                        )
                    itemView.findNavController().navigate(direction)
                }
            }
        }
    }
}
