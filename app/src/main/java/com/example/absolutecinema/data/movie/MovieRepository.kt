package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.model.request.WatchListBody
import com.example.absolutecinema.data.model.response.MovieDetails
import com.example.absolutecinema.data.model.response.MovieSearchResult
import com.example.absolutecinema.data.model.response.MovieState
import com.example.absolutecinema.data.model.response.ResultPages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(
    private val api: MovieApiService,
    private val sessionManager: SessionManager
) : MovieRepositoryInterface {


    private var genreMap: Map<Int, String>? = null

    /*
    GenreMap null değilse, istek atmadan kendisini döndürür, yoksa api'den alır ve güncelleyip döndürür.
    */
    override fun getGenreMap(): Flow<Map<Int, String>> = flow {
        genreMap?.let { emit(it) }

        val response = api.getGenreList()
        genreMap = response.genres.associate { it.id to it.name }
        emit(genreMap ?: emptyMap())
    }

    /*
    Verilen genre idlerin, genreMapte eşleşen isimlerini döndürür.
    */
    override fun getGenreNamesByIds(genreIds: List<Int>): Flow<String> = flow {
        var map = ""
        getGenreMap().collect { result ->
            map = genreIds.mapNotNull { result[it] }.joinToString(", ")
        }
        emit(map)
    }

    /*
    Aratıla kelimeyi vererek api'den arama yapar. Sonuç olarak bulunan filmleri döndürür.
    */
    override fun search(word: String): Flow<List<MovieSearchResult>> = flow {
        val response = api.searchMovies(word)
        emit(response.results)
    }

    /*
    Verilen id'ye göre api'den filmin detaylarını alır.
    */
    override fun getDetails(id: Int): Flow<MovieDetails>  = flow{
        val response = api.getMovieDetails(id)
        emit(response)
    }

    /*
    Verilen id'ye göre api'den filmin durumunu alır.
    */
    override fun getMovieState(id: Int): Flow<MovieState> = flow{
        val response = api.getMovieAccountStates(id)
        emit(response)
    }

    /*
    Session managerdan dönen account id ile watchlist'i çeker.
    */
    override fun getWatchList(): Flow<List<MovieSearchResult>> =flow{
        val accountId = sessionManager.accountId
        val response = api.getWatchlist(accountId)
        emit(response.results)
    }

    /*
    Session managerdan dönen account id ile watchlist'e ekleme yapar. Verilen id ve boolean ile
    hangi film olduğu ve eklemek veya çıkarmak istediği bilgisini gönderir.
    */
    override fun addToWatchlist(movieId: Int, add: Boolean): Flow<Unit> = flow {
        val accountId = sessionManager.accountId
        val watchListBody = WatchListBody(mediaType = "movie", media_id = movieId, watchlist = add)
        api.addToWatchlist(accountId, watchListBody)
        emit(Unit)
    }

    /*
    Alttaki 4 method da api'den filmleri çeker. HomeScreen'deki tablerde gösterilirler.
    */
    override fun getNowPlaying(): Flow<ResultPages> = flow{
        val response = api.getNowPlayingMovies()
        emit(response)
    }

    override fun getPopular(): Flow<ResultPages> = flow{
        val response = api.getPopularMovies()
        emit(response)
    }
    override fun getUpcoming(): Flow<ResultPages> = flow{
        val response = api.getUpcomingMovies()
        emit(response)
    }
    override fun getTopRated(): Flow<ResultPages> = flow{
        val response = api.getTopRatedMovies()
        emit(response)
    }

}