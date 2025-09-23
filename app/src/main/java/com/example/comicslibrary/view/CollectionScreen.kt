package com.example.comicslibrary.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comicslibrary.MovieImage
import com.example.comicslibrary.model.Note
import com.example.comicslibrary.model.db.MovieEntity
import com.example.comicslibrary.model.db.MovieEntity.Companion.toMovie
import com.example.comicslibrary.model.db.NotesEntity
import com.example.comicslibrary.viewmodel.CollectionViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CollectionScreen(
    cvm: CollectionViewModel,
    paddingValues: PaddingValues,
    navController: NavHostController? = null
) {
    val collections = cvm.collection.collectAsState()
    val notes = cvm.notes.collectAsState()
    var expandOnlyOneCard by remember { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        // Header
        Text(
            text = "My Collection",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
        )

        if (collections.value.isEmpty()) {
            EmptyCollectionState()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = collections.value,
                    key = { movieEntity -> movieEntity.id }
                ) { movieEntity ->

                    val filteredNotes = notes.value.filter { it.movieId == movieEntity.id }
                    MovieCollectionCard(
                        movieEntity = movieEntity,
                        notes = filteredNotes,
                        expandOnlyOneCard = expandOnlyOneCard,
                        cvm = cvm,
                        onClickExpandOnlyOneCard = {
                            if (expandOnlyOneCard == movieEntity.id) {
                                expandOnlyOneCard = -1
                            } else {
                                expandOnlyOneCard = movieEntity.id
                            }
                                                   },
                        onDelete = {
                            cvm.delete(movieEntity.toMovie())
                        }
                    )
                }

                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun MovieCollectionCard(
    movieEntity: MovieEntity,
    notes: List<NotesEntity>,
    cvm: CollectionViewModel,
    onDelete: () -> Unit,
    onClickExpandOnlyOneCard: () -> Unit,
    expandOnlyOneCard: Int
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Main card content
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Movie Poster
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { onClickExpandOnlyOneCard() }
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    MovieImage(
                        url = movieEntity.posterPathUrlConverted ?: "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Movie Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = movieEntity.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", movieEntity.voteAverage),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "(${movieEntity.voteCount} votes)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Release Date
                    Text(
                        text = formatDate(movieEntity.releaseDate),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                // Action buttons
                Column {
                    // Delete button
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete from collection",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Expand/Collapse button
                    IconButton(
                        onClick = { isExpanded = !isExpanded },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Expanded content
            AnimatedVisibility(
                visible = expandOnlyOneCard == movieEntity.id || isExpanded,
                enter = expandVertically() + fadeIn()
            ) {
                NotesSection(movieEntity = movieEntity, notes = notes.filter { it.movieId == movieEntity.id }, cvm)
            }
        }
    }
}


@Composable
private fun NotesSection(
    movieEntity: MovieEntity,
    notes: List<NotesEntity>,
    cvm: CollectionViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp) // Limit max height
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Notes (${notes.size})",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (notes.isEmpty()) {
            Text(
                text = "No notes for this movie yet",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(8.dp))
            // Make notes scrollable within limited height
            LazyColumn(
                modifier = Modifier.heightIn(max = 200.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(notes) { note ->
                    NoteItem(note = note, cvm = cvm)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Make AddNote section scrollable
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp) // Limit height to leave room for keyboard
        ) {
            LazyColumn {
                item {
                    AddNote(movieId = movieEntity.id, cvm)
                }
            }
        }

    }
}

@Composable
private fun NoteItem(
    note: NotesEntity,
    cvm: CollectionViewModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = note.text,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = { cvm.deleteNote(note.id) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete note",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun AddNote(movieId: Int, cvm: CollectionViewModel) {
    var addNoteToElement by remember { mutableIntStateOf(-1) }
    var noteTitle by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }
    if (addNoteToElement == movieId) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Add Note",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = noteTitle,
                    onValueChange = { noteTitle = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    label = { Text("Note") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 80.dp, max = 120.dp),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            noteTitle = ""
                            noteText = ""
                            addNoteToElement = -1
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (noteTitle.isNotBlank() && noteText.isNotBlank()) {
                                cvm.addNote(NotesEntity.fromNotes(Note(movieId, noteTitle, noteText)))
                                noteTitle = ""
                                noteText = ""
                                addNoteToElement = -1
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = noteTitle.isNotBlank() && noteText.isNotBlank()
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    } else {
        Button(
            onClick = { addNoteToElement = movieId }
        ) {
            Text("Write Note")
        }
    }

}

@Composable
private fun ExpandedMovieContent(movieEntity: MovieEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        // Overview (truncated for better scrolling)
        if (movieEntity.overview.isNotEmpty()) {
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movieEntity.overview,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Additional Details
        Text(
            text = "Details",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        DetailRow(label = "Original Title", value = movieEntity.originalTitle)
        DetailRow(label = "Language", value = movieEntity.originalLanguage.uppercase())
        DetailRow(label = "Popularity", value = String.format("%.1f", movieEntity.popularity))
        DetailRow(label = "Adult Content", value = if (movieEntity.adult) "Yes" else "No")
        DetailRow(label = "Video Available", value = if (movieEntity.video) "Yes" else "No")

        if (movieEntity.genreIds.isNotEmpty()) {
            DetailRow(label = "Genre IDs", value = movieEntity.genreIds)
        }
        
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun EmptyCollectionState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "📚",
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your collection is empty",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Start adding movies to your collection from the Library",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

// Helper function for date formatting
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
