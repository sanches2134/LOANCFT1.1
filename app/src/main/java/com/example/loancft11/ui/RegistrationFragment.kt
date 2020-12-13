package com.example.loancft11.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loancft11.R
import com.example.loancft11.models.RegistrationModel
import com.example.loancft11.api.ApiClient
import com.example.loancft11.models.UserModel
import kotlinx.android.synthetic.main.fragment_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationFragment : Fragment() {

companion object{

    fun newInstance() = RegistrationFragment()
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }
    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
    fun registration(name: String, password: String) {

        val loginRequest = UserModel(name, password)

        val loginResponseCall: Call<RegistrationModel> = ApiClient.userService.registration(loginRequest)
        loginResponseCall.enqueue(object : Callback<RegistrationModel> {
            override fun onResponse(call: Call<RegistrationModel>, response: Response<RegistrationModel>) {
                if (response.isSuccessful) {
                    Handler().postDelayed({
                        openFragment(MainFragment.newInstance())
                    }, 700)
                } else {
                    Log.d("TAG", "OSHIBKA")
                }
            }
            override fun onFailure(call: Call<RegistrationModel>, t: Throwable) {
                Log.d("TAG", "ERROR")
            }

        })
    }

    override fun onStart() {
        super.onStart()
        registratoinBtn.setOnClickListener {

            val name=login_input_registration.text.trim().toString()
            val password=password_input_registration.text.trim().toString()

            if(name.isEmpty()){
                login_input_registration.error="Введите имя"
                login_input_registration.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty())
            {
                password_input_registration.error="Введите пароль"
                password_input_registration.requestFocus()
                return@setOnClickListener
            }

            registration(name,password)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        }
    }

