package com.aadityaverma.solutionexplorer.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aadityaverma.solutionexplorer.data.datasource.Product

class SharedViewModel : ViewModel() {
    var selectedProduct by mutableStateOf<Product?>(null)
}