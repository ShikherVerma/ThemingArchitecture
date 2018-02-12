package com.shikherverma.themingarchitecture

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by shikher on 2/13/18.
 */

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
