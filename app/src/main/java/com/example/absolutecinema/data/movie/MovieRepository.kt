package com.example.absolutecinema.data.movie

import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.model.request.WatchListBody
import kotlinx.coroutines.flow.flow

class MovieRepository(
    private val api: MovieApiService,
    private val sessionManager: SessionManager
) : MovieRepositoryInterface {


    private var genreMap: Map<Int, String>? = null

    /*
    GenreMap null değilse, istek atmadan kendisini döndürür, yoksa api'den alır ve güncelleyip döndürür.
    */
    override fun getGenreMap() = flow {
        genreMap?.let { emit(it) }

        val response = api.getGenreList()
        genreMap = response.genres.associate { it.id to it.name }
        emit(genreMap ?: emptyMap())
    }

    /*
    Verilen genre idlerin, genreMapte eşleşen isimlerini döndürür.
    */
    suspend fun getGenreNamesByIds(genreIds: List<Int>?): String? {
        var map: String? = ""
        getGenreMap().collect { result ->
            map = genreIds?.mapNotNull { result[it] }?.joinToString(", ")
        }
        return map
    }

    /*
    Aratıla kelimeyi vererek api'den arama yapar. Sonuç olarak bulunan filmleri döndürür.
    */
    override fun search(word: String) = flow {
        val response = api.searchMovies(word)
        emit(response.results)
    }

    /*
    Verilen id'ye göre api'den filmin detaylarını alır.
    */
    override fun getDetails(id: Int) = flow {
        val response = api.getMovieDetails(id)
        emit(response)
    }

    /*
    Verilen id'ye göre api'den filmin durumunu alır.
    */
    override fun getMovieState(id: Int) = flow {
        val response = api.getMovieAccountStates(id)
        emit(response)
    }

    /*
    Session managerdan dönen account id ile watchlist'i çeker.
    */
    override fun getWatchList() = flow {
        val accountId = sessionManager.accountId
        emit(api.getWatchlist(accountId))
    }

    /*
    Session managerdan dönen account id ile watchlist'e ekleme yapar. Verilen id ve boolean ile
    hangi film olduğu ve eklemek veya çıkarmak istediği bilgisini gönderir.
    */
    override fun addToWatchlist(movieId: Int, add: Boolean) = flow {
        val accountId = sessionManager.accountId
        val watchListBody = WatchListBody(mediaType = "movie", mediaId = movieId, watchlist = add)
        api.addToWatchlist(accountId, watchListBody)
        emit(Unit)
    }

    /*
    Alttaki 4 method da api'den filmleri çeker. HomeScreen'deki tablerde gösterilirler.
    */
    override fun getNowPlaying() = flow {
        emit(api.getNowPlayingMovies())
    }

    override fun getPopular() = flow {
        emit(api.getPopularMovies())
    }

    override fun getUpcoming() = flow {
        emit(api.getUpcomingMovies())
    }

    override fun getTopRated() = flow {
        emit( api.getTopRatedMovies())
    }

}