package com.express.epifidemo.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.express.epifidemo.R
import com.express.epifidemo.data.MovieUIItem
import com.express.epifidemo.databinding.ItemMovieBinding

class HomeMovieAdapter(context: Context, private val itemClickListener: OnItemClickListener) :
    PagingDataAdapter<MovieUIItem, HomeMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private val glide = Glide.with(context)

    inner class MovieViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: MovieUIItem) {
            binding.tvTitle.text = data.title
            binding.tvItemType.text = data.type
            glide
                .load(data.poster)
                .centerCrop()
                .into(binding.ivPoster)

            if(data.bookmarked)
                binding.ctaBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
            else
                binding.ctaBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { MovieUIItem ->
            holder.bind(MovieUIItem)
            holder.itemView.setOnClickListener {
                val clickedPos = holder.bindingAdapterPosition
                val currentMovie = getItem(clickedPos)
                currentMovie?.let {
                    itemClickListener.onItemClick(
                        clickedPos, it
                    )
                }
            }
            holder.binding.ctaBookmark.setOnClickListener {
                val clickedPos = holder.bindingAdapterPosition
                val currentMovie = getItem(clickedPos)
                currentMovie?.let {
                    val bookmarked = !it.bookmarked
                    itemClickListener.onBookMarkClicked(
                        it, bookmarked
                    )
                    snapshot()[clickedPos]?.bookmarked = bookmarked
                    notifyItemChanged(clickedPos)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieUIItem>() {
            override fun areItemsTheSame(oldItem: MovieUIItem, newItem: MovieUIItem) =
                oldItem.imdbID == newItem.imdbID

            override fun areContentsTheSame(oldItem: MovieUIItem, newItem: MovieUIItem) =
                oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, movieUiItem: MovieUIItem)
        fun onBookMarkClicked(movieUIItem: MovieUIItem, bookmarked: Boolean)
    }

}