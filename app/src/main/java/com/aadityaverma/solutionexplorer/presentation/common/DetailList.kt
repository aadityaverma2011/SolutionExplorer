package com.aadityaverma.solutionexplorer.presentation.common

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

import com.aadityaverma.solutionexplorer.data.datasource.Product
import com.aadityaverma.solutionexplorer.presentation.components.DetailCard
import com.aadityaverma.solutionexplorer.ui.theme.SolutionExplorerTheme

@Composable
fun handlePagingResult(details: LazyPagingItems<Product>): Boolean {
    val loadState = details.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }
    return when {
        loadState.refresh is LoadState.Loading -> {
            // ShimmerEffect()
            false
        }
        else -> {
            true
        }
    }
}
@Composable
fun DetailList(
    modifier: Modifier = Modifier,
    details: LazyPagingItems<Product>,
    onClick: (Product) -> Unit // Update this to pass the clicked product
) {
    val context = LocalContext.current
    val handlePagingResult = handlePagingResult(details = details)
    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize().padding(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(all = 3.dp)
        ) {
            items(details.itemCount) { index ->
                details[index]?.let {
                    SolutionExplorerTheme {
                        DetailCard(
                            detail = it,
                            onClick = {
                                onClick(it) // Pass the clicked product to the onClick handler
                            }
                        )
                    }
                }
            }
        }
    }
}