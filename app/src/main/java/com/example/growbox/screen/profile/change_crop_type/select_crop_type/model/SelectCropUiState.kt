package com.example.growbox.screen.profile.change_crop_type.select_crop_type.model

import com.example.growbox.R


data class SelectCropUiState(
    val availableCropType: List<Int> = listOf(
        R.string.crop_type_vegetables,
        R.string.crop_type_flowering_plants,
        R.string.crop_type_herbs,
        R.string.crop_type_microgreens,
        R.string.crop_type_mushrooms
    ),
    val selectedCropType: Int? = null,
    val isLoading: Boolean = false
)