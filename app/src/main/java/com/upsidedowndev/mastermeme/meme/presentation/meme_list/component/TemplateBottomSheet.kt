@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.upsidedowndev.mastermeme.meme.presentation.meme_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.mastermeme.core.presentation.getTemplateList
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.MemeListAction
import com.upsidedowndev.mastermeme.meme.presentation.navigation.MemeStatus
import com.upsidedowndev.mastermeme.ui.theme.manropeFamily

@Composable
fun TemplateBottomSheet(
    isTemplateBottomSheetVisible: Boolean, modifier: Modifier = Modifier,
    onAction: (MemeListAction) -> Unit,
) {
    if (isTemplateBottomSheetVisible) {
        ModalBottomSheet(onDismissRequest = {}) {
            Column(
                modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
            ) {
                Text(
                    text = "Choose Template",
                    fontSize = 16.sp,
                    fontFamily = manropeFamily,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Choose template for your next meme masterpiece",
                    fontSize = 12.sp,
                    fontFamily = manropeFamily,
                    fontWeight = FontWeight.Medium
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(180.dp)
                ) {
                    itemsIndexed(getTemplateList()) {index, memeDefault ->

                        Image(
                            painter = painterResource(memeDefault.memeDrawable),
                            contentDescription = "",
                            modifier = Modifier
                                .width(190.dp)
                                .height(240.dp)
                                .padding(
                                    horizontal = 8.dp, vertical = 12.dp
                                )
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    onAction(MemeListAction.OnClickMeme(index,-1, MemeStatus.CREATE))
                                },
                            contentScale = ContentScale.Crop
                        )
                    }

                }
            }
        }
    }
}