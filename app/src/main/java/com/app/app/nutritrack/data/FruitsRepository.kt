package com.app.app.nutritrack.data

import com.app.app.nutritrack.data.network.FruityViceAPIService
import com.app.app.nutritrack.data.network.FruityViceResponseModel

class FruitsRepository() {
    private val apiService: FruityViceAPIService = FruityViceAPIService.create()

    suspend fun getDetails(fruitName: String): FruityViceResponseModel? {
        val response = apiService.getDetails(fruitName)
        return response
    }

}