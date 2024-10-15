package com.test.app.core.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.test.app.R
import com.test.app.ui.theme.TestAppTheme
import com.test.app.ui.theme.dimens

@Preview
@Composable
private fun ScaffoldPreview() {
    TestAppTheme {
        SimpleScaffold(
            title = "Some title",
            onBackClick = { /*TODO*/ },
            "https://cdn.pixabay.com/photo/2016/10/10/14/46/icon-1728549_640.jpg"
        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleScaffold(
    title: String,
    onBackClick: (() -> Unit)? = null,
    image: String? = null,
    imagePlaceholder: Painter? = null,
    imageSize: Dp = MaterialTheme.dimens.appBarImage,
    onImageClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        if (image != null || imagePlaceholder != null) {
                            AsyncImage(
                                model = image,
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(imageSize)
                                    .clickable(onClick = onImageClick),
                                contentScale = ContentScale.FillBounds,
                                error = imagePlaceholder
                            )
                            Spacer(modifier = Modifier.width(MaterialTheme.dimens.smallSpace))
                        }
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                },
                navigationIcon = {
                    onBackClick?.let {
                        IconButton(onClick = it) {
                            Icon(
                                painter = painterResource(R.drawable.back_icon),
                                contentDescription = null
                            )
                        }
                    }
                },
                actions = actions,
                modifier = Modifier
                    .shadow(MaterialTheme.dimens.shadow)
            )
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}