package com.example.dogbreeds.ui.dogbreeds

import com.example.dogbreeds.model.DogBreed

sealed interface DogBreedsUiState {
    data object Loading : DogBreedsUiState
    data object Error : DogBreedsUiState
    data class Loaded(val dogBreeds: List<DogBreed>) : DogBreedsUiState
}
