package com.swvl.assessmenttest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.swvl.assessmenttest.databinding.MoviesImageListBinding
import com.swvl.assessmenttest.viewmodel.movies.MoviesImagesItemViewModel

class MoviesPictureAdapter(
    private val lifecycleOwner: LifecycleOwner,
) :  RecyclerView.Adapter<MoviesPictureAdapter.MoviesItemViewHolder>() {

    private var genreList: List<String> = listOf()

    fun setImagesUrl(genreList: List<String>) {
        this.genreList = genreList
        notifyDataSetChanged()
    }

    init {
        setHasStableIds(true)
    }

    /*override fun getItemId(position: Int): Long {
        return moviesList[position].hashCode().toLong()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesItemViewHolder {
        val viewModel = MoviesImagesItemViewModel()

        val binding = MoviesImageListBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MoviesItemViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: MoviesItemViewHolder, position: Int) {
        holder.onBind(genreList[position])
    }

    override fun getItemCount(): Int = genreList.size

    inner class MoviesItemViewHolder(
        private val binding: MoviesImageListBinding,
        private val itemViewModel: MoviesImagesItemViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = itemViewModel
            binding.lifecycleOwner = lifecycleOwner
        }

        fun onBind(item: String) {
            itemViewModel.bind(item)
            binding.executePendingBindings()
        }
    }

}