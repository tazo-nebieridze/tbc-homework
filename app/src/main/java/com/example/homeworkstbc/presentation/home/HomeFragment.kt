package com.example.homeworkstbc.presentation.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.homeworkstbc.R
import com.example.homeworkstbc.RunningService
import com.example.homeworkstbc.databinding.FragmentHomeBinding
import com.example.homeworkstbc.domain.utils.Resource
import com.example.homeworkstbc.presentation.base.BaseFragment
import com.example.homeworkstbc.presentation.utils.convertUriToBase64
import com.example.homeworkstbc.presentation.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

//    val homeViewModel : HomeViewModel by viewModels()
    private var newProfilePhotoBase64: String? = null

    override fun start() {
        binding.profileImage.setOnClickListener { showImageOptions() }

        binding.crash.setOnClickListener {
            throw RuntimeException("Test Crash")
        }
        binding.startForegroundService.setOnClickListener{
            Intent(requireContext(),RunningService::class.java).also {
                it.action = RunningService.Actions.START.toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireContext().startForegroundService(it)
                }else {
                    requireContext().startService(it)

                }
            }
        }
        binding.StopForegroundService.setOnClickListener{
            Intent(requireContext(),RunningService::class.java).also {
                it.action = RunningService.Actions.STOP.toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireContext().startForegroundService(it)
                }else {
                    requireContext().startService(it)

                }
            }
        }
    }
    private fun showImageOptions() {
        val options = arrayOf(
            getString(R.string.pick_from_gallery),
            getString(R.string.take_photo),
            getString(R.string.cancel)
        )
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.select_image))
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> pickFromGallery()
                    1 -> takePhoto()
                    2 -> dialog.dismiss()
                }
            }.show()
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            newProfilePhotoBase64 = requireContext().convertUriToBase64(it)
            binding.profileImage.setImageURI(it)
        }
    }

    private fun pickFromGallery() {
        galleryLauncher.launch("image/*")
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as? android.graphics.Bitmap
            bitmap?.let { updateProfileImage(it) }
        }
    }

    private fun takePhoto() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                launchCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.camera_permission_required))
            .setMessage(getString(R.string.camera_permission_message))
            .setPositiveButton(getString(R.string.go_to_settings)) { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera()
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showPermissionRationaleDialog()
            } else {
                binding.root.showSnackbar(getString(R.string.camera_permission_denied))
            }
        }
    }

    private fun launchCamera() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }

    private fun updateProfileImage(bitmap: android.graphics.Bitmap) {
        val outputStream = java.io.ByteArrayOutputStream()
        bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 90, outputStream)
        newProfilePhotoBase64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
        binding.profileImage.setImageBitmap(bitmap)
    }



}