package com.example.growbox.screen.profile.change_crop_type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.data.model.Crop
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.navigation.NavigationDestination
import com.example.growbox.screen.profile.change_crop_type.components.ChangeCropGradientButton
import com.example.growbox.screen.profile.change_crop_type.components.CropWarningText
import com.example.growbox.screen.profile.change_crop_type.components.CurrentCropInfo

object ChangeCropTypeDestination: NavigationDestination{
    override val route = "change_crop_type"
    override val titleRes = R.string.change_crop_type_title
    override val showBottomBar = true
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeCropTypeScreen (
    onNavigateBack: () -> Unit,
    onNavigateToSelectCropType: () -> Unit,
    viewModel: ChangeCropViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val cropFromUiState = if (uiState.cropType.isNotEmpty()) {
        Crop(
            cropType = uiState.cropType,
            currentDay = uiState.currentDay,
            totalDays = uiState.totalDays
        )
    } else {
        null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.change_crop_type_title),
                        fontSize = dimensionResource(R.dimen.font_size_huge).value.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_crop_screen),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_medium))
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ){
            CropWarningText(
                description = stringResource(R.string.crop_type_warning_text),

            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CurrentCropInfo(
                    cropType = cropFromUiState?.cropType ?: stringResource(R.string.no_active_crop_type),
                    currentDay = cropFromUiState?.currentDay ?: 0,
                    totalDays = cropFromUiState?.totalDays ?: 21
                    )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

            ChangeCropGradientButton(
                isEnabled = uiState.isButtonEnabled,
                onClick = {
                    if (uiState.isButtonEnabled)
                        onNavigateToSelectCropType()
                },
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
        }

    }
}