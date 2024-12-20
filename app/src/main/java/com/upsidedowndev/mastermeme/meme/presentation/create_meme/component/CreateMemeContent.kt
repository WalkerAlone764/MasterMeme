package com.upsidedowndev.mastermeme.meme.presentation.create_meme.component

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.upsidedowndev.mastermeme.core.presentation.getMemeResourceList
import com.upsidedowndev.mastermeme.meme.presentation.common.Meme
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.CreateMemeAction
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.CreateMemeState
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.MemeText
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import com.upsidedowndev.mastermeme.ui.theme.impact

@Composable
fun CreateMemeContentForStockImages(
    modifier: Modifier = Modifier,
    @DrawableRes memeResources: Int,
    uiState: CreateMemeState,
    graphicsLayer: GraphicsLayer,
    onAction: (CreateMemeAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .drawWithContent {
                    // call record to capture the content in the graphics layer
                    graphicsLayer.record {
                        // draw the contents of the composable into the graphics layer
                        this@drawWithContent.drawContent()
                    }
                    // draw the graphics layer on the visible canvas
                    drawLayer(graphicsLayer)
                }
                .clipToBounds()
                .aspectRatio(1f)
        ) {
            Image(
                painter = painterResource(memeResources),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
            )

            //add text field
            uiState.memeTextList.forEach { state ->
                key(state) {
                    MemeDraggableText(
                        state = state,
                        onDragEnd = { offset, memeText ->
            onAction(CreateMemeAction.OnUpdatePosition(offset,memeText))
                        },
                        onClick = {
                            onAction(CreateMemeAction.OnSelectText(it))
                        }
                    )
                }

            }
        }
    }
}

@Composable
private fun MemeDraggableText(
    state: MemeText,
    onDragEnd: (Offset, MemeText) -> Unit,
    onClick: (MemeText) -> Unit
) {
    var localCurrentPos by remember {
        mutableStateOf(state.offset)
    }

    LaunchedEffect(state.isSelectedTextField) {
        if (state.isSelectedTextField) {
            state.focusRequester.captureFocus()
        }
    }

    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier
            .offset {
                IntOffset(
                    x = localCurrentPos.x.toInt(),
                    y = localCurrentPos.y.toInt()
                )
            }

            .pointerInput(key1 = Unit) {

                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        Log.d(
                            "TAG",
                            "CreateMemeContentForStockImages change: $change"
                        )
                        Log.d("TAG", "CreateMemeContentForStockImages: $dragAmount")

                        change.consume()

                        localCurrentPos += dragAmount
                    },

                    onDragEnd = {
                        onDragEnd(localCurrentPos, state)
                    }
                )

            },
    ) {
        if (state.isSelectedTextField) {
            Icon(
                Icons.Default.Close,
                "close",
                modifier = Modifier
                    .offset(
                        y = (-10).dp,
                        x = (15).dp
                    )
                    .background(
                        color = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(
                        4.dp
                    )
                    .zIndex(100f)
            )
        }
        Box(
            modifier = Modifier
                .border(
                    width = if (state.isSelectedTextField) 1.4.dp else (-1).dp,
                    color = Color.White,
                    shape = RoundedCornerShape(16)
                )
                .clickable {
                    Log.d("TextField", "MemeDraggableText: $state")
                }
        ) {
            TextField(
                value = state.textFieldState.text.toString(),
                onValueChange = {
                    state.textFieldState.setTextAndPlaceCursorAtEnd(it.toString())
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 40.sp,
                    fontFamily = impact,
                    fontWeight = FontWeight.Medium,
                    drawStyle = Stroke(
                        width = 20f,
                    ),
//                    lineHeight = 1.sp
//                    lineHeight = 40.sp

                ),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.focusRequester(state.focusRequester),
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    // works like onClick
                                    Log.d("TextField","hello world")
                                }
                            }
                        }
                    }



            )
//            Box(
//                modifier = Modifier
//                    .padding(TextFieldDefaults.contentPaddingWithoutLabel())
//            ) {
//                Text(
//                    text = state.textFieldState.text.toString(),
//                    color = Color.White,
//                    fontSize = 40.sp,
//                    fontFamily = impact,
//                    fontWeight = FontWeight.Medium,
//                    lineHeight = 40.sp,
//
//                )
//            }

            TextField(
                value = state.textFieldState.text.toString(),
                onValueChange = {
                    state.textFieldState.setTextAndPlaceCursorAtEnd(it.toString())
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontFamily = impact,
                    fontWeight = FontWeight.Medium,
//                    drawStyle = Stroke(
//                        width = 20f,
//                    ),
//                    lineHeight = 1.sp
//                    lineHeight = 40.sp

                ),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.focusRequester(state.focusRequester),
                readOnly = false,
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    // works like onClick
                                    onClick(state)
                                }
                            }
                        }
                    }


            )
        }
    }
}

@Preview
@Composable
private fun CreateMemeContentPreview() {
    MasterMemeTheme {
        CreateMemeContentForStockImages(
            memeResources = getMemeResourceList()[0],
            uiState = CreateMemeState(),

            graphicsLayer = rememberGraphicsLayer(),
            onAction = {}
        )
    }
}