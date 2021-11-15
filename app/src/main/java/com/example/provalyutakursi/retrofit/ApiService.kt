package com.example.provalyutakursi.retrofit

import com.example.provalyutakursi.models.Currency
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    fun getCurrency():Call<List<Currency>>

}