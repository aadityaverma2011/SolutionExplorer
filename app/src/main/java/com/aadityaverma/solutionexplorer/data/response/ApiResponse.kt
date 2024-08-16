package com.aadityaverma.solutionexplorer.data.response


import com.aadityaverma.solutionexplorer.data.datasource.Product

data class ApiResponse(
    val data: List<Product>?,
    val error: String?
)