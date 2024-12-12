package com.upsidedowndev.mastermeme.meme.presentation.meme_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MemeListViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<MemeListState>(MemeListState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: MemeListAction) {

    }

}