package com.example.comicslibrary.model

data class CharacterDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: CharacterDataContainer,
    val etag: String
)

data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val comics: ComicList,
    val series: SeriesList,
    val stories: StoryList,
    val events: EventList,
    val urls: List<Url>
)

data class ComicList(
    val available: Int,
    val collectionURI: String,
    val items: List<ComicSummary>,
    val returned: Int
)

data class ComicSummary(
    val resourceURI: String,
    val name: String
)

data class SeriesList(
    val available: Int,
    val collectionURI: String,
    val items: List<SeriesSummary>,
    val returned: Int
)

data class SeriesSummary(
    val resourceURI: String,
    val name: String
)

data class StoryList(
    val available: Int,
    val collectionURI: String,
    val items: List<StorySummary>,
    val returned: Int
)

data class StorySummary(
    val resourceURI: String,
    val name: String,
    val type: String
)

data class EventList(
    val available: Int,
    val collectionURI: String,
    val items: List<EventSummary>,
    val returned: Int
)

data class EventSummary(
    val resourceURI: String,
    val name: String
)

data class Url(
    val type: String,
    val url: String
)

