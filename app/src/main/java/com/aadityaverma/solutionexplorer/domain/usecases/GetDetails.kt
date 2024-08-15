package com.aadityaverma.solutionexplorer.domain.usecases

import androidx.paging.PagingData
import com.aadityaverma.solutionexplorer.data.datasource.Detail
import com.aadityaverma.solutionexplorer.domain.repository.DetailRespository
import kotlinx.coroutines.flow.Flow

class GetDetails(
    private val repository: DetailRespository
) {
    operator fun invoke(): Flow<PagingData<Detail>>{
        return repository.getDetails()
    }
}