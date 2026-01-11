package com.sidharth.search.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.sidharth.model.SearchItem
import com.sidharth.ui.R

@Composable
internal fun SearchContent(
    result: List<SearchItem>,
    onMovieClicked: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = result,
            key = { it.id }
        ) { searchItem ->
            ListItem(
                searchItem = searchItem,
                onClick = { onMovieClicked(searchItem.id) }
            )
        }
    }
}

@Composable
private fun ListItem(
    searchItem: SearchItem,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.clickable { onClick() }
    ) {
        AsyncImage(
            model = searchItem.posterUrl,
            fallback = painterResource(R.drawable.img_cinema),
            contentDescription = searchItem.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.Black.copy(alpha = 0.2f)),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = searchItem.title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchContentPreview() {
    val searchItems = listOf(
        SearchItem(
            id = 1,
            title = "Avengers: Endgame",
            posterUrl = ""
        ),
        SearchItem(
            id = 2,
            title = "Avengers: Endgame",
            posterUrl = ""
        ),
        SearchItem(
            id = 3,
            title = "Avengers: Endgame",
            posterUrl = ""
        ),
    )

    SearchContent(result = searchItems, onMovieClicked = {})
}

@Preview(showBackground = true)
@Composable
private fun ListItemPreview() {
    val searchItem = SearchItem(
        id = 1,
        title = "Avengers: Endgame",
        posterUrl = ""
    )

    ListItem(searchItem = searchItem, onClick = {})
}
