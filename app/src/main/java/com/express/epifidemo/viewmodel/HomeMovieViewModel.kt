package com.express.epifidemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.express.epifidemo.data.Movie
import com.express.epifidemo.data.Result
import com.express.epifidemo.model.HomeMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMovieViewModel @Inject constructor(private val repository: HomeMovieRepository) :
    ViewModel() {

    fun fetchRandomMovieData(type: String?): Flow<PagingData<Movie>> {
        return repository.getRandomMovies(type)
            .cachedIn(viewModelScope)
    }

}