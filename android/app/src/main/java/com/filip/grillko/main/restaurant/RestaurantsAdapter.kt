package com.filip.grillko.main.restaurant

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.filip.grillko.databinding.ItemRestaurauntBinding

class RestaurantsAdapter(
    private val restaurantList: ArrayList<Restaurant>,
    private val onItemClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurauntBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.bind(restaurant)
    }

    inner class RestaurantViewHolder(private val binding: ItemRestaurauntBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.apply {
                tvName.text = restaurant.name
                tvPrice.text = "${restaurant.deliveryPrice}"
                tvGrade.text = restaurant.rating.toString()
                Glide.with(ivRestaurant.context).load(restaurant.logo.path).into(ivRestaurant)

                root.setOnClickListener {
                    Log.d("ATVSS12345", "Item on click")
                    onItemClick(restaurant)
                }
            }
        }
    }
}