package com.example.loancft11.models

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.loancft11.R
import com.example.loancft11.api.ApiClient
import com.example.loancft11.ui.MainFragment
import com.example.loancft11.ui.RegistrationFragment
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var progressDialog: ProgressDialog
    companion object {

        fun newInstance() = LoginFragment()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog= ProgressDialog(activity)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)
    }

    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    fun login(name: String, password: String) {
        progressDialog.setMessage("Входим...")
        progressDialog.show()
        val loginRequest = UserModel(name, password)

        val loginResponseCall: Call<ResponseBody> = ApiClient.userService.auth(loginRequest)
        loginResponseCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Handler().postDelayed({
                        progressDialog.dismiss()
                        openFragment(MainFragment.newInstance())
                    }, 700)
                    response.body()?.string()?.let { Log.d("TAG", it) }

                } else {
                    response.body()?.string()?.let { Log.d("TAG", it) }
                    progressDialog.setOnDismissListener { progressDialog.setMessage("Ошибка ${response.body()
                        ?.string()}") }
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("TAG", "ERROR")
            }

        })
    }
    override fun onStart() {
        super.onStart()
        loginBtn.setOnClickListener {

            val name=login_input_login.text.trim().toString()
            val password=password_input_login.text.trim().toString()
            if(name.isEmpty()){
                login_input_login.error="Введите имя"
                login_input_login.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty())
            {
                password_input_login.error="Введите пароль"
                password_input_login.requestFocus()
                return@setOnClickListener
            }
            login(name, password)
        }
        noAccount.setOnClickListener {
            openFragment(RegistrationFragment.newInstance())
        }
    }
}