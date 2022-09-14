package com.dariomartin.marvelapp

import com.dariomartin.marvelapp.data.remote.model.*
import com.dariomartin.marvelapp.domain.model.Character


fun createCharactersResponse(
    characters: List<Character>,
    offset: Int,
    limit: Int,
    query: String
): CharactersResponse {

    return CharactersResponse(
        attributionHTML = "",
        attributionText = "",
        code = 200,
        copyright = "",
        data = createData(characters, offset, limit, query),
        etag = "",
        status = ""
    )
}

private fun createData(characters: List<Character>, offset: Int, limit: Int, query: String): Data {

    val remoteCharacters = characters.map {
        Result(
            comics = Comics(
                available = 0,
                collectionURI = "",
                items = emptyList(),
                returned = 0
            ),
            description = it.description,
            events = Events(
                available = 0,
                collectionURI = "",
                items = emptyList(),
                returned = 0
            ),
            id = it.id,
            modified = "",
            name = it.name,
            resourceURI = "",
            series = Series(
                available = 0,
                collectionURI = "",
                items = emptyList(),
                returned = 0
            ),
            stories = Stories(
                available = 0,
                collectionURI = "",
                items = emptyList(),
                returned = 0
            ),
            thumbnail = Thumbnail(
                extension = "jpg",
                path = it.imageUrl.dropLast(4)
            ),
            urls = emptyList()
        )
    }

    val results = remoteCharacters.drop(offset).take(limit).filter { it.name.startsWith(query) }

    return Data(
        count = results.size,
        limit = limit,
        offset = offset,
        results = results,
        total = characters.size
    )
}