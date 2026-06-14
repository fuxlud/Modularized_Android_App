package com.example.fruitlist.data

import com.example.fruitlist.model.Fruit

object FruitRepository {
    val fruits = listOf(
        Fruit(name = "Apple", emoji = "🍎"),
        Fruit(name = "Banana", emoji = "🍌"),
        Fruit(name = "Strawberry", emoji = "🍓")
    )
}
