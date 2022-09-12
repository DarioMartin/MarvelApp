package com.dariomartin.talentoapp.data.remote

import java.util.*

class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String,
    val modified: Date,
    val resourceURI: String,
    val urls: List<String>,
    val thumbnail: Thumbnail,
    val comics: Collection,
    val series: Collection,
    val stories: Collection,
    val events: Collection
)

data class Thumbnail(val path: String, val extension: String)

data class Collection(
    val available: Int,
    val collectionUri: String,
    val items: List<CollectionItem>
)

data class CollectionItem(val resourceURI: String, val name: String, val type: String?)
