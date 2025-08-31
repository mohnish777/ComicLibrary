package com.example.comicslibrary

import com.example.comicslibrary.model.CharacterDataWrapper
import com.example.comicslibrary.model.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Example of how to make the API call
class ExampleApiCall {

    
    fun fetchCharacters() {
        // Get authentication parameters
        val authParams = ApiService.getAuthParams()

        // Debug: Print the parameters being sent
        println("DEBUG - API Parameters:")
        println("apikey: ${authParams["apikey"]}")
        println("ts: ${authParams["ts"]}")
        println("hash: ${authParams["hash"]}")

        // Make the API call
        val call = ApiService.api.getCharacters(
            apikey = authParams["apikey"]!!,
            ts = authParams["ts"]!!,
            hash = authParams["hash"]!!,
            nameStartsWith = "spider", // or "Spider" to search for characters starting with "Spider"
            limit = 20,
            offset = 0
        )

        
        call.enqueue(object : Callback<CharacterDataWrapper> {
            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                if (response.isSuccessful) {
                    val characterData = response.body()
                    characterData?.let { data ->
                        println("Status: ${data.status}")
                        println("Total characters: ${data.data.total}")
                        println("Characters returned: ${data.data.count}")
                        
                        // Loop through characters
                        data.data.results.forEach { character ->
                            println("Character: ${character.name}")
                            println("Description: ${character.description}")
                            println("Comics available: ${character.comics.available}")
                            println("---")
                        }
                    }
                } else {
                    println("Error: ${response.code()} - ${response.message()}")
                    println("Error Body: ${response.body()} - ${response.headers()}")
                }
            }
            
            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                println("Network error: ${t.message}")
            }
        })
    }
    
    // Example with search
    fun searchCharacters(searchTerm: String) {
        val authParams = ApiService.getAuthParams()
        
        val call = ApiService.api.getCharacters(
            apikey = authParams["apikey"]!!,
            ts = authParams["ts"]!!,
            hash = authParams["hash"]!!,
            nameStartsWith = searchTerm,
            limit = 10
        )
        
        call.enqueue(object : Callback<CharacterDataWrapper> {
            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                if (response.isSuccessful) {
                    response.body()?.data?.results?.forEach { character ->
                        println("Found: ${character.name}")
                    }
                }
            }
            
            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                println("Search failed: ${t.message}")
            }
        })
    }
}

