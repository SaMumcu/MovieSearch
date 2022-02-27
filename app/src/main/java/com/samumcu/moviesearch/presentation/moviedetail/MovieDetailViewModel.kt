package com.samumcu.moviesearch.presentation.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samumcu.moviesearch.data.response.MovieDetailResponse
import com.samumcu.moviesearch.domain.usecase.MovieDetailUseCase
import com.samumcu.moviesearch.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailUseCase
) : ViewModel() {

    private val movieDetailData = MutableLiveData<MovieDetailResponse?>()
    val movieDetailLiveData: LiveData<MovieDetailResponse?> get() = movieDetailData

    private val animationData = MutableLiveData<Boolean?>()
    val animationLiveData: LiveData<Boolean?> get() = animationData

    fun getMovieDetail(movieID: String) {
        viewModelScope.launch {
            movieDetailUseCase.execute(
                movieID
            ).also {
                when {
                    it is NetworkResult.Success -> {
                        movieDetailData.value = it.data
                    }
                    it is NetworkResult.Error ->
                        animationData.value = true
                }
            }
        }
    }
}