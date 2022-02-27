package com.samumcu.moviesearch.domain.repository

import com.samumcu.moviesearch.data.remote.MovieService
import javax.inject.Inject

class MovieSearchRepository @Inject constructor(
    private val movieService: MovieService
) {
    suspend fun getSearchedMovie(movieName: String, pageIndex: Int) =
        movieService.getMovies(movieName, page = pageIndex)

}