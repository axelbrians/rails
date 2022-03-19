package com.axelb.rails.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.axelb.rails.R

@Composable
fun OverlayVideoVerticalPager(
    modifier: Modifier = Modifier
) {
    val channelShape = RoundedCornerShape(50f)
    val backSoundShape = RoundedCornerShape(4.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp)
            .zIndex(3f),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 18.dp, top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "arrow back",
                modifier = Modifier.size(26.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "camera",
                modifier = Modifier.size(26.dp)
            )
        }

        Box(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.padding(end = 18.dp, top = 16.dp, bottom = 16.dp),
                color = Color.Transparent
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dots_more_horizontal),
                    contentDescription = "camera",
                    modifier = Modifier.size(30.dp)
                )
            }

            Column(
                modifier = Modifier.padding(end = 18.dp, top = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_thumbs_up),
                    contentDescription = "camera",
                    modifier = Modifier.size(30.dp)
                )
                Text(text
                = "531K",
                    color = Color.White,
                    fontWeight = FontWeight.W400,
                    fontSize = 13.sp,)
            }
            Column(
                modifier = Modifier.padding(end = 18.dp, top = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_thumbs_down),
                    contentDescription = "camera",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Dislike",
                    color = Color.White,
                    fontWeight = FontWeight.W400,
                    fontSize = 13.sp)
            }
            Column(
                modifier = Modifier.padding(end = 18.dp, top = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_comment_bubble),
                    contentDescription = "camera",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "717",
                    color = Color.White,
                    fontWeight = FontWeight.W400,
                    fontSize = 13.sp)
            }
            Column(
                modifier = Modifier.padding(end = 18.dp, top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "camera",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Share",
                    color = Color.White,
                    fontWeight = FontWeight.W400,
                    fontSize = 13.sp)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 18.dp, vertical = 18.dp)
            ) {
                Text(
                    text = "How Yeji Catch other members vs Ryujin",
                    style = TextStyle(color = Color.White),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Image(
                        painter = painterResource(id = R.drawable.yuna_white),
                        contentDescription = "channel display picture",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(channelShape)
                            .border(width = 1.dp, color = Color.White, shape = channelShape)
                    )
                    
                    Text(
                        text = "YouTube Channel",
                        style = TextStyle(color = Color.White),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }

            Box(modifier = Modifier.padding(18.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.img_kang_mina),
                    contentDescription = "kang mina in white",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(backSoundShape)
                        .border(width = 1.dp, color = Color.White, shape = backSoundShape)
                )
            }
        }
    }
}