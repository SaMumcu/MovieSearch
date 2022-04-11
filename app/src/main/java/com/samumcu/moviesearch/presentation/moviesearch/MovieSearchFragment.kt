package com.samumcu.moviesearch.presentation.moviesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samumcu.moviesearch.databinding.MovieSearchFragmentBinding
import com.samumcu.moviesearch.presentation.moviesearch.adapter.ListAdapter
import com.samumcu.moviesearch.utils.UIUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchFragment : Fragment() {

    private lateinit var viewModel: MovieSearchViewModel
    private lateinit var binding: MovieSearchFragmentBinding

    private lateinit var listAdapter: ListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieSearchFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MovieSearchViewModel::class.java)
        layoutManager = GridLayoutManager(context, 1)
        binding.movieListRV.layoutManager = layoutManager
        observeLiveDatas()
        return binding.root
    }

    private fun observeLiveDatas() {
        viewModel.movieListDataLiveData.observe(viewLifecycleOwner, {
            it?.Search?.let {
                binding.movieListRV.visibility = View.VISIBLE
                closeProgressBar()
                closeAnimation()
                listAdapter = ListAdapter(it)
                binding.movieListRV.adapter = listAdapter
            }
        })
        viewModel.animationLiveData.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    showAnimation()
                } else {
                    closeAnimation()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getFilmBTN.setOnClickListener {
            hideKeyboard()
            viewModel.resetData()
            viewModel.getMovies(binding.filmNameET.text.trim().toString())
            showProgress()
        }
        binding.movieListRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (viewModel.currentPage < viewModel.totalPage) {
                        scrollDown()
                    }
                } else if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (viewModel.currentPage > 1) {
                        scrollUp()
                    }
                }
            }
        })
    }

    private fun scrollDown() {
        showProgress()
        viewModel.goToNextPage()
        listAdapter.notifyDataSetChanged()
    }

    private fun scrollUp() {
        showProgress()
        viewModel.goToPreviousPage()
        listAdapter.notifyDataSetChanged()
    }

    private fun showProgress() {
        closeAnimation()
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun closeProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showAnimation() {
        binding.movieListRV.visibility = View.GONE
        closeProgressBar()
        binding.animationView.visibility = View.VISIBLE
    }

    private fun closeAnimation() {
        binding.animationView.visibility = View.GONE
    }
}