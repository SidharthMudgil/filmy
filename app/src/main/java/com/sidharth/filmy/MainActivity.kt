package com.sidharth.filmy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sidharth.movie_details.movieDetailsNavGraph
import com.sidharth.navigation.Routes
import com.sidharth.search.searchNavGraph
import com.sidharth.ui.theme.FilmyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FilmyTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.SEARCH,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        searchNavGraph(navController)
                        movieDetailsNavGraph(navController)
                    }
                }
            }
        }
    }
}
