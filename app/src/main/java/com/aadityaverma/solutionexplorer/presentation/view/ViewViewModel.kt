package com.aadityaverma.solutionexplorer.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aadityaverma.solutionexplorer.data.api.ApiService
import com.aadityaverma.solutionexplorer.data.datasource.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewViewModel : ViewModel() {

    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://us-central1-walprototype.cloudfunctions.net/") // Replace with your actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getProductById(productId: String, callback: (Product?) -> Unit) {
        viewModelScope.launch {
            val product = withContext(Dispatchers.IO) {
                try {
                    val response = apiService.getProducts().execute()
                    if (response.isSuccessful) {
                        response.body()?.find { it.productId == productId }
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
            callback(product)
        }
    }
}