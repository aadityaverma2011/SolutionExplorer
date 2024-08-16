package com.aadityaverma.solutionexplorer.presentation.explore

import android.content.Context
import android.location.Location
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.filter
import androidx.paging.map
import coil.compose.AsyncImage
import com.aadityaverma.solutionexplorer.R
import com.aadityaverma.solutionexplorer.domain.LocationManager
import com.aadityaverma.solutionexplorer.presentation.common.DetailList
import com.aadityaverma.solutionexplorer.presentation.components.DistanceUtils
import com.aadityaverma.solutionexplorer.ui.theme.SolutionExplorerTheme
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel(), // Use the ViewModel
    selectedDistance: Int
) {
    SolutionExplorerTheme {

        val state by viewModel.state // Collect state from ViewModel
        val searchQuery = remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()

        val filteredDetailsFlow = remember(searchQuery.value, state.details) {
            Log.d("ExploreScreen", "State details: ${state.details}")
            state.details?.map { pagingData ->
                Log.d("ExploreScreen", "PagingData: $pagingData")
                pagingData.map { detail ->
                    Log.d("ExploreScreen", "Detail: $detail")
                    detail
                }.filter { detail ->
                    Log.d("ExploreScreen", "Filtering detail: $detail")
                    val productNameMatches = searchQuery.value.isEmpty() ||
                            detail.productName.contains(searchQuery.value, ignoreCase = true)
                    val aisleMatches = detail.aisle?.contains(searchQuery.value, ignoreCase = true) == true
                    val availableAtMatches = detail.availableAt.contains(searchQuery.value, ignoreCase = true)
                    productNameMatches || aisleMatches || availableAtMatches
                }
            } ?: emptyFlow()
        }

        Column(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .statusBarsPadding()
                .fillMaxSize() // Ensure the column fills the available space
        ) {

            if (state.isLoading) {
                Log.d("ExploreScreen", "Loading state active")
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(CenterHorizontally)
                    )
                }
            } else {
                Log.d("ExploreScreen", "Loading complete")
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Align logo and search bar vertically centered
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    AsyncImage(
                        model = R.drawable.walmartlogo,
                        contentDescription = "logo",
                        modifier = Modifier
                            .height(45.dp)
                            .width(45.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp)) // Optional: Add some space between the logo and search bar
                    SearchBar(
                        query = searchQuery.value,
                        onQueryChange = { searchQuery.value = it },
                        onSearch = { /* Trigger search if needed */ },
                        placeholder = {
                            Text(
                                "Search",
                                color = Color.Black,
                                style = TextStyle(fontWeight = FontWeight.Bold)
                            )
                        },
                        active = false,
                        colors = SearchBarDefaults.colors(Color(0xFFffc220)),
                        onActiveChange = { /* Handle active state change */ }
                    ){}
                }

                Spacer(modifier = Modifier.height(24.dp))

                filteredDetailsFlow?.let { flow ->
                    val details = flow.collectAsLazyPagingItems()

                    DetailList(
                        details = details,
                        onClick = { /* Handle item click */ }
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun ExploreScreenPreview() {
//    ExploreScreen(navController = NavController, state = ExploreState() ) {
//
//    }
//}
