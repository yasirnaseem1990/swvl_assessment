package com.swvl.assessmenttest.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.swvl.assessmenttest.BuildConfig
import com.swvl.assessmenttest.R
import com.swvl.assessmenttest.adapter.MoviesCastAdapter
import com.swvl.assessmenttest.adapter.MoviesGenreAdapter
import com.swvl.assessmenttest.adapter.MoviesPictureAdapter
import com.swvl.assessmenttest.api.models.MoviesListResponseModel
import com.swvl.assessmenttest.databinding.FragmentMovieDetailBinding
import com.swvl.assessmenttest.viewmodel.movies.MovieDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private var param1: String? = null
    private var param2: String? = null

    private val binding: FragmentMovieDetailBinding by viewBinding()

    private lateinit var moviesCastAdapter: MoviesCastAdapter
    private lateinit var moviesGenreAdapter: MoviesGenreAdapter
    private lateinit var moviesImagesAdapter: MoviesPictureAdapter
    private lateinit var movieCastLinearLayoutManager: LinearLayoutManager
    private lateinit var movieGenreLinearLayoutManager: LinearLayoutManager
    private lateinit var imagesGridLayoutManager: GridLayoutManager

    val args: MovieDetailFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModel {
        parametersOf(args.movieDetails)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycle.addObserver(viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        moviesCastAdapter = MoviesCastAdapter(
            viewLifecycleOwner
        )

        moviesGenreAdapter = MoviesGenreAdapter(
            viewLifecycleOwner
        )

        moviesImagesAdapter = MoviesPictureAdapter(
            viewLifecycleOwner
        )

        movieCastLinearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        movieGenreLinearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        imagesGridLayoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        binding.rvCasts.apply {
            layoutManager = movieCastLinearLayoutManager
            adapter = moviesCastAdapter
        }

        binding.rvGenres.apply {
            layoutManager = movieGenreLinearLayoutManager
            adapter = moviesGenreAdapter
        }

        binding.rvImages.apply {
            layoutManager = imagesGridLayoutManager
            adapter = moviesImagesAdapter
        }


        val moviesModel =
            Gson().fromJson(args.movieDetails, MoviesListResponseModel.Movy::class.java)
        moviesCastAdapter.setCastList(moviesModel.cast)
        moviesGenreAdapter.setGenreList(moviesModel.genres)

        viewModel.getShowLoadingResult().observe(viewLifecycleOwner, {
            if (it) binding.progressPictures.visibility =
                View.VISIBLE else binding.progressPictures.visibility = View.GONE
        })
        viewModel.getErrorResult().observe(viewLifecycleOwner, {
            Timber.e(it)
        })

        viewModel.searchMoviePictureData.observe(viewLifecycleOwner, {

            val imagesPathList = it.photos.photo.map {
                BuildConfig.IMAGES_URL
                    .plus(it.farm)
                    .plus(BuildConfig.IMAGES_PATH)
                    .plus(it.server)
                    .plus("/")
                    .plus(it.id)
                    .plus("_").plus(it.secret).plus(".jpg")
            }

            moviesImagesAdapter.setImagesUrl(imagesPathList)
        })

    }

    companion object {}
}