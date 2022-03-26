package com.axelb.rails.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.axelb.rails.R
import com.axelb.rails.presentation.state.PlayPauseOverlayState
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayPauseOverlayComposable(
	modifier: Modifier = Modifier,
	state: State<PlayPauseOverlayState>,
) {

	val defaultTween = tween<Float>(
		durationMillis = 150,
		easing = LinearEasing
	)
	AnimatedVisibility(
		modifier = modifier,
		visible = state.value.isShown,
		enter = fadeIn(animationSpec = defaultTween) + scaleIn(animationSpec = defaultTween),
		exit = fadeOut(animationSpec = defaultTween) + scaleOut(animationSpec = defaultTween)
	) {
		Box(
			modifier = Modifier
				.clip(RoundedCornerShape(50))
				.background(
					color = Color(0, 0, 0, 0x99),
					shape = RoundedCornerShape(50)
				)
				.padding(24.dp)
		) {
			Icon(
				painter = painterResource(id = state.value.drawableId),
				contentDescription = "Play",
				tint = Color.White,
				modifier = Modifier
					.size(32.dp)
			)
		}
	}
}