package com.example.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.newsapp.R


data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf<Page>(
    Page(
        title = "Stay Informed",
        description = "Get the latest headlines and breaking news from around the world, all in one place.",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Personalized News",
        description = "Follow topics and categories you care about to get news tailored just for you.",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Real-Time Updates",
        description = "Enable notifications to never miss important updates as they happen.",
        image = R.drawable.onboarding3
    )
)
