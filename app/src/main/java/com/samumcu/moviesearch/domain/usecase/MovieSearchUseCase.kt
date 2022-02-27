package com.samumcu.moviesearch.domain.usecase

import com.samumcu.moviesearch.data.response.BaseSearchResponse
import com.samumcu.moviesearch.domain.repository.MovieSearchRepository
import com.samumcu.moviesearch.utils.NetworkResult
import java.io.IOException
import javax.inject.Inject

class MovieSearchUseCase @Inject constructor(
    private val movieSearchRepository: MovieSearchRepository
) {
    suspend fun execute(movieName: String, pageIndex: Int): NetworkResult<BaseSearchResponse> =
        try {
            movieSearchRepository.getSearchedMovie(
                movieName,
                pageIndex
            ).let {
                NetworkResult.Success(it)
            }
        } catch (e: IOException) {
            NetworkResult.Error(e.toString())
        }
}