package com.shikherverma.themingarchitecture

import android.app.Application
import com.facebook.stetho.Stetho

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
