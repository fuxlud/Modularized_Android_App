package com.example.dogbreeds.ui.dogbreeds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.example.dogbreeds.data.DogBreedRepository
import com.example.dogbreeds.model.DogBreed
import com.example.dogbreeds.ui.theme.DogBreedsTheme

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

@Composable
private fun DogBreedsContent(
    state: DogBreedsUiState,
    modifier: Modifier = Modifier
) {
    when (state) {
        DogBreedsUiState.Loading -> DogBreedsMessage(
            text = "Loading dog breeds...",
            modifier = modifier
        )

        DogBreedsUiState.Error -> DogBreedsMessage(
            text = "Could not load dog breeds.",
            modifier = modifier
        )

        is DogBreedsUiState.Loaded -> LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = state.dogBreeds,
                key = { dogBreed -> dogBreed.name },
                contentType = { "dogBreed" }
            ) { dogBreed ->
                DogBreedCell(dogBreed = dogBreed)
            }
        }
    }
}

@Composable
private fun DogBreedsMessage(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

private sealed interface DogBreedsUiState {
    data object Loading : DogBreedsUiState
    data object Error : DogBreedsUiState
    data class Loaded(val dogBreeds: List<DogBreed>) : DogBreedsUiState
}

@Preview(showBackground = true)
@Composable
private fun DogBreedsScreenPreview() {
    DogBreedsTheme {
        DogBreedsContent(state = DogBreedsUiState.Loaded(sampleDogBreeds))
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE
)
@Composable
private fun DogBreedsScreenDarkPreview() {
    DogBreedsTheme(darkTheme = true) {
        DogBreedsContent(state = DogBreedsUiState.Loaded(sampleDogBreeds))
    }
}

private val sampleDogBreeds = listOf(
    DogBreed("Akita"),
    DogBreed("Beagle"),
    DogBreed("French Bulldog")
)
