package com.samumcu.moviesearch.presentation.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.rememberAsyncImagePainter
import com.samumcu.moviesearch.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var viewModel: MovieDetailViewModel
    private val args by navArgs<MovieDetailFragmentArgs>()

    companion object {
        const val FRAGMENT_STATE = "FRAGMENT_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        return ComposeView(requireContext()).apply {
            setContent {
                val movieDetail by viewModel.movieDetailLiveData.observeAsState()
                movieDetail?.let {
                    MaterialTheme {
                        Column {
                            MovieNameArea(movieDetail?.Title ?: "")
                            ShortDescription(movieDetail?.Plot ?: "")
                            MovieImage(movieDetail?.Poster ?: "")
                            MovieRateArea(movieDetail?.imdbRating ?: "0")
                            movieDetail?.Genre?.let { genres ->
                                AddGenreTags(genres)
                            }
                            ActorsOfMovie(movieDetail?.Actors ?: "")
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState.let { bundle ->
            bundle?.let {
                parentFragmentManager.getFragment(bundle, FRAGMENT_STATE)
            } ?: run {
                viewModel.getMovieDetail(args.movieId)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        parentFragmentManager.putFragment(outState, FRAGMENT_STATE, this)
    }

    @Composable
    private fun MovieNameArea(
        movieName: String
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "Back button icon",
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp
                    )
                    .clickable {
                        findNavController().popBackStack()
                    },
                tint = colorResource(id = R.color.dark_yellow)
            )
            Text(
                text = movieName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.dark_yellow),
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }

    @Composable
    private fun ShortDescription(
        movieDescription: String
    ) {
        Row {
            Text(
                movieDescription,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp
                )
            )
        }
    }

    @Composable
    private fun MovieImage(
        imageUrl: String
    ) {
        Row {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Movie Description Image",
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }

    @Composable
    private fun MovieRateArea(
        movieRate: String
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_rate),
                contentDescription = "IMDB rank icon",
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 8.dp,
                    top = 16.dp
                ),
                tint = colorResource(id = R.color.dark_yellow)
            )
            Text(
                movieRate,
                color = colorResource(id = R.color.gray_dark),
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }

    @Composable
    private fun ActorsOfMovie(
        actors: String
    ) {
        Row {
            Text(
                actors,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp
                ),
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    private fun AddGenreTags(genres: String) {
        val tags = genres.split(",")
        ChipGroup(tags)
    }

    @Composable
    fun ChipGroup(
        tags: List<String>
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            LazyRow {
                items(items = tags) {
                    Chip(it)
                }
            }
        }
    }

    @Composable
    fun Chip(
        name: String = "Chip"
    ) {
        Surface(
            modifier = Modifier.padding(4.dp),
            shape = RoundedCornerShape(16.dp),
            color = colorResource(id = R.color.gray_light)
        ) {
            Row {
                Text(
                    text = name,
                    style = MaterialTheme.typography.body2,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
