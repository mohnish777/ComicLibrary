package com.example.movielibrary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movielibrary.MovieImage
import com.example.movielibrary.model.Movie
import com.example.movielibrary.view.ImageUrlTemplate.toTMDBImageUrl
import com.example.movielibrary.viewmodel.CollectionViewModel
import com.example.movielibrary.viewmodel.LibraryApiViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MovieDetailsScreen(
    lvm: LibraryApiViewModel,
    cvm: CollectionViewModel,
    paddingValues: PaddingValues,
    navController: NavHostController? = null,
    movieId: Int?
) {
    // Trigger movie detail retrieval when screen loads
    LaunchedEffect(movieId) {
        lvm.retrieveMovieDetailById(movieId)
    }

    val movieDetail: Movie? = lvm.movieDetailById.value
    val collection = cvm.collection.collectAsState()
    val inCollection = collection.value.any { it.id == movieId }

    LaunchedEffect(Unit) {
        cvm.setCurrentMovieEntityWithId(movieId ?: -1)
    }

    if (movieDetail != null) {
        val shouldAdd = !inCollection
        Box(modifier = Modifier.fillMaxSize()) {
            // Main content with bottom padding for button
            MovieDetailContent(
                movie = movieDetail,
                paddingValues = PaddingValues(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateLeftPadding(androidx.compose.ui.unit.LayoutDirection.Ltr),
                    end = paddingValues.calculateRightPadding(androidx.compose.ui.unit.LayoutDirection.Ltr),
                    bottom = paddingValues.calculateBottomPadding() + 80.dp // Extra space for button
                )
            )

            // Sticky "Add to Library" button
            Button(
                onClick = {
                    if(shouldAdd) {
                        cvm.addMovie(movieDetail)
                    } else {
                        cvm.delete(movieDetail)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = paddingValues.calculateBottomPadding() + 16.dp
                    )
                    .height(56.dp)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = if (shouldAdd) Icons.Default.Add else Icons.Default.Delete,
                    contentDescription = null,
                )
                Text(
                    text = if (shouldAdd) "Add to My Collection" else "Remove from My Collection",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    } else {
        // Loading or error state
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Loading movie details...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun MovieDetailContent(
    movie: Movie,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
    ) {
        // Backdrop Image with Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            // Backdrop Image
            MovieImage(
                url = movie.backdropPath?.toTMDBImageUrl(ImageUrlTemplate.BACKDROP_LARGE),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient overlay for better text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            // Movie title overlay
            Text(
                text = movie.title,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // Movie Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Poster and Basic Info Row
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Poster Image
                    MovieImage(
                        url = movie.posterPath?.toTMDBImageUrl(ImageUrlTemplate.POSTER_LARGE),
                        modifier = Modifier
                            .width(120.dp)
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Movie Info
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Original Title (if different)
                        if (movie.originalTitle != movie.title) {
                            Text(
                                text = "Original: ${movie.originalTitle}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        // Release Date
                        Text(
                            text = "Released: ${formatDate(movie.releaseDate)}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Rating
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFFFD700), // Gold color
                                modifier = Modifier.width(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${String.format("%.1f", movie.voteAverage)}/10",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "(${movie.voteCount} votes)",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Language
                        Text(
                            text = "Language: ${movie.originalLanguage.uppercase()}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Popularity
                        Text(
                            text = "Popularity: ${String.format("%.0f", movie.popularity)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Overview Section
        if (movie.overview.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        // Additional Info Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Additional Information",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Movie ID
                InfoRow(label = "Movie ID", value = movie.id.toString())

                // Adult Content
                InfoRow(
                    label = "Content Rating",
                    value = if (movie.adult) "Adult (18+)" else "General Audience"
                )

                // Video Available
                InfoRow(
                    label = "Video Available",
                    value = if (movie.video) "Yes" else "No"
                )

                // Genre IDs (if available)
                if (movie.genreIds.isNotEmpty()) {
                    InfoRow(
                        label = "Genre IDs",
                        value = movie.genreIds.joinToString(", ")
                    )
                }
            }
        }

        // Add some bottom padding
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}
