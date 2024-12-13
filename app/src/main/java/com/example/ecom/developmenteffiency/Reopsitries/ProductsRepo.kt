package com.example.ecom.developmenteffiency.Reopsitries

import com.example.ecom.developmenteffiency.API.ModelClasses.ProductsList
import com.example.ecom.developmenteffiency.DataSources.ProductsDataSrc

class ProductsRepo() {

    val productDataSrc = ProductsDataSrc()
    suspend fun getAllProducts(filter:String): List<ProductsList>? {

        return productDataSrc.getAllProducts(filter)

    }
    suspend fun getAllCategories(): List<String>? {

        return productDataSrc.getAllCategories()

    }
}