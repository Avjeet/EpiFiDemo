package com.express.epifidemo.model.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.express.epifidemo.data.Movie
import com.express.epifidemo.data.MovieDetail
import com.express.epifidemo.data.Result
import com.express.epifidemo.model.OMDBApiService
import com.express.epifidemo.utils.repositoryTryCatch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val PAGE_SIZE = 10

class MovieRemoteDataSource @Inject constructor(private val omdbApiService: OMDBApiService) {
    suspend fun getMovieByImdbId(imdbId: String): Result<MovieDetail> {
        return repositoryTryCatch {
            val result = omdbApiService.getMovieByIMDBId(imdbId)
            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body()!!)
            } else {
                Result.Failure(result.errorBody().toString())
            }
        }
    }

//    suspend fun searchMoviesWithQuery(query: String, page: Int, type:String?): Result<List<Movie>> {
//        return repositoryTryCatch {
//            val result = omdbApiService.searchMovies(query, page, type)
//            if (result.isSuccessful && result != null) {
//                Result.Success(result.body()!!.search)
//            } else {
//                Result.Failure(result.errorBody().toString())
//            }
//        }
//    }
}