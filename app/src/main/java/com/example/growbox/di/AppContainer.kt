package com.example.growbox.di

import android.content.Context
import com.example.growbox.data.FirebaseDataSource
import com.example.growbox.data.FirebaseDataSourceImpl
import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.data.GrowBoxRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer {
    val growBoxRepository: GrowBoxRepository
}


class DefaultAppContainer(private val context: Context) : AppContainer {


    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firebaseFirestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }


    private val firebaseDataSource: FirebaseDataSource by lazy {
        FirebaseDataSourceImpl(firebaseAuth, firebaseFirestore)
    }


    override val growBoxRepository: GrowBoxRepository by lazy {
        GrowBoxRepositoryImpl(firebaseDataSource)
    }
}