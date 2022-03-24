package com.axelb.rails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.axelb.rails.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import timber.log.Timber

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EndlessVideoVerticalPager(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val itemCount = remember {
        mutableStateOf(3)
    }
    val addedItems = listOf(
        R.raw.gidle_tomboy,
        R.raw.video_kucing_masak
    )
    val addedThumbnailId = listOf(
        R.drawable.gidle_tomboy,
        R.drawable.video_kucing
    )
    val videoItems = remember {
        mutableStateListOf(
            R.raw.gidle_tomboy,
            R.raw.video_kucing_masak
        )
    }
    val thumbnailItems = remember {
        mutableStateListOf(
            R.drawable.gidle_tomboy,
            R.drawable.video_kucing
        )
    }
    val isAutoPlay = true
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            this.prepare()
            this.playWhenReady = true
            this.repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    val currentVideo = if (isAutoPlay) {
        determineCurrentlyPlayingItem(pagerState, videoItems)
    } else {
        null
    }

    updateCurrentlyPlayingItem(exoPlayer = exoPlayer, video = currentVideo)
    DisposableEffect(
        VerticalPager(
            count = videoItems.size,
            state = pagerState,
            key = { it }
        ) { pageIndex ->

            if (currentPage == videoItems.lastIndex) {
                videoItems.addAll(addedItems)
                thumbnailItems.addAll(addedThumbnailId)
                Timber.d("itemCount increased to ${itemCount.value}")
            }

            Box(modifier = Modifier.fillMaxSize()) {
//            Image(
//                painter = painterResource(id = drawableId),
//                contentDescription = "yuna img",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .zIndex(1f),
//                contentScale = ContentScale.Crop
//            )
                VideoPlayerComposable(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f),
                    exoPlayer = exoPlayer,
                    thumbnailId = thumbnailItems[pageIndex],
                    isCurrentShownPage = pageIndex == currentPage
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
                                    Color(0, 0, 0, 0x66)
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
}

@Composable
private fun updateCurrentlyPlayingItem(exoPlayer: ExoPlayer, video: Int?) {
    LaunchedEffect(video) {
        exoPlayer.apply {
            if (video != null) {
                val videoUri = RawResourceDataSource.buildRawResourceUri(video)
                val mediaItem = MediaItem.Builder()
                        .setUri(videoUri)
                        .setMediaId(video.toString())
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
            } else {
                stop()
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
private fun determineCurrentlyPlayingItem(pagerState: PagerState, videoItems: List<Int>): Int? {

    val currentPage = pagerState.currentPage
    return videoItems[currentPage]

//
//    val layoutInfo = pagerState.layoutInfo
//    val visibleTweets = layoutInfo.visibleItemsInfo.map { videoItems[it.index] }
//    val tweetsWithVideo = visibleTweets.filter { it.hasAnimatedMedia }
//    return if (tweetsWithVideo.size == 1) {
//        tweetsWithVideo.first()
//    } else {
//        val midPoint = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
//        val itemsFromCenter =
//            layoutInfo.visibleItemsInfo.sortedBy { abs((it.offset + it.size / 2) - midPoint) }
//        itemsFromCenter.map { videoItems[it.index] }.firstOrNull { it.hasAnimatedMedia }
//    }
}