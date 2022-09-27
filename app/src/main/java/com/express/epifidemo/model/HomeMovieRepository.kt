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
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface HomeMovieRepository {
    fun getRandomMovies(type:String?): Flow<PagingData<Movie>>

    fun getMovieByImdbId(imdbId: String): Result<MovieDetail>

    fun searchMovies(page: Int, type:String?): Flow<Result<List<Movie>>>
}

open class HomeMovieRepositoryImpl @Inject constructor() :
    HomeMovieRepository {
    override fun getRandomMovies(type:String?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieRemotePagingSource(type = type)
            }
        ).flow
    }

    override fun getMovieByImdbId(imdbId: String): Result<MovieDetail> {
//        return flow {
//            emit(Result.Loading)
//            emit(movieRemoteDataSource.getMovieByImdbId(imdbId))
//        }
        return Result.Loading
    }

    override fun searchMovies(page: Int, type: String?): Flow<Result<List<Movie>>> {
        TODO("Not yet implemented")
    }
}