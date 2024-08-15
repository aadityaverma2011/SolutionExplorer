package com.aadityaverma.solutionexplorer.data.response

import com.aadityaverma.solutionexplorer.data.datasource.Detail

data class ApiResponse(
    val data: List<Detail>?,
    val error: String?
)