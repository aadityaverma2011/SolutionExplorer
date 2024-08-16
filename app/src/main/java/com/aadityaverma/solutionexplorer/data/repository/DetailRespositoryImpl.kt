package com.aadityaverma.solutionexplorer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aadityaverma.solutionexplorer.data.DetailPagingSource
import com.aadityaverma.solutionexplorer.data.api.ApiService
import com.aadityaverma.solutionexplorer.data.datasource.Product

import com.aadityaverma.solutionexplorer.domain.repository.DetailRespository
import kotlinx.coroutines.flow.Flow

class DetailRespositoryImpl(
    private val apiService: ApiService
): DetailRespository {
    override fun getDetails(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                DetailPagingSource(apiService)
            }
        ).flow
    }
}