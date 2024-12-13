package com.example.ecom.developmenteffiency.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    var retrofitHelper: Retrofit? = null
    fun getInstance() : Retrofit {
        if (retrofitHelper == null) {
            retrofitHelper = Retrofit.Builder().baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitHelper!!
    }
}