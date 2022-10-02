package com.express.epifidemo.data

import com.google.gson.annotations.SerializedName

data class MovieSearchResult(
    @SerializedName("Search")
    val search: List<Movie>,
    @SerializedName("Response")
    val response: Boolean
)

data class Movie(
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Year")
    val year: String
)

data class MovieUIItem(
    val imdbID: String,
    val poster: String,
    val title: String,
    val type: String,
    var bookmarked: Boolean
) {
    companion object {
        fun mapMovieToUiObject(movie: Movie): MovieUIItem {
            return MovieUIItem(
                movie.imdbID,
                movie.poster,
                movie.title,
                movie.type,
                false
            )
        }
    }
}

data class MovieDetail(
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Awards")
    val awards: String,
    @SerializedName("BoxOffice")
    val boxOffice: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("DVD")
    val dVD: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("imdbRating")
    val imdbRating: String,
    @SerializedName("imdbVotes")
    val imdbVotes: String,
    @SerializedName("Language")
    val language: String,
    @SerializedName("Metascore")
    val metascore: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Production")
    val production: String,
    @SerializedName("Rated")
    val rated: String,
    @SerializedName("Ratings")
    val ratings: List<Rating>,
    @SerializedName("Released")
    val released: String,
    @SerializedName("Runtime")
    val runtime: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Website")
    val website: String,
    @SerializedName("Writer")
    val writer: String,
    @SerializedName("Year")
    val year: String
)

data class Rating(
    @SerializedName("Source")
    val source: String,
    @SerializedName("Value")
    val value: String
)


