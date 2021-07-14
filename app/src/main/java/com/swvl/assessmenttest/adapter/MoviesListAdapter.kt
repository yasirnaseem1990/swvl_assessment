package com.swvl.assessmenttest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.swvl.assessmenttest.api.models.MoviesListResponseModel.Movy
import com.swvl.assessmenttest.databinding.MoviesListitemBinding
import com.swvl.assessmenttest.utils.SingleLiveEvent
import com.swvl.assessmenttest.viewmodel.movies.MoviesListItemViewModel

class MoviesListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val movieDetails: SingleLiveEvent<Movy>
) :  RecyclerView.Adapter<MoviesListAdapter.MoviesItemViewHolder>() {

    private var moviesList: List<Movy> = listOf()

    fun setMoviesList(moviesList: List<Movy>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return moviesList[position].id.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesItemViewHolder {
        val viewModel = MoviesListItemViewModel(movieDetails)

        val binding = MoviesListitemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MoviesItemViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: MoviesItemViewHolder, position: Int) {
        holder.onBind(moviesList[position])
    }

    override fun getItemCount(): Int = moviesList.size

    inner class MoviesItemViewHolder(
        private val binding: MoviesListitemBinding,
        private val viewModel: MoviesListItemViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = viewModel
            binding.lifecycleOwner = lifecycleOwner
        }

        fun onBind(item: Movy) {
            viewModel.bind(item)
            binding.executePendingBindings()
        }
    }

}