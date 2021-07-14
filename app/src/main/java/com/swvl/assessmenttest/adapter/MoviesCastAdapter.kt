package com.swvl.assessmenttest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.swvl.assessmenttest.databinding.MoviesCastListitemBinding
import com.swvl.assessmenttest.viewmodel.movies.MoviesCastItemViewModel

class MoviesCastAdapter(
    private val lifecycleOwner: LifecycleOwner,
) :  RecyclerView.Adapter<MoviesCastAdapter.MoviesItemViewHolder>() {

    private var castList: List<String> = listOf()

    fun setCastList(castList: List<String>) {
        this.castList = castList
        notifyDataSetChanged()
    }

    init {
        setHasStableIds(true)
    }

    /*override fun getItemId(position: Int): Long {
        return moviesList[position].hashCode().toLong()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesItemViewHolder {
        val viewModel = MoviesCastItemViewModel()

        val binding = MoviesCastListitemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MoviesItemViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: MoviesItemViewHolder, position: Int) {
        holder.onBind(castList[position])
    }

    override fun getItemCount(): Int = castList.size

    inner class MoviesItemViewHolder(
        private val binding: MoviesCastListitemBinding,
        private val viewModel: MoviesCastItemViewModel
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