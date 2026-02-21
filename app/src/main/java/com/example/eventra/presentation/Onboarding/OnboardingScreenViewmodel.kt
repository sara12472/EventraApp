package com.example.eventra.presentation.Onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnboardingScreenViewmodel: ViewModel() {

    private  val _currentPage= MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage

    fun nextPage(totalPages: Int) {
        if (_currentPage.value < totalPages - 1) {
            _currentPage.value++
        }
    }

    fun previousPage(){
        if(_currentPage.value >0 ){
            _currentPage.value--

        }
    }
    fun reset() {
        _currentPage.value = 0
    }



}