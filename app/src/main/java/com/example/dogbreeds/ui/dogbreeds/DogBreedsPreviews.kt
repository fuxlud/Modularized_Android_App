package com.example.dogbreeds.ui.dogbreeds

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.example.dogbreeds.ui.theme.DogBreedsTheme

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
