package com.example.eventra.presentation.Onboarding

import com.example.eventra.R

data class OnboardingData(
    val image:Int,
    val tittle:String,
    val description: String
)

val onboardingItem=listOf<OnboardingData>(
    OnboardingData(image= R.drawable.onboarding1,
        tittle = "Plan your events easily",
        description = "Create, manage, and organize your events in one place with a clean and simple experience."),

    OnboardingData(image=R.drawable.onboarding2,
        tittle = "Never Miss a Reminder",
        description = "Get timely notifications and smart reminders so you’re always on track."),
    OnboardingData(image = R.drawable.onboarding3, tittle = " Stay Organized Every Day",
        description = "Keep your schedule organized and your day stress-free with powerful planning tools.")

)
