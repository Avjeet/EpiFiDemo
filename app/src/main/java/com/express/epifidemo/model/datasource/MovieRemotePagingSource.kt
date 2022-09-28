package com.express.epifidemo.model.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.express.epifidemo.App
import com.express.epifidemo.data.Movie
import com.express.epifidemo.di.modules.DataSourceEntryPoint
import com.express.epifidemo.model.OMDBApiService
import dagger.hilt.android.EntryPointAccessors
import retrofit2.HttpException
import java.io.IOException
import java.lang.IllegalStateException
import javax.inject.Inject

class MovieRemotePagingSource @Inject constructor(private val query:String, private val type: String?) :
    PagingSource<Int, Movie>() {

    val appContext = App.getInstance()?.applicationContext ?: throw IllegalStateException()
    val entryPoint = EntryPointAccessors.fromApplication(appContext, DataSourceEntryPoint::class.java)
    val omdbApiService = entryPoint.omdbApiService

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_KEY

        return try {
            val data = omdbApiService.searchMovies(query, params.key ?: 1, type)
            if(data.response){
                val movies = data.search

                LoadResult.Page(
                    data = movies,
                    prevKey = if (page == STARTING_KEY) null else page,
                    nextKey = if (movies.isEmpty()) null else page + 1
                )
            } else{
                LoadResult.Error(Throwable("No Data"))
            }

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }

    }

    companion object {
        const val STARTING_KEY = 1
    }
}