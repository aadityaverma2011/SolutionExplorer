package com.aadityaverma.solutionexplorer.presentation.navgraph

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aadityaverma.solutionexplorer.R
import com.aadityaverma.solutionexplorer.presentation.explore.ExploreScreen
import com.aadityaverma.solutionexplorer.presentation.explore.ExploreViewModel
import com.aadityaverma.solutionexplorer.presentation.navgraph.components.BottomNavigation
import com.aadityaverma.solutionexplorer.presentation.navgraph.components.BottomNavigationItem
import com.aadityaverma.solutionexplorer.presentation.refine.RefineScreen
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    var selectedItem = remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                items = listOf(
                    BottomNavigationItem(R.drawable.baseline_explore_24, "Explore"),
                    BottomNavigationItem(R.drawable.baseline_filter_list_24, "Refine"), // Add other items as needed
                ),
                selectedItem = selectedItem,
                onItemClick = { index ->
                    selectedItem.value = index
                    // Handle navigation
                    when (index) {
                        0 -> navController.navigate(Screen.ExploreScreen.route)
                        1 -> navController.navigate(Screen.RefineScreen.route) // Add other routes as needed
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.ExploreScreen.route,
            Modifier.fillMaxSize()
        ) {
            composable(Screen.ExploreScreen.route) {

                val viewModel: ExploreViewModel = hiltViewModel()
                val state = viewModel.state.value
                ExploreScreen(navController = navController,viewModel, selectedDistance = Int.MAX_VALUE, selectedChips = listOf("Gym", "Friendship", "Horror", "Gaming", "Sports", "Business", "Yoga", "Singing").toString())
            }
            composable(Screen.RefineScreen.route) {
                RefineScreen(navController = navController)
            }
            composable(
                "explore_screen/{selectedDistance}/{selectedChips}",
                arguments = listOf(
                    navArgument("selectedDistance") { type = NavType.IntType },
                    navArgument("selectedChips") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val selectedDistance = backStackEntry.arguments?.getInt("selectedDistance") ?: 0
                val selectedChips = backStackEntry.arguments?.getString("selectedChips") ?: ""
                val viewModel: ExploreViewModel = hiltViewModel()
                val state = viewModel.state.value
                ExploreScreen(navController = navController,viewModel, selectedDistance = selectedDistance, selectedChips = selectedChips)
            }
        }
    }
}