package com.aadityaverma.solutionexplorer.domain.repository

import androidx.paging.PagingData

import com.aadityaverma.solutionexplorer.data.datasource.Product
import kotlinx.coroutines.flow.Flow

interface DetailRespository {
    fun getDetails(): Flow<PagingData<Product>>
}