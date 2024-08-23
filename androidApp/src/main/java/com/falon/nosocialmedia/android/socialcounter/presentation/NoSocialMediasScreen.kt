package com.falon.nosocialmedia.android.socialcounter.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.falon.nosocialmedia.socialcounter.presentation.model.SocialMediaItem
import com.falon.nosocialmedia.socialcounter.presentation.viewstate.NoSocialMediasViewState
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoSocialMediasScreen(
    viewState: NoSocialMediasViewState,
    onSocialMediaClick: (Int) -> Unit,
    onFabClick: () -> Unit,
) {
    var isTextFieldVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) { // Box przeniesiony na zewnątrz Scaffold
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        isTextFieldVisible = true
                        scope.launch {
                            keyboardController?.show()
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Social Media")
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(viewState.items.size) { index ->
                    val item = viewState.items[index]
                    ClickableCard(
                        item = viewState.items[index],
                        onClick = { onSocialMediaClick(item.id) }
                    )
                }
            }
        }

        // Dolne pole tekstowe z animacją wysuwania od dołu
        AnimatedVisibility(
            visible = isTextFieldVisible,
            enter = slideInVertically(initialOffsetY = { it }), // Wjeżdżanie od dołu
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier.align(Alignment.BottomCenter) //
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter) // Przypięcie do dolnej krawędzi ekranu
            ) {
                Surface(
                    elevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            value = "",
                            onValueChange = {},
                            modifier =  Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            placeholder = { Text("New habit" +
                                    "") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                isTextFieldVisible = false
                                keyboardController?.hide()
                            },
                        ) {
                            Text("Save")
                        }
                    }
                }
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
            .clickable {
                onClick()
            },
        elevation = 8.dp
    ) {
        Box {
            Text(
                text = item.name,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(8.dp)
                    .padding(4.dp)
            )
            Text(
                text = item.count.toString(),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp)
                    .padding(4.dp)
            )
        }
    }
}
