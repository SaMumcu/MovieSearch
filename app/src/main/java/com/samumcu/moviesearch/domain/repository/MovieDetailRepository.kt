package com.samumcu.moviesearch.domain.repository

import com.samumcu.moviesearch.data.remote.MovieService
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    private val movieService: MovieService
) {
    suspend fun getMovieDetail(movieID: String) =
        movieService.getMovieDetail(movieID)
}
