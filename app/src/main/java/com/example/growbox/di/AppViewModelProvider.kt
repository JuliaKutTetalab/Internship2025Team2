package com.example.growbox.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.growbox.GrowBoxApplication
import com.example.growbox.screen.auth.auth.LoginViewModel
import com.example.growbox.screen.auth.auth.SignUpViewModel
import com.example.growbox.screen.auth.auth.SplashScreenViewModel
import com.example.growbox.screen.home.HomeScreenViewModel
import com.example.growbox.screen.home.chart.ChartViewModel
import com.example.growbox.screen.home.chart.model.ChartType
import com.example.growbox.screen.profile.ProfileViewModel
import com.example.growbox.screen.profile.change_crop_type.ChangeCropViewModel
import com.example.growbox.screen.profile.change_crop_type.select_crop_type.SelectCropViewModel
import com.example.growbox.screen.profile.historic_data.model.HistoricChartViewModel
import com.example.growbox.screen.profile.my_harvest.MyHarvestViewModel
import com.example.growbox.screen.settings.SettingsViewModel


object AppViewModelProvider {

    val Factory = viewModelFactory {


        initializer {
            val app = growBoxApplication()
            HomeScreenViewModel(
               app.container.growBoxRepository,
                app.container.offlineRepository
            )
        }

        initializer {
            val app = growBoxApplication()
            ProfileViewModel(
                growBoxRepository = app.container.growBoxRepository,
                offlineRepository = app.container.offlineRepository
            )
        }


        initializer {
            val app = growBoxApplication()
            MyHarvestViewModel(
                app.container.growBoxRepository,
            )
        }

        initializer {
            val app = growBoxApplication()
            SplashScreenViewModel(
                app.container.growBoxRepository
            )
        }

        initializer {
            val app = growBoxApplication()
            LoginViewModel(app.container.growBoxRepository)
        }

        initializer {
            val app = growBoxApplication()
            SignUpViewModel(app.container.growBoxRepository)
        }


        initializer {
            val app = growBoxApplication()
            SettingsViewModel(app.container.growBoxRepository)
        }

        initializer {
            val app = growBoxApplication()
            ChartViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                growBoxRepository = app.container.growBoxRepository,
                offlineRepository = app.container.offlineRepository
            )
        }

        initializer {
            val app = growBoxApplication()
            ChangeCropViewModel(
                app.container.growBoxRepository,
                app.container.offlineRepository
            )
        }

        initializer {
            val app = growBoxApplication()
            SelectCropViewModel(
                app.container.growBoxRepository
            )
        }

        initializer {
            val app = growBoxApplication()
            HistoricChartViewModel(
                growBoxRepository = app.container.growBoxRepository,
                offlineRepository = app.container.offlineRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }


    }
}

fun CreationExtras.growBoxApplication(): GrowBoxApplication {
    val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
    return requireNotNull(app) {
        "GrowBoxApplication is missing in CreationExtras. " +
                "Are you calling viewModel() in Preview or without a proper Activity owner?"
    } as GrowBoxApplication
}