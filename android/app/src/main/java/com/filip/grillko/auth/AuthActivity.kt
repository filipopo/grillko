package com.filip.grillko.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.filip.grillko.R
import com.filip.grillko.auth.login.LoginFragment
import com.filip.grillko.isUserSaved
import com.filip.grillko.startActivityMain

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (isUserSaved()) {
            startActivityMain()
        } else {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.authContainer, LoginFragment())
                .commit()
        }
    }
}