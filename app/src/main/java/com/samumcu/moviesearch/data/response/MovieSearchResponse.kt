package com.samumcu.moviesearch.data.response

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("Title") val Title: String?,
    @SerializedName("Year") val Year: String?,
    @SerializedName("imdbID") var imdbID: String,
    @SerializedName("Type") var Type: String,
    @SerializedName("Poster") val Poster: String?
)
