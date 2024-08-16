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
import com.aadityaverma.solutionexplorer.presentation.view.ViewScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    var selectedItem = remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                items = listOf(
                    BottomNavigationItem(R.drawable.baseline_home_24, "Home"),
                    BottomNavigationItem(R.drawable.baseline_filter_list_24, "Filter"), // Add other items as needed
                ),
                selectedItem = selectedItem,
                onItemClick = { index ->
                    selectedItem.value = index
                    when (index) {
                        0 -> navController.navigate(Screen.ExploreScreen.route)
                        1 -> navController.navigate(Screen.RefineScreen.route)
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
                ExploreScreen(navController = navController, viewModel = viewModel, selectedDistance = Int.MAX_VALUE)
            }
            composable(Screen.RefineScreen.route) {
                RefineScreen(navController = navController)
            }
            composable(
                "view_screen/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
                ViewScreen(navController = navController, productId = productId)
            }
        }
    }
}