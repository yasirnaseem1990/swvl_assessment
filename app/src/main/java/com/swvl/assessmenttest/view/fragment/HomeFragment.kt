package com.swvl.assessmenttest.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.swvl.assessmenttest.R
import com.swvl.assessmenttest.adapter.MoviesListAdapter
import com.swvl.assessmenttest.databinding.FragmentHomeBinding
import com.swvl.assessmenttest.viewmodel.movies.MoviesListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var param1: String? = null
    private var param2: String? = null

    private val binding: FragmentHomeBinding by viewBinding()

    private val viewModel: MoviesListViewModel by viewModel()

    private lateinit var moviesAdapter: MoviesListAdapter

    private var searchItem: MenuItem? = null

    private var searchView: SearchView? = null

    private lateinit var movieListLinearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        moviesAdapter = MoviesListAdapter(
            viewLifecycleOwner,
            viewModel.movieDetails)

        movieListLinearLayoutManager = LinearLayoutManager(requireContext())


        binding.moviesListRV.apply {
            layoutManager = movieListLinearLayoutManager
            adapter = moviesAdapter
        }

        viewModel.moviesList.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                moviesAdapter.setMoviesList(it)
            } else {
                moviesAdapter.setMoviesList(it)
            }
        })

        viewModel.movieDetails.observe(viewLifecycleOwner, {
            val movieObj = Gson().toJson(it)
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(movieObj))
        })


        viewModel.getShowLoadingResult().observe(viewLifecycleOwner, {
            if (it) binding.progressMovies.visibility = View.VISIBLE else binding.progressMovies.visibility = View.GONE
        })

        viewModel.getErrorResult().observe(viewLifecycleOwner, {
            Timber.e(it)

        })

        /*viewModel.searchViewHint.observe(viewLifecycleOwner) {
            searchView?.queryHint = getString(it)
        }*/
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movies_toolbar, menu)

        searchItem = menu.findItem(R.id.menu_search)
        searchView = searchItem?.actionView as SearchView?

        searchView?.queryHint = getString(R.string.search_movies)

        val clearSearch: ImageView = searchView?.findViewById(R.id.search_close_btn) as ImageView

        clearSearch.setOnClickListener {
            //Clear query
            searchView?.setQuery("", false)
            searchItem?.collapseActionView()
            viewModel.onClearFilterClicked()
        }

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                viewModel.querySearchValue.value = query
                viewModel.resetMoviesSetUp()
                viewModel.getMoviesList()
                searchItem?.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}