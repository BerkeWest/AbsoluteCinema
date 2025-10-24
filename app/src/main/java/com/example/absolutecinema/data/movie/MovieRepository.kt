package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.remote.model.request.MovieSearchResult
import com.example.absolutecinema.data.remote.model.request.WatchListBody
import com.example.absolutecinema.data.remote.model.response.MovieDetails
import com.example.absolutecinema.data.remote.model.response.MovieState
import com.example.absolutecinema.data.remote.model.response.ResultPages


class MovieRepository(
    private val api: MovieApiService,
    private val sessionManager: SessionManager
) {


    private var genreMap: Map<Int, String>? = null

    /*
    GenreMap null değilse, istek atmadan kendisini döndürür, yoksa api'den alır ve güncelleyip döndürür.
    */
    suspend fun getGenreMap(): Map<Int, String> {
        genreMap?.let {return it}

        val response = api.getGenreList()
        genreMap = response.genres.associate { it.id to it.name }
        return genreMap ?: emptyMap()
    }

    /*
    Verilen genre idlerin, genreMapte eşleşen isimlerini döndürür.
    */
    suspend fun getGenreNamesByIds(genreIds: List<Int>): String {
        val map = getGenreMap()
        return genreIds.mapNotNull { map[it] }.joinToString(", ")
    }

    /*
    Aratıla kelimeyi vererek api'den arama yapar. Sonuç olarak bulunan filmleri döndürür.
    */
    suspend fun search(word: String): List<MovieSearchResult> {
        val response = api.searchMovies(word)
        return response.results
    }

    /*
    Verilen id'ye göre api'den filmin detaylarını alır.
    */
    suspend fun getDetails(id: Int): MovieDetails {
        val response = api.getMovieDetails(id)
        return response
    }

    /*
    Verilen id'ye göre api'den filmin durumunu alır.
    */
    suspend fun getMovieState(id: Int): MovieState {
        val response = api.getMovieAccountStates(id)
        return response
    }

    /*
    Session managerdan dönen account id ile watchlist'i çeker.
    */
    suspend fun getWatchList(): List<MovieSearchResult> {
        val accountId = sessionManager.accountId
        val response = api.getWatchlist(accountId)
        return response.results
    }

    /*
    Session managerdan dönen account id ile watchlist'e ekleme yapar. Verilen id ve boolean ile
    hangi film olduğu ve eklemek veya çıkarmak istediği bilgisini gönderir.
    */
    suspend fun addToWatchlist(movieId: Int, add: Boolean) {
        val accountId = sessionManager.accountId
        val watchListBody = WatchListBody(mediaType = "movie", media_id = movieId, watchlist = add)
        api.addToWatchlist(accountId, watchListBody)
    }

    /*
    Alttaki 4 method da api'den filmleri çeker. HomeScreen'deki tablerde gösterilirler.
    */
    suspend fun getNowPlaying(): ResultPages {
        val response = api.getNowPlayingMovies()
        return response
    }

    suspend fun getUpcoming(): ResultPages {
        val response = api.getUpcomingMovies()
        return response
    }

    suspend fun getTopRated(): ResultPages {
        val response = api.getTopRatedMovies()
        return response
    }

    suspend fun getPopular(): ResultPages {
        val response = api.getPopularMovies()
        return response
    }

}

/*
Genre isimlerini birleştirerek döndürür.
*/
fun getGenreString(genres: List<String>): String {
    return genres.joinToString(", ")
}