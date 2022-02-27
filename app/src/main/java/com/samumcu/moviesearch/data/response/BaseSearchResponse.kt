package com.samumcu.moviesearch.data.response

import com.google.gson.annotations.SerializedName

data class BaseSearchResponse(
    @SerializedName("Search") val Search: List<MovieSearchResponse>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val Response: String?
)
