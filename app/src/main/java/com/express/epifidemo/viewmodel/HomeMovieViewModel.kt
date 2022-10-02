package com.express.epifidemo.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.express.epifidemo.constants.MovieType
import com.express.epifidemo.data.MovieDetail
import com.express.epifidemo.data.MovieUIItem
import com.express.epifidemo.data.Result
import com.express.epifidemo.model.HomeMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMovieViewModel @Inject constructor(private val repository: HomeMovieRepository) :
    ViewModel() {

    private val currentMovieType = MutableLiveData(MovieType.Home)

    var searchQuery = MutableLiveData<String>()

    private val _movieDetail = MutableLiveData<Result<MovieDetail>>()
    val movieDetail: LiveData<Result<MovieDetail>> = _movieDetail

    fun fetchRandomMovieData(): Flow<PagingData<MovieUIItem>> {
        return repository.getRandomMovies(currentMovieType.value?.value)
            .map { pagingData ->
                pagingData.map { MovieUIItem.mapMovieToUiObject(it) }
            }.cachedIn(viewModelScope)
    }

    fun searchMoviesUsingQuery(): Flow<PagingData<MovieUIItem>> {
        return searchQuery.asFlow()
            .debounce(1000)
            .filter {
                it.trim().isEmpty().not()
            }
            .distinctUntilChanged()
            .flatMapLatest {
                repository.searchMovies(query = it, type = currentMovieType.value?.value)
                    .map { pagingData ->
                        pagingData.map { movie -> MovieUIItem.mapMovieToUiObject(movie) }
                    }
                    .cachedIn(viewModelScope)
            }
    }

    fun updateCurrentMovieType(movieType: MovieType) {
        currentMovieType.value = movieType
    }

    fun fetchMovieDetail(imdbId: String) {
        viewModelScope.launch {
            val movieDetail = repository.getMovieByImdbId(imdbId)
            _movieDetail.postValue(movieDetail)
        }
    }
}