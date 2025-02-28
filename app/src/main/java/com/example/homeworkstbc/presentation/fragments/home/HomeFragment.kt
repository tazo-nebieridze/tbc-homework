package com.example.homeworkstbc.presentation.fragments.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.homeworkstbc.R
import com.example.homeworkstbc.data.LocationPermissionStatus
import com.example.homeworkstbc.data.Resource
import com.example.homeworkstbc.data.repositories.PermissionManagerImpl
import com.example.homeworkstbc.databinding.FragmentHomeBinding
import com.example.homeworkstbc.presentation.fragments.BaseFragment
import com.example.homeworkstbc.presentation.fragments.LocationBottomSheetDialogFragment
import com.example.homeworkstbc.presentation.presentationModules.Location
import com.example.homeworkstbc.utils.MyItem
import com.example.homeworkstbc.utils.PermissionManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var permissionManagerFactory: PermissionManagerImpl.Factory

    private lateinit var permissionManager: PermissionManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null

    private lateinit var clusterManager: ClusterManager<MyItem>

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager = permissionManagerFactory.create(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun start() {
        permissionManager.checkAndRequestLocationPermission { status ->
            handlePermissionStatus(status)
        }
    }

    private fun handlePermissionStatus(status: LocationPermissionStatus) {
        when (status) {
            LocationPermissionStatus.PermissionGrantedAndLocationEnabled -> loadMap()
            LocationPermissionStatus.PermissionGrantedButLocationDisabled -> showLocationDisabledDialog()
            LocationPermissionStatus.PermissionDenied -> {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showPermissionRationaleDialog()
                } else {
                    showPermissionDeniedMessage()
                }
            }
        }
    }

    private fun loadMap() {
        val mapFragment = (childFragmentManager.findFragmentById(R.id.map_container) as? SupportMapFragment)
            ?: SupportMapFragment.newInstance().also { newFragment ->
                childFragmentManager.beginTransaction()
                    .replace(R.id.map_container, newFragment)
                    .commit()
                newFragment.getMapAsync(mapReadyCallback)
            }
        mapFragment.getMapAsync(mapReadyCallback)
        binding.mapContainer.visibility = View.VISIBLE
        binding.permissionDeniedText.visibility = View.GONE
    }

    private val mapReadyCallback = OnMapReadyCallback { map ->
        googleMap = map

        try {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap?.isMyLocationEnabled = true
                getUserLocationAndUpdateMap()
            } else {
                Toast.makeText(requireContext(), "Location permission not granted", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Log.e("HomeFragment", "Security Exception: ${e.message}")
            showPermissionDeniedMessage()
        }
        setupZoomButtons()

        googleMap?.let { map ->
            clusterManager = ClusterManager(requireContext(), map)
            map.setOnCameraIdleListener(clusterManager)
            map.setOnMarkerClickListener(clusterManager)
            clusterManager.setOnClusterItemClickListener { item ->
                val bundle = Bundle().apply {
                    putDouble("lat", item.position.latitude)
                    putDouble("lng", item.position.longitude)
                    putString("title", item.title)
                    putString("address", item.snippet)
                }
                val bottomSheet = LocationBottomSheetDialogFragment().apply {
                    arguments = bundle
                }
                bottomSheet.show(parentFragmentManager, "LocationBottomSheetDialog")
                true
            }
        }

        fetchRemoteLocations()
    }

    private fun getUserLocationAndUpdateMap() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val targetLocation = if (location != null) {
                    LatLng(location.latitude, location.longitude)
                } else {
                    LatLng(-34.0, 151.0)
                }
                googleMap?.clear()
                val userItem = MyItem(
                    position = targetLocation,
                    title = if (location != null) "My Location" else "Default Location",
                    snippet = "",
                    zIndex = 1.0f
                )
                clusterManager.addItem(userItem)
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLocation, 15f))
                clusterManager.cluster()
            }.addOnFailureListener { exception ->
                Log.e("HomeFragment", "Error fetching location", exception)
                Toast.makeText(requireContext(), "Failed to fetch location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchRemoteLocations() {
        viewModel.fetchLocations()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.locationsState.collectLatest { state ->
                when (state) {
                    is Resource.Loading -> binding.progressLoader.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressLoader.visibility = View.GONE
                        addClusterItems(state.data)
                    }
                    is Resource.Error -> {
                        binding.progressLoader.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun addClusterItems(locations: List<Location>) {
        locations.forEach { location ->
            val item = MyItem(
                position = LatLng(location.lat, location.lng),
                title = location.title,
                snippet = location.address ?: "",
                zIndex = 0.0f
            )
            clusterManager.addItem(item)
        }
        clusterManager.cluster()
    }

    private fun setupZoomButtons() {
        binding.zoomInButton.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.zoomOutButton.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    private fun showLocationDisabledDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Location Services Disabled")
            .setMessage("Please enable location services to use this feature.")
            .setPositiveButton("Settings") { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                showPermissionDeniedMessage()
            }
            .show()
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Location Permission Needed")
            .setMessage("This app needs location permission to show your position on the map. Please grant the permission.")
            .setPositiveButton("OK") { _, _ ->
                permissionManager.checkAndRequestLocationPermission { status ->
                    handlePermissionStatus(status)
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                showPermissionDeniedMessage()
            }
            .show()
    }

    private fun showPermissionDeniedMessage() {
        binding.mapContainer.visibility = View.GONE
        binding.permissionDeniedText.visibility = View.VISIBLE
    }

    private fun isSystemLocationEnabled(): Boolean {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
            isSystemLocationEnabled()
        ) {
            if (googleMap == null) {
                loadMap()
            }
        }
    }
}
