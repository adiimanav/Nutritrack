package com.fit2081.aditya_33520070.nutritrack.data

import com.fit2081.aditya_33520070.nutritrack.data.network.FruityViceAPIService
import com.fit2081.aditya_33520070.nutritrack.data.network.FruityViceResponseModel

class FruitsRepository() {
    private val apiService: FruityViceAPIService = FruityViceAPIService.create()

    suspend fun getDetails(fruitName: String): FruityViceResponseModel? {
        val response = apiService.getDetails(fruitName)
        return response
    }

}