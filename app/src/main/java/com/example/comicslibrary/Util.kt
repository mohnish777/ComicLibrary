package com.example.comicslibrary

import java.security.MessageDigest


fun generateHash(ts: String, privateKey: String, publicKey: String): String {
    val input = ts + privateKey + publicKey
    println("DEBUG - Hash input: '$input'")
    val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
    val hash = bytes.joinToString("") { "%02x".format(it) }
    println("DEBUG - Generated hash: '$hash'")
    return hash
}








