package com.teledrive

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TeleDriveApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
