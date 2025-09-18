package com.example.comicslibrary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comicslibrary.MovieImage
import com.example.comicslibrary.createMovieDetailRoute
import com.example.comicslibrary.model.Movie
import com.example.comicslibrary.model.api.NetworkResult
import com.example.comicslibrary.view.ImageUrlTemplate.toTMDBImageUrl
import com.example.comicslibrary.viewmodel.LibraryApiViewModel

@Composable
fun LibraryScreen(
    modifier: Modifier,
    navController: NavHostController? = null,
    vm: LibraryApiViewModel,
    paddingValues: PaddingValues
) {

    val result = vm.movies.collectAsState()
    val text = vm.queryText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding(), top = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = text.value,
            onValueChange = { it ->
                vm.onQueryUpdate(it)
            },
            label = { Text(text = "Character search") },
            placeholder = { Text(text = "Character") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result.value) {
                is NetworkResult.Success -> {
                    ShowCharacterList(results = result.value.data?.results, navController)
                }

                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.value.message}")
                }

                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResult.Initial -> {
                    Text(text = "Search for a character")
                }
            }

        }

    }

}

@Composable
private fun ShowCharacterList(
    results: List<Movie>?,
    navController: NavHostController?
) {
    results?.let {
        LazyColumn {
            item {
                Text(
                    text = "Best movies of all time",
                    modifier = Modifier.padding(
                        start = 8.dp,
                        top = 4.dp
                    ),
                    fontSize = 12.sp
                )
            }
            items(results) { movie ->
                val imageUrl = movie.posterPath?.toTMDBImageUrl(ImageUrlTemplate.POSTER_SMALL) ?: ""
                val title = movie.title
                val overview = movie.overview
                val voteAverage = movie.voteAverage
                val releaseDate = movie.releaseDate
                val id = movie.id

                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            navController?.navigate(createMovieDetailRoute(id))
                        }
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        MovieImage(
                            url = imageUrl,
                            modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp)
                        )

                        Column(modifier = Modifier.padding(4.dp)) {
                            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    }

                    Text(text = overview, maxLines = 4, fontSize = 14.sp)
                }
            }

        }

    }

}


@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
}

