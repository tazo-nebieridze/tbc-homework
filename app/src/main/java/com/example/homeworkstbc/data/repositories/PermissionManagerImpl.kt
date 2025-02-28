package com.example.homeworkstbc.data.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.homeworkstbc.data.LocationPermissionStatus
import com.example.homeworkstbc.utils.PermissionManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext

class PermissionManagerImpl @AssistedInject constructor(
    @ApplicationContext private val context: Context,
    @Assisted private val activityResultCaller: ActivityResultCaller
) : PermissionManager {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var onPermissionResult: ((LocationPermissionStatus) -> Unit)? = null

    init {
        permissionLauncher = activityResultCaller.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            onPermissionResult?.invoke(
                if (isGranted) {
                    if (isSystemLocationEnabled()) {
                        LocationPermissionStatus.PermissionGrantedAndLocationEnabled
                    } else {
                        LocationPermissionStatus.PermissionGrantedButLocationDisabled
                    }
                } else {
                    LocationPermissionStatus.PermissionDenied
                }
            )
        }
    }

    private fun isSystemLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun checkAndRequestLocationPermission(onResult: (LocationPermissionStatus) -> Unit) {
        onPermissionResult = onResult
        val hasFineLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocation || hasCoarseLocation) {
            if (isSystemLocationEnabled()) {
                onResult(LocationPermissionStatus.PermissionGrantedAndLocationEnabled)
            } else {
                onResult(LocationPermissionStatus.PermissionGrantedButLocationDisabled)
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(activityResultCaller: ActivityResultCaller): PermissionManagerImpl
    }
}
