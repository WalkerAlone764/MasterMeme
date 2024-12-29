package com.upsidedowndev.mastermeme.meme.presentation.meme_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsidedowndev.mastermeme.meme.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemeListViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _uiState = MutableStateFlow<MemeListState>(MemeListState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<MemeListEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    val memeList = repository
        .getAllMeme()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onAction(action: MemeListAction) {
        when(action) {
            MemeListAction.OnAddMeme -> {
                _uiState.update { state ->
                    state.copy(
                        templateListShown = true
                    )
                }
            }

            is MemeListAction.OnClickMeme -> {
                viewModelScope.launch {
                    _uiState.update { state ->
                        state.copy(
                            templateListShown = false
                        )
                    }
                    delay(40)

                    _uiEvent.send(
                        MemeListEvent.NavigateToCreate(action.memeId,action.memeDefaultId , action.memeStatus)
                    )
                }
            }
        }

    }

}