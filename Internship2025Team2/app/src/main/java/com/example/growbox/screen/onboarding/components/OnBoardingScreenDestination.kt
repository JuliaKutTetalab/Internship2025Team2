package com.example.growbox.screen.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.growbox.R
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.onboarding.OnBoardingEffect
import com.example.growbox.screen.onboarding.OnBoardingEvent
import com.example.growbox.screen.onboarding.OnBoardingViewModel
import com.example.growbox.screen.onboarding.pages
import kotlinx.coroutines.launch

object OnBoardingScreenDestination: NavigationDestination{
    override val route = "onboarding_route"
    override val titleRes = R.string.onboarding_name
    override val showBottomBar  = false
}
@Composable
fun OnBoardingScreen(
    onNavigateToHome: () -> Unit,
    viewModel: OnBoardingViewModel = viewModel()
){

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect){
                is OnBoardingEffect.NavigateToHome -> {
                    onNavigateToHome()
                }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = dimensionResource(R.dimen.padding_large))
    ){
        val pagerState = rememberPagerState(initialPage = 0){
            pages.size
        }

        val buttonState = remember{
            derivedStateOf {
                when (pagerState.currentPage){
                    0 -> listOf("Skip","Next")
                    1 -> listOf("Skip","Next")
                    2 -> listOf("Skip","Next")
                    else -> listOf("","")
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            OnBoardingPage(page = pages[index])
        }

        PageIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(dimensionResource(R.dimen.spacing_large)),
            pageSize = pages.size,
            selectedPage = pagerState.currentPage
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_large))
                .padding(bottom = dimensionResource(R.dimen.padding_extra_large)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val scope = rememberCoroutineScope()



            if (buttonState.value[0].isNotEmpty()) {
                FinishOnBoardingTextButton(
                    text = buttonState.value[0],
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = pages.size - 1)
                        }
                    }
                )
            }else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            if (buttonState.value[1].isNotEmpty()) {
                Box(modifier = Modifier.width(dimensionResource(R.dimen.button_onboarding_large))){
                GradientButton(
                    text = buttonState.value[1],
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == pages.size - 1) {
                                //Event переходить в ViewModel
                                viewModel.onEvent(OnBoardingEvent.CompleteOnBoarding)
                            } else {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1
                                )
                            }
                        }
                    }
                )
            }
        }

    }
}
}