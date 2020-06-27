package com.imnstudios.riafytest.depricatedwithp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.LocationResult
import com.imnstudios.riafytest.utils.toast

class LocationService : BroadcastReceiver() {
    companion object {
        const val ACTION_PROCESS_UPDATE = "com.imnstudios.riafytest.UPDATE_LOCATION"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (action.equals(ACTION_PROCESS_UPDATE)) {
                val result = LocationResult.extractResult(intent)

                if (result != null) {
                    val location = result.lastLocation

                    val locationString =
                        "lattide: " + location.latitude.toString() + "\nlongitude: " + location.longitude.toString()

                    try {
                        MainActivity.getMainInstance()
                            .updateUI(locationString)
                    } catch (e: Exception) {
                        context?.toast(locationString)
                    }
                }
            }
        }
    }


}
