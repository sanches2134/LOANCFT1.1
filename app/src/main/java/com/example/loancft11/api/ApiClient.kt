package com.example.loancft11.api

import com.example.loancft11.api.BASEURL
import com.example.loancft11.ui.BEARERTOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

    private val BASEURL="http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com/"
object ApiClient {
    private val retrofit: Retrofit
        private get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .client(okHttpClient)
                .build()
        }
    val userService: UserService
        get() = retrofit.create(UserService::class.java)
}
object ApiClientGetData {
    private val retrofit: Retrofit
         get() {
            val requestInterceproe= Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .addHeader("Authorization", BEARERTOKEN )
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceproe).build()
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .client(okHttpClient)
                .build()
        }
    val userService: UserService
        get() = retrofit.create(UserService::class.java)
}