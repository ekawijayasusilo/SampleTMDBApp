package com.example.sampletmdb.presentation.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampletmdb.R
import com.example.sampletmdb.databinding.ItemMovieBinding
import com.example.sampletmdb.presentation.list.model.MoviePageModel
import com.example.sampletmdb.utils.loadRoundedRectangle

class MoviesAdapter(private var listener: MoviesAdapterListener) :
    ListAdapter<MoviePageModel, MoviesAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<MoviePageModel>() {
            override fun areItemsTheSame(oldItem: MoviePageModel, newItem: MoviePageModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MoviePageModel, newItem: MoviePageModel) =
                oldItem == newItem
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false),
        listener
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (position == itemCount - 1) {
            listener.onLoadNextPage()
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    class ViewHolder(view: View, private var listener: MoviesAdapterListener) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieBinding.bind(view)

        fun bind(movie: MoviePageModel) {
            binding.textViewTitle.text = movie.title
            binding.textViewPopularity.text = movie.popularity
            binding.imageViewPoster.loadRoundedRectangle(movie.posterUrl)
            binding.root.setOnClickListener { listener.onMovieSelected(movie) }
            binding.imageViewPoster.setImageResource(0)
        }

        fun unbind() {
            binding.imageViewPoster.setImageResource(0)
        }
    }

    interface MoviesAdapterListener {
        fun onMovieSelected(movie: MoviePageModel)

        fun onLoadNextPage()
    }
}