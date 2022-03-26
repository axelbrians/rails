package com.axelb.rails.presentation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberPlayPauseOverlayState(
	drawableId: Int = 0,
	isShown: Boolean = false
) = remember {
	mutableStateOf(PlayPauseOverlayState(drawableId, isShown))
}

data class PlayPauseOverlayState(
	val drawableId: Int,
	val isShown: Boolean
)
