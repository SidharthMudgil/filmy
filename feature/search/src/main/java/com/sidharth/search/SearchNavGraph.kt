package com.sidharth.search

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sidharth.navigation.Routes
import com.sidharth.navigation.movieDetailsRoute
import com.sidharth.search.presentation.SearchViewModel
import com.sidharth.search.presentation.ui.SearchScreen

fun NavGraphBuilder.searchNavGraph(
    navController: NavController
) {
    composable(Routes.SEARCH) {
        val viewModel: SearchViewModel = hiltViewModel()

        SearchScreen(viewModel = viewModel) { movieId ->
            navController.navigate(
                movieDetailsRoute(movieId)
            )
        }
    }
}