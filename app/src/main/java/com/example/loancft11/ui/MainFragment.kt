package com.example.loancft11.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loancft11.R
import com.example.loancft11.adapters.LoanAdapter
import com.example.loancft11.models.LoanConditionModel
import com.example.loancft11.models.LoanModel
import com.example.loancft11.api.ApiClientGetData
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var BEARERTOKEN= "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTYW5jaGVzIiwiZXhwIjoxNjA5NjE1MDg2fQ.IzlpEVi7ZgZt3vL0u8XVgM9byl56oPeW8gXreh4cs63Qk7NycE0ZGezOAEGEMBcFN_5a4RMAtp96S_WuLydbOw"

class MainFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAllLoans()
    }

    fun getAllLoans(){
        val loginResponseCall: Call<List<LoanModel>> = ApiClientGetData.userService.request()
        loginResponseCall.enqueue(object : Callback<List<LoanModel>> {
            override fun onResponse(call: Call<List<LoanModel>>, response: Response<List<LoanModel>>) {
                if (response.isSuccessful) {

                    response.body()?.let { setAllLoans(it) }
                } else {
                    Log.d("TAG", "OSHIBKA")
                }
            }
            override fun onFailure(call: Call<List<LoanModel>>, t: Throwable) {
                Log.d("TAG", "ERROR")
            }

        })
    }

    private fun setAllLoans(list: List<LoanModel>) {
        val myAdapter = LoanAdapter(list, object : LoanAdapter.Callback {
                override fun onItemClicked(item: LoanModel) {
                    val builder=AlertDialog.Builder(activity).apply {
                        setTitle("Подробности займа")
                        setMessage("Имя: ${item.firstName}\n" +
                                "Фамилия: ${item.lastName}\n"+
                                "Id займа: ${item.id}\n"+
                                "Сумма займа: ${item.amount}\n"+
                                "Процент займа: ${item.percent}\n"+
                                "Процент займа: ${item.percent}\n"+
                                "Период: ${item.period}\n"+
                                "Номер телефона: ${item.phoneNumber}\n"+
                                "Состояния займа: ${item.state}\n"+
                                "Дата: ${item.date}" )
                    }
                    val dialog=builder.create()
                    dialog.show()
                }
            })
        loansRv.adapter = myAdapter
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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("TOK", BEARERTOKEN)
        createLoanBtn.setOnClickListener {
            openFragment(CreateLoanFragment.newInstance())
        }
    }

    companion object {

        fun newInstance() = MainFragment()

    }
}