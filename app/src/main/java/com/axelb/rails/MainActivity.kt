package com.axelb.rails

import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.axelb.rails.presentation.EndlessVideoVerticalPager
import com.axelb.rails.presentation.OverlayVideoVerticalPager
import com.axelb.rails.ui.theme.RailsTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.fitsSystemWindows = false
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        setContent {
            RailsTheme {
                EndlessVideoVerticalPager()
//                VideoPlayer()
            }
        }
    }

    @Composable
    private fun VideoPlayer(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val mediaItem = remember {
            val resourceId = R.raw.gidle_tomboy
            val videoUri = RawResourceDataSource.buildRawResourceUri(resourceId)
            MediaItem.Builder()
                .setUri(videoUri)
                .setMediaId(resourceId.toString())
                .setTag(videoUri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setDisplayTitle("GIDLE TOMBOY Challenge")
                        .build()
                )
                .build()
        }
        val exoPlayer = remember {
            ExoPlayer.Builder(context).build().apply {
                this.setMediaItem(mediaItem)
                this.prepare()
                this.playWhenReady = true
                this.repeatMode = Player.REPEAT_MODE_ONE
            }
        }
        DisposableEffect(
            Box {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        PlayerView(context).apply {
                            player = exoPlayer
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                            useController = false
                        }
                    })

                OverlayVideoVerticalPager()
            }
        ) {
            onDispose {
                Timber.d("onDispose DisposableEffect()")
                exoPlayer.pause()
                exoPlayer.release()
            }
        }
    }
}