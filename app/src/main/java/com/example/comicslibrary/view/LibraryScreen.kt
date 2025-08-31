package com.example.comicslibrary.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicslibrary.ExampleApiCall

@Composable
fun LibraryScreen(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "This is Library Screen")
        ExampleApiCall().fetchCharacters()

    }
}


@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    LibraryScreen(modifier = Modifier.fillMaxSize())
}

