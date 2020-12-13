package com.example.loancft11.models

data class LoanRequestModel(
    val amount:Number,
    val firstName:String,
    val lastName:String,
    val percent:Number,
    val period:Int,
    val phoneNumber:String,
)