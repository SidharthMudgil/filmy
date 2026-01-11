package com.sidharth.movie_details.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sidharth.model.Movie
import com.sidharth.ui.R

@Composable
internal fun MovieDetailsContent(
    movie: Movie?,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
    ) {
        movie?.let {
            AsyncImage(
                model = it.posterUrl,
                contentDescription = it.title,
                fallback = painterResource(R.drawable.img_cinema),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color.Black.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = it.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 24.sp
                )
            )
            Text(
                text = it.overview,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsContentPreview() {
    val dummyMovie = Movie(
        id = 1,
        title = "Avengers: Endgame",
        overview = "After the devastating events of Avengers: Infinity War, the universe is in ruins. The remaining Avengers must come together to undo Thanos' actions and restore balance to the universe.",
        posterUrl = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg"
    )

    MovieDetailsContent(movie = dummyMovie)
}
