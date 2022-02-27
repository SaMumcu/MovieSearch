package com.samumcu.moviesearch.presentation.moviesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samumcu.moviesearch.data.response.BaseSearchResponse
import com.samumcu.moviesearch.domain.usecase.MovieSearchUseCase
import com.samumcu.moviesearch.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val movieSearchUseCase: MovieSearchUseCase
) : ViewModel() {

    private val movieListData = MutableLiveData<BaseSearchResponse?>()
    val movieListDataLiveData: LiveData<BaseSearchResponse?> get() = movieListData

    private val animationData = MutableLiveData<Boolean?>()
    val animationLiveData: LiveData<Boolean?> get() = animationData

    var currentPage = 1
    var totalPage = 0
    var totalPageCalculated = false
    var currentMovieName = ""

    fun getMovies(movieName: String) {
        currentMovieName = movieName
        viewModelScope.launch {
            movieSearchUseCase.execute(
                movieName,
                currentPage
            ).also {
                when {
                    it is NetworkResult.Success -> {
                        if (it.data?.Response.equals("False")) {
                            animationData.value = true
                        } else {
                            animationData.value = false
                            movieListData.value = it.data
                            if (!totalPageCalculated) {
                                totalPageCalculated = true
                                it.data?.totalResults?.let {
                                    if (it.toInt() % 10 != 0) {
                                        totalPage += 1
                                    }
                                    totalPage += it.toInt() / 10
                                }
                            }
                        }
                    }
                    it is NetworkResult.Error ->
                        animationData.value = true
                }
            }
        }
    }

    fun goToPreviousPage() {
        currentPage--
        getMovies(currentMovieName)
    }

    fun goToNextPage() {
        currentPage++
        getMovies(currentMovieName)
    }

    fun resetData() {
        totalPage = 0
        currentPage = 1
        totalPageCalculated = false
    }
}