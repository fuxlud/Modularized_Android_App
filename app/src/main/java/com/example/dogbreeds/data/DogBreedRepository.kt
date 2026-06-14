package com.example.dogbreeds.data

import com.example.dogbreeds.data.remote.DogBreedsApi
import com.example.dogbreeds.model.DogBreed

class DogBreedRepository(
    private val api: DogBreedsApi = DogBreedsApi()
) {
    suspend fun getDogBreeds(): List<DogBreed> = api.getAllBreeds()
}
