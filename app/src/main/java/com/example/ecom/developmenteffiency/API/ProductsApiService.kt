package com.example.ecom.developmenteffiency.API

import com.example.ecom.developmenteffiency.API.ModelClasses.ProductsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsApiService {
    @GET("products")
    fun getAllProducts(): Call<List<ProductsList>>
    @GET("products/category/{category}")
    fun getProductsByCategory(@Path("category") category: String): Call<List<ProductsList>>

    @GET("products/categories")
    fun getAllCategories() : Call<List<String>>
}