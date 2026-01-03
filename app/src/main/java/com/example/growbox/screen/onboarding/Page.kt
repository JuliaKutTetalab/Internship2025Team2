package com.example.growbox.screen.onboarding

import androidx.annotation.DrawableRes
import com.example.growbox.R

data class Page(
    val title:Int,
    val description:Int,
    @DrawableRes val image: Int
)


val pages = listOf(
    Page(
        title = R.string.onboarding_screen1_title,
        description = R.string.onboarding_screen1_description,
        image = R.drawable.ic_onboarding_plant_1
    ),
    Page(
        title = R.string.onboarding_screen2_title,
        description = R.string.onboarding_screen2_description,
        image = R.drawable.ic_onboarding_plant_2
    ),
    Page(
        title = R.string.onboarding_screen3_title,
        description = R.string.onboarding_screen3_description,
        image = R.drawable.ic_onboarding_plant_3
    ),
)
