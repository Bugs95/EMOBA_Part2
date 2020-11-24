package fhnw.emoba.modules.module07.gps.data

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import com.google.android.gms.location.LocationServices
import androidx.core.app.ActivityCompat


/*
 Eintrag im AndroidManifest.xml

     <!-- Zugriff auf den GPS Sensor -->
     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>


 Dependency im build.gradle (emoba-II.app)

    implementation 'com.google.android.gms:play-services-location:17.1.0'

*/

class GPSConnector(val activity: Activity) {
    private val brugg = GeoPosition(latitude = 47.480995, longitude = 8.211862, altitude = 352.0)

    private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                                      Manifest.permission.ACCESS_COARSE_LOCATION)

    private val locationProvider by lazy { LocationServices.getFusedLocationProviderClient(activity) }

    init {
        ActivityCompat.requestPermissions(activity, PERMISSIONS, 10)
    }

    @SuppressLint("MissingPermission")
    fun getLocation(onSuccess:          (geoPosition: GeoPosition) -> Unit,
                    onFailure:          (exception: Exception) -> Unit,
                    onPermissionDenied: () -> Unit) {
        if (PERMISSIONS.oneOfGranted()) {
            locationProvider.lastLocation
                .addOnSuccessListener(activity) {
                    // der Emulator liefert null zurueck. In diesem Fall nehmen wir einfach 'brugg'
                    onSuccess.invoke(if (it == null) brugg else GeoPosition(it.longitude, it.latitude, it.altitude)) }

                .addOnFailureListener(activity) {
                    onFailure.invoke(it)
                }
        }
        else {
            onPermissionDenied.invoke()
        }
    }

    private fun Array<String>.oneOfGranted() : Boolean = any { it.granted() }

    private fun String.granted(): Boolean = ActivityCompat.checkSelfPermission(activity, this) == PackageManager.PERMISSION_GRANTED
}

