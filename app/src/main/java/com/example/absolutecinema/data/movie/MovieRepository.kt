package com.example.absolutecinema.data.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.absolutecinema.data.datasource.local.MovieLocalDataSource
import com.example.absolutecinema.data.model.request.WatchListBody
import com.example.absolutecinema.data.paging.MoviePagingSource
import com.example.absolutecinema.data.paging.PagingEnum
import com.example.absolutecinema.domain.mapper.MovieSearchResultDomainMapper.toDomain
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val api: MovieApiService,
    private val localDataSource: MovieLocalDataSource,
) : MovieRepositoryInterface {


    private var genreMap: Map<Int, String>? = null

    /*
    GenreMap null değilse, istek atmadan kendisini döndürür, yoksa api'den alır ve güncelleyip döndürür.
    */
    override fun getGenreMap() = flow {
        if (genreMap?.isNotEmpty() == true) genreMap?.let { emit(it) }
        else {
            val response = api.getGenreList()
            genreMap = response.genres.associate { it.id to it.name }
            emit(genreMap ?: emptyMap())
        }
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

    override fun getCast(id: Int) = flow {
        val response = api.getMovieCast(id)
        emit(response.cast)
    }

    override fun getReviews(id: Int) = flow {
        val response = api.getMovieReviews(id)
        emit(response)
    }

    override fun getRecommendations(id: Int) = flow {
        val response = api.getRecommendations(id)
        emit(response)
    }

    /*
    Session managerdan dönen account id ile watchlist'i çeker.
    */
    override fun getWatchList() = flow {
        val accountId = localDataSource.getLocalAccountId()
        emit(api.getWatchlist(accountId))
    }

    /*
    Session managerdan dönen account id ile watchlist'e ekleme yapar. Verilen id ve boolean ile
    hangi film olduğu ve eklemek veya çıkarmak istediği bilgisini gönderir.
    */
    override fun addToWatchlist(movieId: Int, add: Boolean) = flow {
        val accountId = localDataSource.getLocalAccountId()
        val watchListBody = WatchListBody(mediaType = "movie", mediaId = movieId, watchlist = add)
        api.addToWatchlist(accountId, watchListBody)
        emit(Unit)
    }

    /*
    Alttaki 4 method da api'den filmleri çeker. HomeScreen'deki tablerde gösterilirler.
    */
    override fun getNowPlaying(page: Int?) = flow {
        emit(api.getNowPlayingMovies(page ?: 1))
    }

    override fun getPopular() = flow {
        emit(api.getPopularMovies())
    }

    override fun getUpcoming() = flow {
        emit(api.getUpcomingMovies())
    }

    override fun getTopRated() = flow {
        emit(api.getTopRatedMovies())
    }

    override fun getMoviePager(call: PagingEnum) =
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            MoviePagingSource(call = call, api = api)
        }.flow.map { it.map { dto -> dto.toDomain() } }

}