package com.example.growbox.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(): ViewModel() {

    private val _sideEffects = Channel <OnBoardingEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()
    fun onEvent(event: OnBoardingEvent){
        when(event){
            is OnBoardingEvent.CompleteOnBoarding -> {
                viewModelScope.launch {
                    _sideEffects.send(OnBoardingEffect.NavigateToHome)
                }
            }
        }
    }
}