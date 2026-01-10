package com.sidharth.movie_details

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sidharth.movie_details.presentation.MovieDetailsViewModel
import com.sidharth.movie_details.presentation.ui.MovieDetailsScreen
import com.sidharth.navigation.Args
import com.sidharth.navigation.Routes

fun NavGraphBuilder.movieDetailsNavGraph(
    navController: NavController
) {
    composable(
        route = "${Routes.MOVIE_DETAILS}/{${Args.MOVIE_ID}}",
        arguments = listOf(
            navArgument(Args.MOVIE_ID) {
                type = NavType.IntType
            }
        )
    ) {
        val viewModel: MovieDetailsViewModel = hiltViewModel()

        MovieDetailsScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() }
        )
    }
}
