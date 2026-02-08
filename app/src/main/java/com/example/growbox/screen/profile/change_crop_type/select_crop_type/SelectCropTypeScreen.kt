package com.example.growbox.screen.profile.change_crop_type.select_crop_type

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.growbox.R
import com.example.growbox.screen.profile.change_crop_type.select_crop_type.components.SelectCropCarousel
import com.example.growbox.screen.profile.change_crop_type.select_crop_type.components.StartPlantingGradientButton
import com.example.growbox.ui.theme.Black
import com.example.growbox.ui.theme.White
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.growbox.di.AppViewModelProvider
import com.example.growbox.navigation.NavigationDestination

object SelectCropTypeDestination: NavigationDestination{
    override val route = "select_crop_type"
    override val titleRes = null
    override val showBottomBar = false
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCropTypeScreen (
    onNavigateBack: () -> Unit,
) {
    val viewModel: SelectCropViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_crop_screen),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_medium)),
                            tint = Black
                            )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = Black
                )
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.select_crop_type_title),
                    fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.spacing_huge)))

            SelectCropCarousel(
                cropTypeResIds = uiState.availableCropType,
                onCropTypeSelected = { index ->
                    viewModel.selectCropType(index)
                }
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.spacing_extra_large)))

            val selectedCropName = uiState.selectedCropType?.let { index ->
                stringResource(uiState.availableCropType[index])
            }
            StartPlantingGradientButton(
                isEnabled = uiState.selectedCropType != null,
                onClick = {
                    selectedCropName?.let { cropName ->
                        viewModel.saveCropType(cropName)
                    }
                    onNavigateBack()
                },
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}