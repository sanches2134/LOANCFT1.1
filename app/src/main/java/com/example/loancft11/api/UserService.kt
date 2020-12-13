package com.example.loancft11.api

import com.example.loancft11.models.*
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("login")
    fun auth(
        @Body userModel: UserModel
    ): retrofit2.Call<ResponseBody>

    @GET("loans/conditions")
    fun conditions(
    ): retrofit2.Call<LoanConditionModel>

    @GET("loans/all")
    fun request(
    ): retrofit2.Call<List<LoanModel>>

    @POST("registration")
    fun registration(
        @Body requestBody: UserModel
    ): retrofit2.Call<RegistrationModel>

    @POST("loans")
    fun createloan(
        @Body loanModel: LoanRequestModel
    ): retrofit2.Call<LoanModel>
}