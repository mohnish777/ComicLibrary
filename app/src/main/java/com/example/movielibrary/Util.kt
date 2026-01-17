package com.example.movielibrary

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import java.security.MessageDigest


fun generateHash(ts: String, privateKey: String, publicKey: String): String {
    val input = ts + privateKey + publicKey
    println("DEBUG - Hash input: '$input'")
    val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
    val hash = bytes.joinToString("") { "%02x".format(it) }
    println("DEBUG - Generated hash: '$hash'")
    return hash
}

@Composable
fun MovieImage(
    url: String?,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}








