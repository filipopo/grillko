package com.filip.grillko.main.meal

import com.filip.grillko.Picture

class Meal(
    val name: String,
    val price: Double,
    val picture: Picture
)

class MealData(
    val data: ArrayList<Meal>
)