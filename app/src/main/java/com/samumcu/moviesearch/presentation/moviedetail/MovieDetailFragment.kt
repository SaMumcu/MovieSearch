package com.samumcu.moviesearch.presentation.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.samumcu.moviesearch.databinding.MovieDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var binding: MovieDetailFragmentBinding
    private val args by navArgs<MovieDetailFragmentArgs>()

    companion object {
        const val FRAGMENT_STATE = "FRAGMENT_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        observeMovieDetailLiveData()
        return binding.root
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
        binding.imageView2.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        parentFragmentManager.putFragment(outState, FRAGMENT_STATE, this)
    }

    private fun observeMovieDetailLiveData() {
        viewModel.movieDetailLiveData.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    binding.movieDetail = it
                    it.Genre?.let { genres ->
                        addGenreTags(genres)
                    }
                }
            }
        )
        viewModel.animationLiveData.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    if (it) {
                        showAnimation()
                    } else {
                        closeAnimation()
                    }
                }
            }
        )
    }

    private fun showAnimation() {
        binding.row.visibility = View.GONE
        binding.appBarLayout.visibility = View.GONE
        binding.ratingText.visibility = View.GONE
        binding.subTitle.visibility = View.GONE
        binding.animationView.visibility = View.VISIBLE
    }

    private fun closeAnimation() {
        binding.animationView.visibility = View.GONE
    }

    private fun addGenreTags(genres: String) {
        val tags = genres.split(",")
        tags.forEach { genre ->
            addChip(genre)
        }
    }

    private fun addChip(tagName: String) {
        val tag = Chip(context)
        tag.text = tagName
        binding.genreTags.addView(tag)
    }
}
