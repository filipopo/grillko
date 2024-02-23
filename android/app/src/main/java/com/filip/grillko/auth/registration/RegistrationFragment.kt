package com.filip.grillko.auth.registration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.filip.grillko.R
import com.filip.grillko.api.Api
import com.filip.grillko.api.RetrofitClient
import com.filip.grillko.auth.UserData
import com.filip.grillko.auth.login.LoginFragment
import com.filip.grillko.databinding.FragmentRegistrationBinding
import com.filip.grillko.internetAvailable
import com.filip.grillko.saveUser
import com.filip.grillko.startActivityMain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationFragment : Fragment() {
    private var binding: FragmentRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply{
            btnSignUp.setOnClickListener {
                onRegisteringClicked()
            }
            btnLogin?.setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.authContainer, LoginFragment())
                    .commit()
            }
        }
    }
    private fun onRegisteringClicked(){
        binding?.apply {
            //val fullName = edFullName.text.toString()
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()
            val confirmPassword = edConfirmPassword.text.toString()
            val request = RegistrationRequest(email,password,confirmPassword)
            when(request.isValid()){
                1->{
                    showToast("Morate uneti email adresu")
                }
                2->{
                    showToast("Morate uneti validan email adresu")
                }
                3->{
                    showToast("Morate uneti password")
                }
                4->{
                    showToast("Morate uneti password duzi od 6 karaktera")
                }
                5->{
                    showToast("Morate uneti Ime")
                }
                6->{
                    showToast("Morate uneti Confirm password")
                }
                7->{
                    showToast("Confirm password mora biti duzi od 6 karaktera")
                }
                8->{
                    showToast("Password != Confirm Password")
                }
                else->{
                    showToast("OK")
                    registerUser(request)
                }
            }
        }
    }
    private fun registerUser(request:RegistrationRequest){
        Log.d("ATVSS123","RegisterUser")
        if (requireActivity().internetAvailable()){
            Log.d("ATVSS123","Internet available")
            RetrofitClient.getClient().create(Api::class.java)
                .register(request).enqueue(object: Callback<UserData> {
                    override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                        if (response.body() == null){
                            Log.d("ATVSS123","data is null")
                            Log.d("ATVSS123","${response.code()}")
                            Log.d("ATVSS123", "${response.message()}")
                            return
                        }
                        if (!response.isSuccessful){
                            Log.d("ATVSS123", "onResponse: NOT Successful")
                            return
                        }
                        response.body()?.data?.let{user->
                            requireActivity().apply {
                                saveUser(user)
                                startActivityMain()
                            }
                        }
                        //Log.d("ATVSS123", "Name: ${user?.name}\nEmail: ${user?.email}")
                    }

                    override fun onFailure(call: Call<UserData>, t: Throwable) {
                        showToast(t.localizedMessage)
                    }

                })
        }else{
            showToast("Proverite svoju internet konekciju.")
        }
    }

    fun showToast(msg:String){
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}