package com.example.growbox



import android.app.Application
import android.util.Log
import android.util.Log.e
import com.example.growbox.di.AppContainer
import com.example.growbox.di.DefaultAppContainer
import com.google.firebase.FirebaseApp



class GrowBoxApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        container = DefaultAppContainer(this)

    }

}
