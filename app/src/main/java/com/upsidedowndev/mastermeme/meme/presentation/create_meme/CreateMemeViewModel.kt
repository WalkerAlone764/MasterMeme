@file:OptIn(ExperimentalCoroutinesApi::class)

package com.upsidedowndev.mastermeme.meme.presentation.create_meme

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsidedowndev.mastermeme.meme.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMemeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateMemeState())
    val uiState = _uiState.asStateFlow().mapLatest { value ->
            value.copy(anyTextSelected = value.memeTextList.any { it.isSelectedTextField })
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000L), _uiState.value
        )

    fun onAction(action: CreateMemeAction) {
        when (action) {
            CreateMemeAction.OnClickAddText -> {
//                val list = _uiState.value.memeTextList.toMutableList()
//                val filteredList = list.filter { memeText ->
//                    memeText.isSelectedTextField
//                }.toMutableList()
//                filteredList.forEachIndexed { index, meme ->
//                    list.removeAt(index)
//                    list.add(index, meme.copy(isSelectedTextField = false))
//                }
//                list.add(MemeText(isSelectedTextField = true))
//
//                _uiState.update { state ->
//                    state.copy(
//                        memeTextList = list
//                    )
//                }

            _uiState.update { state ->
                state.copy(
                    textInputDialogShown = true
                )
            }
            }

            is CreateMemeAction.OnDragText -> {
                val list = _uiState.value.memeTextList.toMutableList()
                val filteredList = list.filter { memeText ->
                    memeText.textFieldState != action.memeText.textFieldState
                }.toMutableList()
                filteredList.add(
                    element = MemeText(
                        textFieldState = action.memeText.textFieldState, offset = action.offset
                    )
                )

                _uiState.value = _uiState.value.copy(
                    memeTextList = filteredList
                )

            }

            CreateMemeAction.DismissSelection -> {
                val list = _uiState.value.memeTextList.toMutableList()
                val filteredSelectedList = list.filter { memeText ->
                    memeText.isSelectedTextField
                }.toMutableList()

                filteredSelectedList.forEach { memeText ->
                    list.remove(memeText)
                    list.add(
                        element = memeText.copy(
                            isSelectedTextField = false
                        )
                    )
                }

                _uiState.value = _uiState.value.copy(
                    memeTextList = list
                )

            }

            is CreateMemeAction.OnSelectText -> {
                Log.d("TAG", "onAction: ${action.memeText}")
                val list = _uiState.value.memeTextList.toMutableList()

                val filteredSelectedList = list.filter { memeText ->
                    memeText.isSelectedTextField
                }.toMutableList()

                filteredSelectedList.forEach { memeText ->
                    list.remove(memeText)
                    list.add(
                        element = memeText.copy(
                            isSelectedTextField = false
                        )
                    )
                }

                val filterList = list.filter { memeText ->
                    memeText.textFieldState == action.memeText.textFieldState
                }.toMutableList()

                _uiState.value = _uiState.value.copy(
                    memeTextList = list
                )

                filterList.forEach { meme ->
                    if (meme.textFieldState == action.memeText.textFieldState) {
                        list.remove(meme)
                        list.add(
                            meme.copy(
                                isSelectedTextField = true
                            )
                        )

                        meme.focusRequester.captureFocus()

                    }
                }

//                filteredList.map { memeText ->
//                    list.remove(memeText)
//                    list.add(
//                        memeText.copy(
//                            isSelectedTextField = true
//                        )
//                    )
//
//                }


                _uiState.value = _uiState.value.copy(
                    memeTextList = list
                )
            }

           is CreateMemeAction.OnCreateTextInput -> {
                val list = _uiState.value.memeTextList.toMutableList()
                val filteredList = list.filter { memeText ->
                    memeText.isSelectedTextField
                }.toMutableList()
                filteredList.forEachIndexed { index, meme ->
                    list.removeAt(index)
                    list.add(index, meme.copy(isSelectedTextField = false))
                }
                list.add(MemeText(isSelectedTextField = true, textFieldState = TextFieldState(initialText = action.text.trim())))

                _uiState.update { state ->
                    state.copy(
                        memeTextList = list,
                        textInputDialogShown = false
                    )
                }
            }

            CreateMemeAction.OnDismissTextInputDialog -> {
                _uiState.update { state ->
                    state.copy(
                        textInputDialogShown = false
                    )
                }
            }

            CreateMemeAction.OnClickSave -> {
                _uiState.update { state ->
                    state.copy(
                        saveMemeBottomSheetShown = true
                    )
                }
            }

            CreateMemeAction.OnDismissSaveBottomSheet -> {
                _uiState.update { state ->
                    state.copy(
                        saveMemeBottomSheetShown = false
                    )
                }
            }

            is CreateMemeAction.OnUpdatePosition -> {
                val list = _uiState.value.memeTextList.toMutableList()
                val filteredList = list.filter { memeText ->
                    memeText.textFieldState == action.memeText.textFieldState
                }.toMutableList()

                filteredList.forEach { memeText ->
                    list.remove(memeText)
                    list.add(
                        memeText.copy(
                            offset = action.offset
                        )
                    )
                }

                _uiState.update { state ->
                    state.copy(
                        memeTextList = list
                    )
                }
            }

            is CreateMemeAction.SaveToDevice -> {
                viewModelScope.launch {
                    val bitmap = action.graphicsLayer.toImageBitmap().asAndroidBitmap()
                    repository.saveToAppCache(bitmap)
                }
            }
        }
    }

}