package com.filip.grillko.auth.registration

import com.google.gson.annotations.SerializedName

class RegistrationRequest (
    val email: String,
    val password: String,
    @SerializedName("password_confirmation")
    val confirmPassword: String
) {
    fun isValid():Int{
        if (email.isEmpty())
            return 1
        else if (!email.contains("@"))
            return 2
        else if (password.isEmpty())
            return 3
        else if (password.length <= 6)
            return 4
        /*else if (fullName.isEmpty())
            return 5*/
        else if (confirmPassword.isEmpty())
            return 6
        else if (confirmPassword.length <= 6)
            return 7
        else if (password != confirmPassword)
            return 8

        return -1
    }
}