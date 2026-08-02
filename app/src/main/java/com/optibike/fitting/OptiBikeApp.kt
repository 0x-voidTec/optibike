package com.optibike.fitting

import android.app.Application
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import dagger.hilt.android.HiltAndroidApp

/**
 * OptiBike Application Class
 * Main entry point for the application with Hilt dependency injection.
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@HiltAndroidApp
class OptiBikeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize PDFBox for Android
        com.tom_roush.pdfbox.android.PDFBoxResourceLoader.init(this)
    }
}
