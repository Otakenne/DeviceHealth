package com.otakenne.devicehealth.utility

import android.app.Application
import com.otakenne.devicehealthsdk.utility.sdkentry.DeviceHealthSDKEntry

class DeviceHealthApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DeviceHealthSDKEntry.initialize(this@DeviceHealthApplication)
    }
}