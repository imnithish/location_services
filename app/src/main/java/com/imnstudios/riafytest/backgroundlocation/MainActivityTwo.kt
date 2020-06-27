package com.imnstudios.riafytest.backgroundlocation

import android.Manifest
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.imnstudios.riafytest.R
import com.imnstudios.riafytest.utils.toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main_two.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class MainActivityTwo : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var mService: MyBackgroundService? = null
    private var mBound = false
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null
            mBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyBackgroundService.LocalBinder
            mService = binder.service
            mBound = true
        }

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onBackgroundLocationRetrieve(event: BackgroundLocation) {
        if (event.location != null)
            toast(Common.getLocationText(event.location))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_two)

        Dexter.withActivity(this)
            .withPermissions(
                Arrays.asList(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    Manifest.permission.FOREGROUND_SERVICE
                )
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    request_location_updates_button.setOnClickListener {
                        mService!!.requestLocationUpdates()
                    }
                    remove_location_updates_button!!.setOnClickListener {
                        mService!!.removeLocationUpdates()
                    }
                    setButtonState(Common.requestingLocationUpdates(this@MainActivityTwo))
                    bindService(
                        Intent(this@MainActivityTwo, MyBackgroundService::class.java),
                        mServiceConnection,
                        Context.BIND_AUTO_CREATE
                    )
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                }

            }).check()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals(Common.KEY_REQUEST_LOCATION_UPDAYE))
            setButtonState(
                sharedPreferences!!.getBoolean(
                    Common.KEY_REQUEST_LOCATION_UPDAYE,
                    false
                )
            )
    }

    private fun setButtonState(boolean: Boolean) {
        if (boolean) {
            remove_location_updates_button.isEnabled = true
            request_location_updates_button.isEnabled = false
        } else {
            remove_location_updates_button.isEnabled = false
            request_location_updates_button.isEnabled = true
        }
    }

    override fun onStart() {
        super.onStart()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}