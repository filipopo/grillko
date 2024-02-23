package com.filip.grillko

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.filip.grillko.auth.AuthActivity
import com.filip.grillko.auth.User
import com.filip.grillko.main.restaurant.MainActivity
import java.net.InetAddress

fun Activity.saveUser(user: User) {
    val sharedPrefs = getSharedPreferences("main", Context.MODE_PRIVATE)
    sharedPrefs.edit()
        .putInt("id", user.id)
        .putString("email", user.email)
        .apply()
}

fun Activity.isUserSaved(): Boolean {
    val sharedPrefs = getSharedPreferences("main", Context.MODE_PRIVATE)
    val userId = sharedPrefs.getInt("id", -1)

    return userId != -1
}

fun Activity.startActivityAuth() {
    val intent = Intent(this, AuthActivity::class.java)
    startActivity(intent)
    finish()
}

fun Activity.startActivityMain() {
   val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
}

fun Activity.internetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

    fun pingGoogle(): Boolean {
        return true
        //val address = InetAddress.getByName("www.google.com")
        //return !address.equals("")
    }

    val hasInternet = when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> pingGoogle()
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> pingGoogle()
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> pingGoogle()
        else -> false
    }

    return hasInternet
}