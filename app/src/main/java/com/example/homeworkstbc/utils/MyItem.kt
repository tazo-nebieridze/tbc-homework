package com.example.homeworkstbc.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MyItem(
    private val position: LatLng,
    private val title: String,
    private val snippet: String,
    private val zIndex: Float = 0.0f
) : ClusterItem {
    override fun getPosition() = position
    override fun getTitle() = title
    override fun getSnippet() = snippet
    override fun getZIndex(): Float = zIndex

}
