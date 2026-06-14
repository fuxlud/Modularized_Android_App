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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DogBreedsContent(
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
