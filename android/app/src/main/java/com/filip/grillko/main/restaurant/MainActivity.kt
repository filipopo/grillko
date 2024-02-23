package com.filip.grillko.main.restaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.filip.grillko.R
import com.filip.grillko.api.Api
import com.filip.grillko.api.RetrofitClient
import com.filip.grillko.databinding.ActivityMainBinding
import com.filip.grillko.main.meal.MealActivity
import com.filip.grillko.startActivityAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setIcon(R.drawable.logo)

        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvRestaurants.layoutManager = manager

        getRestaurantList()
    }

    private fun getRestaurantList() {
        Log.d("ATVSS12345", "getRestaurants")
        RetrofitClient.getClient().create(Api::class.java).getRestaurants().enqueue(object :
            Callback<RestaurantData> {
            override fun onResponse(
                call: Call<RestaurantData>,
                response: Response<RestaurantData>
            ) {
                Log.d("ATVSS12345", "getRestaurants: response code == ${response.code()}")
                val restaurantList: ArrayList<Restaurant> = ArrayList()

                if (response.body() == null) {
                    Log.d("ATVSS12345", "getRestaurants: response body == null")
                    return
                } else if (!response.isSuccessful) {
                    Log.d("ATVSS12345", "getRestaurants: response !isSuccessful")
                    return
                }

                val restaurantData = response.body()!!
                restaurantList.addAll(restaurantData.data)
                setRestaurauntAdapter(restaurantList)
            }

            override fun onFailure(call: Call<RestaurantData>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.d("ATVSS12345", "getRestaurants failure: $t")
            }
        })
    }

    private fun setRestaurauntAdapter(restaurauntList: ArrayList<Restaurant>) {
        val adapter = RestaurantsAdapter(restaurauntList, onItemClick = { restaurant ->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra("id", restaurant.id)
            intent.putExtra("name", restaurant.name)

            startActivity(intent)
        })

        binding.rvRestaurants.adapter = adapter
    }

    private fun logout() {
        deleteSharedPreferences("main")
        startActivityAuth()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            logout()
        }

        return super.onOptionsItemSelected(item)
    }
}