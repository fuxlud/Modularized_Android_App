package com.example.fruitlist.ui.fruit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fruitlist.data.FruitRepository
import com.example.fruitlist.model.Fruit
import com.example.fruitlist.ui.theme.FruitListTheme

@Composable
fun FruitListScreen(
    modifier: Modifier = Modifier,
    fruits: List<Fruit> = FruitRepository.fruits
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        fruits.forEach { fruit ->
            FruitCell(fruit = fruit)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FruitListScreenPreview() {
    FruitListTheme {
        FruitListScreen()
    }
}
