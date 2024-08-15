package com.aadityaverma.solutionexplorer.presentation.explore

import androidx.paging.PagingData
import com.aadityaverma.solutionexplorer.data.datasource.Detail
import kotlinx.coroutines.flow.Flow

data class ExploreState(
  val details: Flow<PagingData<Detail>>?=null,
  val isLoading: Boolean = false
)