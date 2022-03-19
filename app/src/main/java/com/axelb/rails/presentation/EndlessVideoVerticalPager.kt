package com.axelb.rails.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.axelb.rails.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
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

    VerticalPager(
        count = itemCount.value,
        state = pagerState,
        key = { it }
    ) { pageIndex ->
        val drawableId = if (pageIndex % 2 == 0)
            R.drawable.yuna_blue
        else
            R.drawable.yuna_white

        if (
            itemCount.value - 2 == currentPage ||
            pageIndex == itemCount.value - 1
        ) {
            itemCount.value += 3
            Timber.d("itemCount increased to ${itemCount.value}")
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "yuna img",
                modifier = Modifier.fillMaxSize().zIndex(1f),
                contentScale = ContentScale.Crop
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
}