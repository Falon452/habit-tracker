package com.falon.nosocialmedia.android.socialcounter.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.falon.nosocialmedia.android.R
import com.falon.nosocialmedia.socialcounter.presentation.model.SocialMediaItem
import com.falon.nosocialmedia.socialcounter.presentation.viewstate.NoSocialMediasViewState

@Composable
fun NoSocialMediasScreen(
    viewState: NoSocialMediasViewState,
    onSocialMediaClick: (Int) -> Unit,
) {
    Scaffold(
        floatingActionButton = { }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp), // Number of columns
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(viewState.items.size) { index ->
                ClickableCard(
                    item = viewState.items[index],
                    onClick = { onSocialMediaClick(index) }
                )
            }
        }
    }
}

@Composable
fun ClickableCard(item: SocialMediaItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f) // To make the cards square
            .clickable {
                onClick()
            },
        elevation = 8.dp
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_instagram_logo), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = item.count.toString(),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color(0x66000000))
                    .padding(4.dp)
            )
        }
    }
}
