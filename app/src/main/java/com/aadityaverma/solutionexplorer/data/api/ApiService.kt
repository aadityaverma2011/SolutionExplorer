package com.aadityaverma.solutionexplorer.data.api

import com.aadityaverma.solutionexplorer.data.datasource.Detail
import com.aadityaverma.solutionexplorer.data.response.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("getDetails")
    fun getDetails(): Call<List<Detail>>
}
