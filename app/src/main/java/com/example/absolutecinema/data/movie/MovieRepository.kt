package com.example.absolutecinema.data.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.absolutecinema.data.datasource.local.MovieLocalDataSource
import com.example.absolutecinema.data.model.request.WatchListBody
import com.example.absolutecinema.data.paging.home.NowPlayingPagingSource
import com.example.absolutecinema.data.paging.home.PopularPagingSource
import com.example.absolutecinema.data.paging.home.TopRatedPagingSource
import com.example.absolutecinema.data.paging.home.UpcomingPagingSource
import com.example.absolutecinema.data.paging.search.SearchPagingSource
import com.example.absolutecinema.data.paging.watchlist.WatchListPagingSource
import com.example.absolutecinema.domain.mapper.MovieSearchResultDomainMapper.toDomain
import kotlinx.coroutines.flow.emitAll
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

    override fun getSearchPager(word: String) =
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            SearchPagingSource(text = word, api = api)
        }.flow.map { it.map { dto -> dto.toDomain() } }

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

        emitAll(Pager(PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            WatchListPagingSource(api = api, accountId = accountId)
        }.flow.map { pagingData -> pagingData.map { it.toDomain() } })
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
    override fun getTrending() = flow {
        emit(api.getTrendingMovies())
    }

    override fun getNowPlayingPager() =
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            NowPlayingPagingSource(api = api)
        }.flow.map { it.map { dto -> dto.toDomain() } }

    override fun getUpcomingPager() =
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            UpcomingPagingSource(api = api)
        }.flow.map { it.map { dto -> dto.toDomain() } }

    override fun getTopRatedPager() =
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            TopRatedPagingSource(api = api)
        }.flow.map { it.map { dto -> dto.toDomain() } }

    override fun getPopularPager() =
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            PopularPagingSource(api = api)
        }.flow.map { it.map { dto -> dto.toDomain() } }

}