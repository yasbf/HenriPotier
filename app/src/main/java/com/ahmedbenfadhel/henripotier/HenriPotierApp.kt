package com.ahmedbenfadhel.henripotier

import android.app.Application
import com.facebook.stetho.Stetho

class HenriPotierApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}