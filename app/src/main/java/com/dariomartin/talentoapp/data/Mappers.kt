package com.dariomartin.talentoapp.data

import com.dariomartin.talentoapp.data.remote.model.Result
import com.dariomartin.talentoapp.domain.model.Resource
import com.dariomartin.talentoapp.domain.model.Character as ModelCharacter

fun Result.toModelCharacter(): ModelCharacter {
    return ModelCharacter(
        id = this.id,
        name = this.name,
        imageUrl = "${this.thumbnail.path.convertToHttps()}.${this.thumbnail.extension}",
        description = this.description,
        comics = this.comics.items.map { Resource(it.name, it.resourceURI) },
        series = this.series.items.map { Resource(it.name, it.resourceURI) },
        events = this.events.items.map { Resource(it.name, it.resourceURI) }
    )
}