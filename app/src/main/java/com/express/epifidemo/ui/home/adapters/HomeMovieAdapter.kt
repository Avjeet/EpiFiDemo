package com.express.epifidemo.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.express.epifidemo.data.Movie
import com.express.epifidemo.databinding.ItemMovieBinding

class HomeMovieAdapter(context: Context, private val itemClickListener: OnItemClickListener) :
    PagingDataAdapter<Movie, HomeMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private val glide = Glide.with(context)

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Movie) {
            binding.tvTitle.text = data.title
            binding.tvItemType.text = data.type
            glide
                .load(data.poster)
                .centerCrop()
                .into(binding.ivPoster)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(holder.absoluteAdapterPosition, movie)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.imdbID == newItem.imdbID

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, Movie: Movie)
    }

}