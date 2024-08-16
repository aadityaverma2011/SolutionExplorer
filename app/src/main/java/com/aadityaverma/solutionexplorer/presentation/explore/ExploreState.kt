package com.aadityaverma.solutionexplorer.presentation.explore

import androidx.paging.PagingData
import com.aadityaverma.solutionexplorer.data.datasource.Product

import kotlinx.coroutines.flow.Flow

data class ExploreState(
  val details: Flow<PagingData<Product>>?=null,
  val isLoading: Boolean = false
)