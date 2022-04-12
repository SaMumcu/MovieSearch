package com.samumcu.moviesearch.data.remote

import com.samumcu.moviesearch.data.response.BaseSearchResponse
import com.samumcu.moviesearch.data.response.MovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    companion object {
        const val BASE_URL = "https://omdbapi.com"
        const val TYPE = "movie"
        const val APIKEY = "apikey"
    }

    @GET(".")
    suspend fun getMovies(
        @Query("s") title: String,
        @Query("type") type: String = TYPE,
        @Query("page") page: Int = 0
    ): BaseSearchResponse

    @GET(".")
    suspend fun getMovieDetail(
        @Query("i") movieID: String,
        @Query("type") type: String = TYPE
    ): MovieDetailResponse
}
