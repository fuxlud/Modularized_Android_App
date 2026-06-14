package com.example.dogbreeds.ui.dogbreeds

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.dogbreeds.data.DogBreedRepository

@Composable
fun DogBreedsScreen(
    modifier: Modifier = Modifier,
    repository: DogBreedRepository = remember { DogBreedRepository() }
) {
    var state by remember { mutableStateOf<DogBreedsUiState>(DogBreedsUiState.Loading) }

    LaunchedEffect(repository) {
        state = runCatching { repository.getDogBreeds() }
            .fold(
                onSuccess = { DogBreedsUiState.Loaded(it) },
                onFailure = { DogBreedsUiState.Error }
            )
    }

    DogBreedsContent(
        state = state,
        modifier = modifier
    )
}
