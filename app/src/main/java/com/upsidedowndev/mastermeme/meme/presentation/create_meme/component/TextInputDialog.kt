package com.upsidedowndev.mastermeme.meme.presentation.create_meme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import com.upsidedowndev.mastermeme.ui.theme.manropeFamily

@Composable
fun TextInputDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onCancel: () -> Unit,
    onOk: (String) -> Unit
) {
    var text by rememberSaveable{
        mutableStateOf("")
    }
    if (isVisible) {
    Dialog(
        onDismissRequest = {}
    ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 4.dp,
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(2)
                        )
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 24.dp
                        )
                ) {

                    Text(
                        text = "Text",
                        fontFamily = manropeFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontSize = 24.sp
                    )

                    TextField(
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedIndicatorColor = Color(79, 55, 139)
                        ),
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = manropeFamily,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Cancel",
                            fontFamily = manropeFamily,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .clickable {
                                    onCancel()
                                }
                        )
                        Spacer(modifier = Modifier.width(42.dp))
                        Text(
                            text = "OK",
                            fontFamily = manropeFamily,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .clickable {
                                    onOk(text)
                                    text = ""
                                }
                        )

                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun TextInputDialogPreview() {
    MasterMemeTheme {
        TextInputDialog(
            isVisible = true,
            onOk = {},
            onCancel = {}
        )
    }

}