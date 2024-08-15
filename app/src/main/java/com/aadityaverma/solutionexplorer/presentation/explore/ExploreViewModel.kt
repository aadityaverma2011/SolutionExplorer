package com.aadityaverma.solutionexplorer.presentation.explore

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aadityaverma.solutionexplorer.domain.usecases.Exploreusecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreusecases: Exploreusecases
) : ViewModel() {
    private var _state = mutableStateOf(ExploreState())
    val state: State<ExploreState> = _state

    init {
        onEvent(ExploreEvent.GetDetails) // Ensure the initial event is called
    }

    fun onEvent(event: ExploreEvent) {
        when (event) {
            is ExploreEvent.GetDetails -> {
                getDetails()
            }
        }
    }

    private fun getDetails() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true) // Set loading to true
            try {
                val details = exploreusecases.getDetails().cachedIn(viewModelScope)
                _state.value = state.value.copy(details = details, isLoading = false) // Set loading to false
            } catch (e: Exception) {
                Log.e("ExploreViewModel", "Error fetching details", e)
                _state.value = state.value.copy(isLoading = false) // Set loading to false in case of error
            }
        }
    }
}
