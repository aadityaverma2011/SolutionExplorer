package com.aadityaverma.solutionexplorer

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aadityaverma.solutionexplorer.presentation.navgraph.Navigation
import com.aadityaverma.solutionexplorer.ui.theme.SolutionExplorerTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        requestLocationPermission()

        setContent {
            SolutionExplorerTheme {
                Navigation()
            }
        }
    }

    private fun requestLocationPermission() {
        when {
            checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                getLastKnownLocation()
            }
            else -> {
                requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLastKnownLocation()
        } else {
            Log.d("Distance", "Location permission denied")
        }
    }

    private fun getLastKnownLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    // Replace with the given latitude and longitude
                    val givenLatitude = 22.291013065885316
                    val givenLongitude = 73.13377097016534

                    // Calculate distance
                    val distance = calculateDistance(it.latitude, it.longitude, givenLatitude, givenLongitude)

                    // Print the result in kilometers to Logcat
                    Log.d("Distance", "Distance in km: $distance")
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun calculateDistance(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double
    ): Float {
        val startPoint = Location("locationA").apply {
            latitude = startLatitude
            longitude = startLongitude
        }

        val endPoint = Location("locationB").apply {
            latitude = endLatitude
            longitude = endLongitude
        }

        return startPoint.distanceTo(endPoint) / 1000 // Convert meters to kilometers
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SolutionExplorerTheme {
        Greeting("Android")
    }
}