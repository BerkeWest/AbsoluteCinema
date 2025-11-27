package com.example.absolutecinema.data.paging

enum class PagingEnum {
    NOW_PLAYING,
    UPCOMING,
    TOP_RATED,
    POPULAR
}

fun TabIndexToTab(tabIndex: Int): PagingEnum {
    when (tabIndex) {
        0 -> return PagingEnum.NOW_PLAYING
        1 -> return PagingEnum.UPCOMING
        2 -> return PagingEnum.TOP_RATED
        3 -> return PagingEnum.POPULAR
        else -> return PagingEnum.NOW_PLAYING
    }
}

