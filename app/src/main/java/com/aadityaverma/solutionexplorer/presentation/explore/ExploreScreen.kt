package com.aadityaverma.solutionexplorer.presentation.explore

import android.content.Context
import android.location.Location
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.filter
import androidx.paging.map
import com.aadityaverma.solutionexplorer.domain.LocationManager
import com.aadityaverma.solutionexplorer.presentation.common.DetailList
import com.aadityaverma.solutionexplorer.presentation.components.DistanceUtils
import com.aadityaverma.solutionexplorer.ui.theme.SolutionExplorerTheme
import kotlinx.coroutines.flow.map
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel(), // Use the ViewModel
    selectedDistance: Int,
    selectedChips: String
) {
    SolutionExplorerTheme {

        val state by viewModel.state // Collect state from ViewModel
        val searchQuery = remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val currentLocation = remember { mutableStateOf<Location?>(null) }
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            if (LocationManager.isLocationPermissionGranted(context)) {
                LocationManager.getLastLocation { location ->
                    location?.let {
                        currentLocation.value = it
                    }
                }
            }
        }

        val filteredDetailsFlow = remember(searchQuery.value, state.details, currentLocation.value) {
            state.details?.map { pagingData ->
                val currentLocation = currentLocation.value
                pagingData.map { detail ->
                    val distance = currentLocation?.let {
                        DistanceUtils.calculateDistance(
                            it.latitude,
                            it.longitude,
                            detail.distance[0].toDouble(),
                            detail.distance[1].toDouble()
                        )
                    } ?: 0f
                    detail.copy(calculateddistance = distance)
                }.filter { detail ->
                    (searchQuery.value.isEmpty() ||
                            detail.name.contains(searchQuery.value, ignoreCase = true) ||
                            detail.profession.contains(searchQuery.value, ignoreCase = true) ||
                            detail.location.contains(searchQuery.value, ignoreCase = true) ||
                            detail.calculateddistance.toString().contains(searchQuery.value, ignoreCase = true) ||
                            detail.safeHobbies.any { hobby ->
                                hobby.name.contains(searchQuery.value, ignoreCase = true)
                            }) &&
                            detail.calculateddistance <= selectedDistance &&
                            selectedChips.split(",").map { it.trim() }.any { chip -> detail.safeHobbies.any { it.name == chip } }
                }
            }
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
                SearchBar(
                    query = searchQuery.value,
                    onQueryChange = { searchQuery.value = it },
                    onSearch = { /* Trigger search if needed */ },
                    modifier = Modifier.padding(bottom = 24.dp),
                    placeholder = { Text("Search") },
                    active = false,
                    onActiveChange = { /* Handle active state change */ }
                ) {}

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
