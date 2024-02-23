package com.filip.grillko.main.restaurant

import com.filip.grillko.Picture
import com.google.gson.annotations.SerializedName

class Restaurant(
    val id: Int,
    val name: String?,
    @SerializedName("delivery_price") val deliveryPrice: Double,
    val rating: Double,
    val logo: Picture
)

class RestaurantData(
    val data: ArrayList<Restaurant>
)