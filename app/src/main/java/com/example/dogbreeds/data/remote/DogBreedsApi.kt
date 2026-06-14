package com.example.dogbreeds.data.remote

import com.example.dogbreeds.model.DogBreed
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.json.JSONObject

class DogBreedsApi(
    private val client: HttpClient = HttpClient(Android)
) {
    suspend fun getAllBreeds(): List<DogBreed> {
        val response = client
            .get("https://dog.ceo/api/breeds/list/all")
            .bodyAsText()

        val breeds = JSONObject(response).getJSONObject("message")

        return breeds.keys().asSequence()
            .flatMap { breed ->
                val subBreeds = breeds.getJSONArray(breed)
                if (subBreeds.length() == 0) {
                    sequenceOf(DogBreed(name = breed.toDisplayName()))
                } else {
                    (0 until subBreeds.length()).asSequence().map { index ->
                        DogBreed(name = "${subBreeds.getString(index)} $breed".toDisplayName())
                    }
                }
            }
            .sortedBy { it.name }
            .toList()
    }

    private fun String.toDisplayName(): String =
        split(" ")
            .joinToString(" ") { word ->
                word.replaceFirstChar { char ->
                    if (char.isLowerCase()) char.titlecase() else char.toString()
                }
            }
}
