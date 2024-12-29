package com.upsidedowndev.mastermeme.meme.presentation.meme_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.upsidedowndev.mastermeme.meme.data.model.MemeModel
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.component.EmptyListComponent
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.component.MemeListFloatingActionButton
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.component.MemeListTopBar
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.component.TemplateBottomSheet
import com.upsidedowndev.mastermeme.meme.presentation.navigation.MemeStatus
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MemeListScreen(
    modifier: Modifier = Modifier,
    memeState: MemeListState,
    memeList: List<MemeModel>,
    onAction: (MemeListAction) -> Unit,
    navigateToCreateMeme: (Int,Int, MemeStatus) -> Unit
) {

    Scaffold(
        topBar = { MemeListTopBar() },
        floatingActionButton = {
            MemeListFloatingActionButton(onClick = {
                onAction(MemeListAction.OnAddMeme)
            })
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            if (memeList.isEmpty()) {
                EmptyListComponent()
            } else {
                MemeListContent(memeList = memeList, navigateToCreateMeme = navigateToCreateMeme)
            }

        }
    }

    TemplateBottomSheet(
        isTemplateBottomSheetVisible = memeState.templateListShown, modifier = Modifier,
        onAction = onAction
    )

}

@Composable
private fun MemeListContent(
    modifier: Modifier = Modifier, memeList: List<MemeModel>,
    navigateToCreateMeme:(Int,Int, MemeStatus) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = 12.dp, horizontal = 12.dp
            )
    ) {
        items(memeList, key = { it.id }) {
            MemeItem(it, onClick = {
                navigateToCreateMeme(it.id,it.defaultMemeId, MemeStatus.EDIT)
            })
        }
    }

}

@Composable
private fun MemeItem(
    memeModel: MemeModel,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(176.dp)
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(memeModel.bitmap)
                .crossfade(true)

                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,

            clipToBounds = true,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24f)),


        )
    }

}


@PreviewLightDark
@Composable
private fun MemeListScreenPreview() {
    MasterMemeTheme {
        MemeListScreen(
            memeState = MemeListState(),
            onAction = {}, memeList = emptyList(),
            navigateToCreateMeme = { meme,defaultID, sae -> {} }

        )
    }
}