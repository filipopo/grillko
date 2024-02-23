package com.filip.grillko.api

import com.filip.grillko.auth.UserData
import com.filip.grillko.auth.login.LoginRequest
import com.filip.grillko.auth.registration.RegistrationRequest
import com.filip.grillko.main.meal.MealData
import com.filip.grillko.main.restaurant.RestaurantData
//import com.filip.grillko.main.restaurant.Restaurant
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
  @POST("auth/login")
  fun login(@Body request: LoginRequest): Call<UserData>

  @POST("auth/register")
  fun register(@Body request: RegistrationRequest): Call<UserData>

  // API name
  @GET("restourants")
  fun getRestaurants(): Call<RestaurantData>

  @GET("restourants/{id}/meals")
  fun getMeals(@Path("id") id: Int): Call<MealData>
}