package com.aadityaverma.solutionexplorer.data

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aadityaverma.solutionexplorer.data.api.ApiService
import com.aadityaverma.solutionexplorer.data.datasource.Detail
import retrofit2.awaitResponse
class DetailPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Detail>() {

    override fun getRefreshKey(state: PagingState<Int, Detail>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Detail> {
        return try {
            val response = apiService.getDetails().awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null && data.isNotEmpty()) {
                    Log.d("DetailPagingSource", "Fetched data: ${data.firstOrNull()?.name ?: "No Name"}")
                } else {
                    Log.d("DetailPagingSource", "No data fetched")
                }
                LoadResult.Page(
                    data = data.orEmpty(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                Log.e("DetailPagingSource", "Error response code: ${response.code()}")
                LoadResult.Error(Throwable("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("DetailPagingSource", "Exception during data fetch", e)
            LoadResult.Error(e)
        }
    }
}