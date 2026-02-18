package com.example.growbox.screen.profile.change_crop_type.select_crop_type


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growbox.R
import com.example.growbox.data.GrowBoxRepository
import com.example.growbox.screen.profile.change_crop_type.select_crop_type.model.SelectCropUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class SelectCropViewModel(
    private val growBoxRepository: GrowBoxRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SelectCropUiState())
    val uiState: StateFlow<SelectCropUiState> = _uiState.asStateFlow()

    private val cropTypeResIds = listOf(
        R.string.crop_type_vegetables,
        R.string.crop_type_flowering_plants,
        R.string.crop_type_herbs,
        R.string.crop_type_microgreens,
        R.string.crop_type_mushrooms
    )

    fun selectCropType(index: Int) {
        _uiState.value = _uiState.value.copy(selectedCropType = index)
    }


    fun saveCropType(cropTypeName: String) {
        viewModelScope.launch {
            val userId = growBoxRepository.getCurrentUserId() ?: return@launch
            try {
                growBoxRepository.harvestAndStartNewCycle(userId, cropTypeName)
            } catch (e: Exception) {

            }
        }
    }

}