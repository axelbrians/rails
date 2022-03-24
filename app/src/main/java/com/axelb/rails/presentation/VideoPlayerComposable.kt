package com.axelb.rails.presentation

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import timber.log.Timber

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VideoPlayerComposable(
    modifier: Modifier = Modifier,
    exoPlayer: ExoPlayer,
    thumbnailId: Int,
    isCurrentShownPage: Boolean
) {
    Box(modifier = modifier) {
        if (isCurrentShownPage) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        player = exoPlayer
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        useController = false
                    }
                }
            )
        } else {
            Image(
                painter = painterResource(id = thumbnailId),
                contentDescription = "yuna img",
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f),
                contentScale = ContentScale.Crop
            )
        }

    }
}