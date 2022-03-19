package com.axelb.rails

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import com.axelb.rails.presentation.EndlessVideoVerticalPager
import com.axelb.rails.presentation.OverlayVideoVerticalPager
import com.axelb.rails.ui.theme.RailsTheme
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
            }
        }
    }
}