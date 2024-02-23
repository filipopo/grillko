package com.filip.grillko.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.filip.grillko.R
import com.filip.grillko.api.Api
import com.filip.grillko.api.RetrofitClient
import com.filip.grillko.auth.UserData
import com.filip.grillko.auth.registration.RegistrationFragment
import com.filip.grillko.databinding.FragmentLoginBinding
import com.filip.grillko.internetAvailable
import com.filip.grillko.saveUser
import com.filip.grillko.startActivityMain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            btnLogin.setOnClickListener {
                onLoginClicked()
            }

            btnSignUp.setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.authContainer, RegistrationFragment())
                    .commit()
            }
        }
    }

    private fun onLoginClicked() {
        binding?.apply {
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()

            val request = LoginRequest(email, password)
            when(request.isValid()) {
                1 -> showToast("Morate uneti email adresu")
                2 -> showToast("Morate uneti validnu email adresu")
                3 -> showToast("Morate uneti password")
                4 -> showToast("Morate uneti password duzi od 6 karaktera")
                else -> loginUser(request)
            }

            //Log.d("ATVSS12345", "onLoginClicked: email: ${email}, password: ${password}")
        }
    }

    private fun loginUser(request: LoginRequest) {
        if (!requireActivity().internetAvailable()) {
            showToast("Proverite svoju internet konekciju")
            return
        }

        Log.d("ATVSS123","Internet available")
        RetrofitClient.getClient().create(Api::class.java).login(request).enqueue(object: Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                Log.d("ATVSS1345", "response: $response")
                if (response.body() == null)
                    return
                else if (!response.isSuccessful)
                    return

                response.body()?.data?.let { user ->
                    requireActivity().apply {
                        saveUser(user)
                        startActivityMain()
                    }
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                t.localizedMessage?.let { showToast(it) }
            }

        })
    }

    fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}