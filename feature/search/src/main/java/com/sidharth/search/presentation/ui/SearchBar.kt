package com.sidharth.search.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sidharth.ui.R

@Composable
internal fun SearchBar(
    initialQuery: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable { mutableStateOf(initialQuery) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE5E7EB),
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = "Search",
            tint = Color(0xFF9CA3AF)
        )

        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = text,
            onValueChange = {
                Log.d("SearchBar", "onValueChange = '$it'")
                text = it
                onQueryChange(it)
            },
                    singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = "Search movies",
                        color = Color(0xFF9CA3AF),
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    SearchBar(initialQuery = "", onQueryChange = {})
}