package com.aadityaverma.solutionexplorer

import android.app.Application
import com.aadityaverma.solutionexplorer.domain.LocationManager
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.http.Header

    @HiltAndroidApp
    class Solution: Application(){
        override fun onCreate() {
            super.onCreate()
            LocationManager.initialize(this)
        }
    }
