package com.express.epifidemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.express.epifidemo.R
import com.express.epifidemo.data.MovieDetail
import com.express.epifidemo.data.Result
import com.express.epifidemo.databinding.MovieDetailBottomSheetBinding
import com.express.epifidemo.utils.ImageUtils
import com.express.epifidemo.viewmodel.HomeMovieViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: MovieDetailBottomSheetBinding
    private val viewModel: HomeMovieViewModel by activityViewModels()
    private var imdbId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailBottomSheetBinding.inflate(inflater, container, false)
        setUpObservers()
        arguments?.let {
            imdbId = it.getString(IMDB_ID)
            imdbId?.let { imdbId ->
                viewModel.fetchMovieDetail(imdbId)
            }
        }
        return binding.root
    }

    private fun setUpObservers() {
        viewModel.movieDetail.observe(this) {
            if (it is Result.Success) {
                inflateData(it.data)
            } else {
                dismiss()
            }
        }
    }

    private fun inflateData(data: MovieDetail) {
        binding.tvGenre.text = data.genre
        binding.rating.text = "${data.imdbRating}/10"
        binding.tvRuntime.text = "${data.runtime} | ${data.rated}"
        binding.tvTitle.text = data.title
        binding.tvDesc.originalText = data.plot
        binding.tvActors.text = data.actors
        binding.ratingBar.rating = data.imdbRating.toFloat() / 2f
        ImageUtils.setRoundedImage(binding.ivPoster, data.poster)
        ImageUtils.setRoundedBlurImage(binding.ivBackPoster, data.poster)
    }

    companion object {
        const val IMDB_ID: String = "imdbId"
        fun getInstance(imdbId: String): MovieDetailBottomSheet {
            return MovieDetailBottomSheet().apply {
                val bundle = Bundle().apply {
                    putString(IMDB_ID, imdbId)
                }
                arguments = bundle
            }
        }
    }
}