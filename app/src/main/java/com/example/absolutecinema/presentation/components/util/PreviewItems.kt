package com.example.absolutecinema.presentation.components.util

import com.example.absolutecinema.data.model.response.AuthorDomainModel
import com.example.absolutecinema.data.model.response.CastDomainModel
import com.example.absolutecinema.data.model.response.ReviewResultDomainModel
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel

object PreviewItems {
    val overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."

    val reviews = listOf(
        ReviewResultDomainModel(
            author = "John Doe",
            authorDetails = AuthorDomainModel(
                name = "John Doe",
                username = "johndoe",
                avatarPath = null,
                rating = 8.5
            ),
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        ),
        ReviewResultDomainModel(
            author = "Jane Doe",
            authorDetails = AuthorDomainModel(
                name = "Jane Doe",
                username = "janedoe",
                avatarPath = null,
                rating = null
            ),
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        )
    )

    val cast = listOf(
        CastDomainModel(
            name = "John Doe",
            character = "Character",
            profilePath = null
        ),
        CastDomainModel(
            name = "Jane Doe",
            character = "Character",
            profilePath = null
        )
    )

    val movie = MovieSearchResultDomainModel(
        genreIds = listOf(1, 2),
        id = 0,
        originalTitle = "Demon Slayer Kimetsu No Yaiba Infinity Castle",
        overview = overview,
        popularity = 0.0,
        posterPath = "",
        releaseDate = "2018-09-08",
        title = "Demon Slayer Kimetsu No Yaiba Infinity Castle",
        voteAverage = 7.8,
        genre = "Anime, Action, Animation, Drama, Fantasy"
    )

    val movieList = listOf( movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie)


}