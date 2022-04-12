package com.samumcu.moviesearch.domain.usecase

import com.samumcu.moviesearch.data.response.MovieDetailResponse
import com.samumcu.moviesearch.domain.repository.MovieDetailRepository
import com.samumcu.moviesearch.utils.NetworkResult
import java.io.IOException
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    suspend fun execute(movieID: String): NetworkResult<MovieDetailResponse> = try {
        movieDetailRepository.getMovieDetail(
            movieID
        ).let {
            NetworkResult.Success(it)
        }
    } catch (e: IOException) {
        NetworkResult.Error(e.toString())
    }
}
