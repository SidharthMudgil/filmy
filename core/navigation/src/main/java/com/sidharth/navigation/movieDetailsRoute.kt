package com.sidharth.navigation

fun movieDetailsRoute(movieId: Int): String {
    return "${Routes.MOVIE_DETAILS}/$movieId"
}
