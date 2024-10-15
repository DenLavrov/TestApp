package com.test.app.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val smallSpace: Dp,
    val largeSpace: Dp,
    val mediumSpace: Dp,
    val profileImage: Dp,
    val buttonPadding: Dp,
    val chatMessagePadding: Dp,
    val shadow: Dp,
    val appBarImage: Dp,
    val chatImage: Dp
)

val DefaultDimens = Dimens(8.dp, 24.dp, 16.dp, 200.dp, 12.dp, 10.dp, 8.dp, 30.dp, 48.dp)

val LocalDimens = staticCompositionLocalOf {
    DefaultDimens
}