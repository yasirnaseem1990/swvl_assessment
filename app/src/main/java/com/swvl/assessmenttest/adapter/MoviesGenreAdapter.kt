package com.swvl.assessmenttest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.swvl.assessmenttest.databinding.MoviesGenreListitemBinding
import com.swvl.assessmenttest.viewmodel.movies.MoviesGenreItemViewModel

class MoviesGenreAdapter(
    private val lifecycleOwner: LifecycleOwner,
) :  RecyclerView.Adapter<MoviesGenreAdapter.MoviesItemViewHolder>() {

    private var genreList: List<String> = listOf()

    fun setGenreList(genreList: List<String>) {
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
        val viewModel = MoviesGenreItemViewModel()

        val binding = MoviesGenreListitemBinding
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
        private val binding: MoviesGenreListitemBinding,
        private val viewModel: MoviesGenreItemViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = viewModel
            binding.lifecycleOwner = lifecycleOwner
        }

        fun onBind(item: String) {
            viewModel.bind(item)
            binding.executePendingBindings()
        }
    }

}