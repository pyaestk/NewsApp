package com.example.newsapp


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecases.appEntry.AppEntryUseCases
import com.example.newsapp.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel() {

    var splashCondition by mutableStateOf<Boolean>(true)
        private set

    var stateDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        appEntryUseCases.readAppEntry().onEach { showOnBoarding ->
            if (showOnBoarding){
                stateDestination = Route.NewsNavigation.route
            } else {
                stateDestination = Route.AppStartNavigation.route
            }
            delay(100)

            splashCondition = false
        }.launchIn(viewModelScope)
    }
}