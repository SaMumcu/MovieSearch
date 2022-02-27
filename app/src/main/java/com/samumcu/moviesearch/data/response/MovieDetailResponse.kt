package com.samumcu.moviesearch.data.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("Title") val Title: String?,
    @SerializedName("Year") val Year: String?,
    @SerializedName("Rated") val Rated: String?,
    @SerializedName("Released") val Released: String?,
    @SerializedName("Genre") val Genre: String?,
    @SerializedName("Director") val Director: String?,
    @SerializedName("Writer") val Writer: String?,
    @SerializedName("Actors") val Actors: String?,
    @SerializedName("imdbID") var imdbID: String,
    @SerializedName("imdbVotes") var imdbVotes: String,
    @SerializedName("imdbRating") var imdbRating: String,
    @SerializedName("Type") var Type: String,
    @SerializedName("Plot") var Plot: String,
    @SerializedName("Poster") val Poster: String?
)
