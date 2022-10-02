package com.express.epifidemo.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.express.epifidemo.data.Movie
import com.express.epifidemo.data.MovieDetail
import com.express.epifidemo.data.Result
import com.express.epifidemo.model.datasource.MovieRemoteDataSource
import com.express.epifidemo.model.datasource.MovieRemotePagingSource
import com.express.epifidemo.model.datasource.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HomeMovieRepository {
    fun getRandomMovies(type: String?): Flow<PagingData<Movie>>

    fun searchMovies(query: String, type: String?): Flow<PagingData<Movie>>

    suspend fun getMovieByImdbId(imdbId: String): Result<MovieDetail>

}

open class HomeMovieRepositoryImpl @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) :
    HomeMovieRepository {
    override fun getRandomMovies(type: String?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieRemotePagingSource(type = type, query = "Avengers")
            }
        ).flow
    }

    override fun searchMovies(query: String, type: String?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieRemotePagingSource(type = type, query = query)
            }
        ).flow
    }

    override suspend fun getMovieByImdbId(imdbId: String): Result<MovieDetail> {
        return movieRemoteDataSource.getMovieByImdbId(imdbId)
    }
}