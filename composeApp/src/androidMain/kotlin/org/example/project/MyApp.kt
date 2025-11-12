package org.example.project

import android.app.Application
import app_firma_digital.org.example.project.core.di.initKoin
import org.example.project.core.utils.appContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        appContext = applicationContext
    }
}