package com.express.epifidemo.model

import com.express.epifidemo.BuildConfig
import com.express.epifidemo.data.MovieDetail
import com.express.epifidemo.data.MovieSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDBApiService {
    companion object {
        const val BASE_URL = "${BuildConfig.OMDB_API_BASE}/"
    }

    @GET(".")
    suspend fun searchMovies(
        @Query("s") keyword: String,
        @Query("page") page: Int,
        @Query("type") type: String?
    ): MovieSearchResult

    @GET(".")
    suspend fun getMovieByIMDBId(
        @Query("i") imdbId: String
    ): Response<MovieDetail>
}