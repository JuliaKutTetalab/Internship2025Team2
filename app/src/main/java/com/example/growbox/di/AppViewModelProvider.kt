package com.example.growbox.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.growbox.GrowBoxApplication
import com.example.growbox.screen.auth.auth.LoginViewModel
import com.example.growbox.screen.auth.auth.SignUpViewModel
import com.example.growbox.screen.auth.auth.SplashScreenViewModel
import com.example.growbox.screen.home.HomeScreenViewModel
import com.example.growbox.screen.home.profile.ProfileViewModel


object AppViewModelProvider {

    val Factory = viewModelFactory {


        // Factory для HomeViewModel
        initializer {
            HomeScreenViewModel(
                growBoxApplication().container.growBoxRepository,
                growBoxApplication().container.offlineRepository // Наш новий репо
            )
        }

        // Factory для ProfileViewModel
        initializer {
            ProfileViewModel(
                growBoxRepository = growBoxApplication().container.growBoxRepository,
                offlineRepository = growBoxApplication().container.offlineRepository
            )
        }

        initializer {
            SplashScreenViewModel(
                growBoxApplication().container.growBoxRepository
            )
        }

        initializer {
            LoginViewModel(growBoxApplication().container.growBoxRepository)
        }

        initializer {
            SignUpViewModel(growBoxApplication().container.growBoxRepository)
        }
//        initializer {
//            AuthViewModel(
//                growBoxApplication().container.growBoxRepository
//            )
//        }

    }
}

fun CreationExtras.growBoxApplication(): GrowBoxApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GrowBoxApplication)