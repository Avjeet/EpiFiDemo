package com.express.epifidemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.express.epifidemo.constants.MovieType
import com.express.epifidemo.data.Movie
import com.express.epifidemo.model.HomeMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeMovieViewModel @Inject constructor(private val repository: HomeMovieRepository) :
    ViewModel() {

    private val currentMovieType = MutableLiveData(MovieType.Home)

    var searchQuery = MutableLiveData<String>()

    fun fetchRandomMovieData(): Flow<PagingData<Movie>> {
        return repository.getRandomMovies(currentMovieType.value?.value)
            .cachedIn(viewModelScope)
    }

    fun searchMoviesUsingQuery(): Flow<PagingData<Movie>> {
        return searchQuery.asFlow()
            .debounce(1000)
            .filter {
                it.trim().isEmpty().not()
            }
            .distinctUntilChanged()
            .flatMapLatest {
                repository.searchMovies(query = it, type = currentMovieType.value?.value)
                    .cachedIn(viewModelScope)
            }
    }

    fun updateCurrentMovieType(movieType: MovieType) {
        currentMovieType.value = movieType
    }

}