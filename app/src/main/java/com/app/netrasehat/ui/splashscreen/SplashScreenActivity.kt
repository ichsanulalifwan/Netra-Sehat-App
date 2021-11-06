package com.app.netrasehat.ui.splashscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.netrasehat.ui.settingvoice.SettingVoiceActivity
import com.app.netrasehat.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

//    private var fusedLocationProvider: FusedLocationProviderClient? = null
//    private val locationRequest: LocationRequest = LocationRequest.create().apply {
//        interval = 30
//        fastestInterval = 10
//        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
//        maxWaitTime = 60
//    }
//
//    private var locationCallback: LocationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
////            val locationList = locationResult.locations
////            if (locationList.isNotEmpty()) {
////                //The last location in the list is the newest
////                val location = locationList.last()
////                Toast.makeText(
////                    this@MainActivity,
////                    "Got Location: $location",
////
////                    Toast.LENGTH_LONG
////                )
////                    .show()
////            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            checkRecordAudio()
        }, TIME)
    }

    private fun startActivity() {
        val main = Intent(this@SplashScreenActivity, SettingVoiceActivity::class.java)
        startActivity(main)
        finish()
    }

    private fun checkRecordAudio() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestRecordAudioPermission()
        } else {
            checkLocationPermission()
//            startActivity()
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            ) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                AlertDialog.Builder(this)
//                    .setTitle("Diperlukan Izin Lokasi")
//                    .setMessage("Mohon untuk memberikan izin lokasi untuk menggunakan fungsionalitas lokasi")
//                    .setPositiveButton(
//                        "OK"
//                    ) { _, _ ->
//                        //Prompt the user once explanation has been shown
//                        requestLocationPermission()
//                    }
//                    .create()
//                    .show()
//            } else {
//                // No explanation needed, we can request the permission.
//                requestLocationPermission()
//            }
        } else {
            startActivity()
        }
    }

//    private fun checkBackgroundLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestBackgroundLocationPermission()
//        } else {
//            startActivity()
//        }
//    }

    private fun requestRecordAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                MY_PERMISSIONS_REQUEST_RECORD_AUDIO
            )
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }

//    private fun requestBackgroundLocationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                ),
//                MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION
//            )
//        } else {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                MY_PERMISSIONS_REQUEST_LOCATION
//            )
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_RECORD_AUDIO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        checkLocationPermission()
                    }
                    Toast.makeText(this, "Izin Rekam Audio Diberikan", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", this.packageName, null),
                        ),
                    )

                    Toast.makeText(
                        this,
                        "Mohon Memberikan Izin Rekam Audio Untuk Menjalankan Aplikasi",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        startActivity()

                        // Now check background location
//                        checkBackgroundLocation()

//                        fusedLocationProvider?.requestLocationUpdates(
//                            locationRequest,
//                            locationCallback,
//                            Looper.getMainLooper()
//                        )
                    }
                } else {

                    startActivity()

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Izin Lokasi Ditolak", Toast.LENGTH_LONG).show()

                    // Check if we are in a state where the user has denied the permission and
                    // selected Don't ask again
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
//                            this,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        )
//                    ) {
//                        startActivity(
//                            Intent(
//                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                                Uri.fromParts("package", this.packageName, null),
//                            ),
//                        )
//                    }
                }
                return
            }
//            MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        ) == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        startActivity()
//                        fusedLocationProvider?.requestLocationUpdates(
//                            locationRequest,
//                            locationCallback,
//                            Looper.getMainLooper()
//                        )
//
//                        Toast.makeText(
//                            this,
//                            "Granted Background Location Permission",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                } else {
//                    startActivity()
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(this, "Background Location permission denied", Toast.LENGTH_LONG)
//                        .show()
//                }
//                return
//            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            fusedLocationProvider?.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                Looper.getMainLooper()
//            )
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            fusedLocationProvider?.removeLocationUpdates(locationCallback)
//        }
//    }

    companion object {
        private const val TIME: Long = 2000
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99

        //        private const val MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION = 66
        private const val MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 77
    }
}