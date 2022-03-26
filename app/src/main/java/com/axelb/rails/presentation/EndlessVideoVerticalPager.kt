package com.axelb.rails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.axelb.rails.R
import com.axelb.rails.presentation.model.VideoModel
import com.axelb.rails.presentation.state.PlayPauseOverlayState
import com.axelb.rails.presentation.state.rememberPlayPauseOverlayState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EndlessVideoVerticalPager(
	modifier: Modifier = Modifier
) {
	val pagerState = rememberPagerState()
	val playPauseOverlayState = rememberPlayPauseOverlayState(R.drawable.ic_pause_solid)
	val itemCount = remember {
		mutableStateOf(3)
	}
	val videoToAdd = listOf(
		VideoModel(R.raw.video_kucing_masak, R.drawable.video_kucing),
		VideoModel(R.raw.gidle_tomboy, R.drawable.gidle_tomboy),
		VideoModel(R.raw.salju_cast, R.drawable.salju_cast),
	)
	val videoList = remember {
		mutableStateListOf(
			VideoModel(R.raw.video_kucing_masak, R.drawable.video_kucing),
			VideoModel(R.raw.gidle_tomboy, R.drawable.gidle_tomboy),
			VideoModel(R.raw.salju_cast, R.drawable.salju_cast),
		)
	}

	val playPauseChannel = remember {
		Channel<Int>(Channel.CONFLATED)
	}

	val context = LocalContext.current
	val exoPlayer = remember {
		ExoPlayer.Builder(context).build().apply {
			this.prepare()
			this.playWhenReady = true
			this.repeatMode = Player.REPEAT_MODE_ONE
		}
	}

	val currentVideo = determineCurrentlyPlayingItem(pagerState, videoList)

	UpdateCurrentlyPlayingItem(exoPlayer = exoPlayer, data = currentVideo)
	DisposableEffect(
		VerticalPager(
			count = videoList.size,
			state = pagerState,
			key = { it }
		) { pageIndex ->

			if (currentPage == videoList.lastIndex) {
				videoList.addAll(videoToAdd)
				Timber.d("itemCount increased to ${itemCount.value}")
			}

			Box(modifier = Modifier.fillMaxSize()) {
				VideoPlayerComposable(
					modifier = Modifier
						.fillMaxSize()
						.zIndex(1f)
						.clickable(
							indication = null,
							interactionSource = remember { MutableInteractionSource() },
							onClick = {
								togglePlayPausePlayer(exoPlayer)
								playPauseChannel.trySend(
									if (exoPlayer.isPlaying) R.drawable.ic_play_solid
									else R.drawable.ic_pause_solid
								)
							}
						),
					exoPlayer = exoPlayer,
					thumbnailId = videoList[pageIndex].thumbnailId,
					isCurrentShownPage = pageIndex == currentPage
				)

				PlayPauseOverlayComposable(
					modifier = Modifier
						.align(Alignment.Center)
						.zIndex(2f),
					state = playPauseOverlayState
				)
				OverlayVideoVerticalPager()
				Box(
					modifier = Modifier
						.height(300.dp)
						.fillMaxWidth()
						.align(Alignment.BottomCenter)
						.zIndex(2f)
						.background(
							brush = Brush.verticalGradient(
								colors = listOf(
									Color(0, 0, 0, 0x00),
									Color(0, 0, 0, 0x99)
								)
							)
						),
				)
			}
		}
	) {
		onDispose {
			exoPlayer.release()
		}
	}

	// Side-effect to control how snackbar should showing
	LaunchedEffect(playPauseChannel) {
		playPauseChannel.receiveAsFlow().collect { drawableId ->
			Timber.d("drawableId: $drawableId")
			playPauseOverlayState.value = PlayPauseOverlayState(drawableId, true)
			delay(1000)
			playPauseOverlayState.value = PlayPauseOverlayState(drawableId, false)
			delay(200)
		}
	}
}

@Composable
private fun UpdateCurrentlyPlayingItem(exoPlayer: ExoPlayer, data: VideoModel) {
	LaunchedEffect(data) {
		exoPlayer.apply {
			val videoUri = RawResourceDataSource.buildRawResourceUri(data.videoId)
			val mediaItem = MediaItem.Builder()
				.setUri(videoUri)
				.setMediaId(data.videoId.toString())
				.setTag(videoUri)
				.setMediaMetadata(
					MediaMetadata.Builder()
						.setDisplayTitle("GIDLE TOMBOY Challenge")
						.build()
				)
				.build()

			setMediaItem(mediaItem)
			prepare()
			playWhenReady = true
		}
	}
}

@OptIn(ExperimentalPagerApi::class)
private fun determineCurrentlyPlayingItem(
	pagerState: PagerState,
	videoList: List<VideoModel>
): VideoModel {

	val currentPage = pagerState.currentPage
	return videoList[currentPage]

//
//    val layoutInfo = pagerState.layoutInfo
//    val visibleTweets = layoutInfo.visibleItemsInfo.map { videoList[it.index] }
//    val tweetsWithVideo = visibleTweets.filter { it.hasAnimatedMedia }
//    return if (tweetsWithVideo.size == 1) {
//        tweetsWithVideo.first()
//    } else {
//        val midPoint = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
//        val itemsFromCenter =
//            layoutInfo.visibleItemsInfo.sortedBy { abs((it.offset + it.size / 2) - midPoint) }
//        itemsFromCenter.map { videoList[it.index] }.firstOrNull { it.hasAnimatedMedia }
//    }
}

private fun togglePlayPausePlayer(exoPlayer: ExoPlayer) {
	with(exoPlayer) {
		if (isPlaying) {
			pause()
		} else {
			play()
		}
	}
}