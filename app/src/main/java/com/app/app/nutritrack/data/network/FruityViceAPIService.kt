package com.fit2081.aditya_33520070.nutritrack.data.network

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.converter.gson.GsonConverterFactory


interface FruityViceAPIService {
    @GET("api/fruit/{fruitName}")
    suspend fun getDetails(@Path("fruitName") fruitName: String): FruityViceResponseModel

    companion object {

        var BASE_URL = "https://www.fruityvice.com/"

        fun create(): FruityViceAPIService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(FruityViceAPIService::class.java)
        }
    }
}