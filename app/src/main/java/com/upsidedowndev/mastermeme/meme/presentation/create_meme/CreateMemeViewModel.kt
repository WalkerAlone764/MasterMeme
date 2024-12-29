package com.upsidedowndev.mastermeme.meme.presentation.create_meme

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.upsidedowndev.mastermeme.core.presentation.getTemplateList
import com.upsidedowndev.mastermeme.meme.data.model.MemeItem
import com.upsidedowndev.mastermeme.meme.data.model.MemeModel
import com.upsidedowndev.mastermeme.meme.domain.repository.Repository
import com.upsidedowndev.mastermeme.meme.presentation.navigation.MemeStatus
import com.upsidedowndev.mastermeme.meme.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMemeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, private val repository: Repository
) : ViewModel() {

    val createMeme = savedStateHandle.toRoute<Route.CreateMeme>()

    private val _uiState = MutableStateFlow(CreateMemeState())
    val uiState = _uiState.asStateFlow().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _uiState.value
    )

    private val _uiEvent = Channel<CreateMemeEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        if (createMeme.memeStatus == MemeStatus.CREATE) {
            _uiState.update { state ->
                state.copy(
                    backgroundMeme = getTemplateList()[createMeme.memeId]
                )
            }
        } else {
            viewModelScope.launch {
                val meme = repository.getMemeById(createMeme.memeId)
                _uiState.update { state ->
                    state.copy(
                        memeTextList = meme.memeItem.map { memeItem ->
                        MemeText(
                            textFieldState = TextFieldState(memeItem.text),
                            offset = memeItem.offset,
                            defaultFontScale = memeItem.fontSize,
                            tempFontSize = memeItem.fontSize
                        )
                    },
                        backgroundMeme = getTemplateList().first { meme ->
                            meme.id == createMeme.memeDefaultId
                        }
                    )

                }
            }
        }
    }

    fun onAction(action: CreateMemeAction) {
        when (action) {
            CreateMemeAction.OnClickAddText -> {

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
                list.add(
                    MemeText(
                        isSelectedTextField = true,
                        textFieldState = TextFieldState(initialText = action.text.trim())
                    )
                )

                _uiState.update { state ->
                    state.copy(
                        memeTextList = list, textInputDialogShown = false
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

            is CreateMemeAction.OnClickSave -> {
                viewModelScope.launch {
                    val bitmap = action.graphicsLayer.toImageBitmap().asAndroidBitmap()
                    repository.insertMeme(
                        MemeModel(
                            memeItem = _uiState.value.memeTextList.map {
                                MemeItem(
                                    text = it.textFieldState.text.toString(),
                                    offset = it.offset,
                                    fontSize = it.tempFontSize
                                )
                            }, savedPath = "", bitmap = bitmap, defaultMemeId = _uiState.value.backgroundMeme.id


                        )
                    )
                    _uiEvent.send(
                        CreateMemeEvent.SavedInDB
                    )
                    delay(100)
                    _uiState.update { state ->
                        state.copy(
                            saveMemeBottomSheetShown = true
                        )
                    }

                }
            }

            CreateMemeAction.OnDismissSaveBottomSheet -> {
                viewModelScope.launch {
                    _uiState.update { state ->
                        state.copy(
                            saveMemeBottomSheetShown = false
                        )
                    }
                    delay(80)
                    _uiEvent.send(
                        CreateMemeEvent.NavigateBack
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
                    val result = repository.saveMemeToDevice(bitmap)

                    if (result.isNotEmpty()) {

                        _uiState.update { state ->
                            state.copy(
                                saveMemeBottomSheetShown = false
                            )
                        }

                        _uiEvent.send(CreateMemeEvent.SuccessfullySavedMeme)
                        delay(40)
                        _uiEvent.send(CreateMemeEvent.NavigateBack)
                    }
                }
            }

            is CreateMemeAction.ShareMeme -> {
                viewModelScope.launch {
                    try {
                        val bitmap = action.graphicsLayer.toImageBitmap().asAndroidBitmap()
                        val result = repository.shareMemeWithApp(bitmap)

                        if (result.isNotEmpty()) {
//                            repository.insertMeme(
//                                MemeModel(
//                                    memeItem = _uiState.value.memeTextList.map {
//                                        MemeItem(
//                                            text = it.textFieldState.text.toString(),
//                                            offset = it.offset,
//                                            fontSize = it.tempFontSize
//
//                                        )
//                                    }, savedPath = result
//
//                                )
//                            )
                            _uiState.update { state ->
                                state.copy(
                                    saveMemeBottomSheetShown = false
                                )
                            }
                            delay(40)
                            _uiEvent.send(CreateMemeEvent.NavigateBack)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            is CreateMemeAction.OnUpdateFontSize -> {
                val list = _uiState.value.memeTextList.toMutableList()
                val filteredList = list.filter { memeText ->
                    memeText.isSelectedTextField
                }.toMutableList()

                filteredList.forEach { memeText ->
                    list.remove(memeText)
                    list.add(
                        memeText.copy(
                            tempFontSize = action.size
                        )
                    )
                }

                _uiState.update { state ->
                    state.copy(
                        memeTextList = list
                    )
                }

            }

            CreateMemeAction.OnDismissFontSizeChange -> {
                Log.d("onDismiss", "Dismiss")
                val list = _uiState.value.memeTextList.toMutableList()
                val filteredList = list.filter { memeText ->
                    memeText.isSelectedTextField
                }.toMutableList()

                filteredList.forEach { memeText ->
                    list.remove(memeText)
                    list.add(
                        memeText.copy(
                            tempFontSize = memeText.defaultFontScale
                        )
                    )
                }

                val filterSelectedList = list.filter { memeText ->
                    memeText.isSelectedTextField

                }
                filterSelectedList.forEach { memeText ->
                    list.remove(memeText)
                    list.add(
                        memeText.copy(
                            isSelectedTextField = false
                        )
                    )
                }

                _uiState.update { state ->
                    state.copy(
                        memeTextList = list
                    )
                }

            }

            CreateMemeAction.OnSubmitFontSizeChange -> {
                val list = _uiState.value.memeTextList.toMutableList()
                val filteredList = list.filter { memeText ->
                    memeText.isSelectedTextField
                }.toMutableList()

                filteredList.forEach { memeText ->
                    list.remove(memeText)
                    list.add(
                        memeText.copy(
                            defaultFontScale = memeText.tempFontSize
                        )
                    )
                }

                val filterSelectedList = list.filter { memeText ->
                    memeText.isSelectedTextField

                }
                filterSelectedList.forEach { memeText ->
                    list.remove(memeText)
                    list.add(
                        memeText.copy(
                            isSelectedTextField = false
                        )
                    )
                }



                _uiState.update { state ->
                    state.copy(
                        memeTextList = list
                    )
                }
            }
        }
    }

}