package com.example.loancft11.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loancft11.R
import com.example.loancft11.api.ApiClient
import com.example.loancft11.api.ApiClientGetData
import com.example.loancft11.models.LoanConditionModel
import com.example.loancft11.models.LoanModel
import com.example.loancft11.models.LoanRequestModel
import kotlinx.android.synthetic.main.fragment_create_loan.*
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateLoanFragment : Fragment() {
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog= ProgressDialog(activity)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)
        setConditions()
    }
    fun setConditions() {

        val loginResponseCall: Call<LoanConditionModel> = ApiClientGetData.userService.conditions()
        loginResponseCall.enqueue(object : Callback<LoanConditionModel> {
            override fun onResponse(call: Call<LoanConditionModel>, response: Response<LoanConditionModel>) {
                if (response.isSuccessful) {

                    response.body()?.let { bindUi(it) }
                } else {
                    Log.d("TAG", "OSHIBKA")
                }
            }
            override fun onFailure(call: Call<LoanConditionModel>, t: Throwable) {
                Log.d("TAG", "ERROR")
            }

        })
    }
    private fun bindUi(it: LoanConditionModel) {
        conditonAmount.text=it.maxAmount.toString()
        conditionperiod.text=it.period.toString()
        conditonpercent.text=it.percent.toString()
    }
    fun createLoan(amount:String,firstname:String,lastname:String,percent:String,period:String ,phonenumber:String) {
        progressDialog.setMessage("Делаем займ...")
        progressDialog.show()
        val createLoanRequest = LoanRequestModel(amount.toInt(),firstname,lastname,percent.toInt(),period.toInt(),phonenumber)

        val loginResponseCall: Call<LoanModel> = ApiClient.userService.createloan(createLoanRequest)
        loginResponseCall.enqueue(object : Callback<LoanModel> {
            override fun onResponse(call: Call<LoanModel>, response: Response<LoanModel>) {
                if (response.isSuccessful) {
                    Handler().postDelayed({
                        progressDialog.setMessage("Успех!...")
                        progressDialog.dismiss()
                    }, 700)

                } else {
                    progressDialog.setOnDismissListener { progressDialog.setMessage("Ошибка ${response.body()
                    }") }
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<LoanModel>, t: Throwable) {
                Log.d("TAG", "ERROR")
            }

        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_loan, container, false)
    }
    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
    override fun onStart() {
        super.onStart()
        back.setOnClickListener {

            openFragment(MainFragment.newInstance())

        }
        createLoan.setOnClickListener {
            val amount=amount_input.text.trim().toString()
            val firstname=firstname_input.text.trim().toString()
            val lastname=lastname_input.text.trim().toString()
            val percent=percent_input.text.trim().toString()
            val period=period_input.text.trim().toString()
            val phoneNumber=phoneNumber_input.text.trim().toString()
            if(amount.isEmpty()){
                amount_input.error="Введите имя"
                amount_input.requestFocus()
                return@setOnClickListener
            }
            if(firstname.isEmpty())
            {
                firstname_input.error="Введите пароль"
                firstname_input.requestFocus()
                return@setOnClickListener
            }
            if(lastname.isEmpty())
            {
                lastname_input.error="Введите пароль"
                lastname_input.requestFocus()
                return@setOnClickListener
            }
            if(percent.isEmpty())
            {
                percent_input.error="Введите пароль"
                percent_input.requestFocus()
                return@setOnClickListener
            }
            if(period.isEmpty())
            {
                period_input.error="Введите пароль"
                period_input.requestFocus()
                return@setOnClickListener
            }
            if(phoneNumber.isEmpty())
            {
                phoneNumber_input.error="Введите пароль"
                phoneNumber_input.requestFocus()
                return@setOnClickListener
            }
            createLoan(amount,firstname,lastname,percent,period,phoneNumber)

        }


    }
    companion object {

        fun newInstance() = CreateLoanFragment()
    }
}