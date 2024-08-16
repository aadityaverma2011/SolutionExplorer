package com.aadityaverma.solutionexplorer.presentation.navgraph

sealed class Screen(val route: String) {
    object ExploreScreen: Screen("explore_screen")
    object RefineScreen: Screen("refine_screen")
    object FilteredExploreScreen : Screen("explore_screen/{selectedDistance}/{selectedChips}")
    object ViewScreen : Screen("view_screen")
}