package com.example.networkmemo.ui.composables

import android.content.res.Resources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Adjust
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.networkmemo.NODE_RADIUS
import com.example.networkmemo.R
import com.example.networkmemo.data.entities.Node
import com.example.networkmemo.pixelToDp

/**
 * Graph, Node, Edge
 *
 * 이전의 프로젝트에서는 계속해서 발생하는 노드와 엣지들의 리컴포지션을 스킵하기 위해서 했던 작업이
 * 노드의 정보를 업데이트하는데 장애물이 되었어.
 *
 * 그래서 그때 생각한 것이 노드와 노드를 덮는 인포를 분리하자는 생각이있었지.
 * 근데 지금 생각해보면 도긴개긴인거 같긴하다
 * 어차피 인포도 노드를 따라가야 하는 건데, 그럼 똑같네
 *
 * */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphScaffold(
    modifier: Modifier = Modifier,
    onReadMode: Boolean = true,
    viewInitButtonClickListener: () -> Unit = {},
    onFolderButtonClickListener: () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable BoxScope.() -> Unit,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val width  = Resources.getSystem().displayMetrics.widthPixels / 2f
    val height = Resources.getSystem().displayMetrics.heightPixels / 2f

    Scaffold(
        modifier = modifier,
        topBar = {
            GraphScaffoldTopBar(
                viewInitButtonClickListener = viewInitButtonClickListener,
                onFolderButtonClickListener = onFolderButtonClickListener,
                onReadMode = onReadMode
            )
        },
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, _ ->
                        if (zoom != 1f) {
                            scale *= zoom
                            offset += Offset(
                                x = (1 - zoom) * (centroid.x - offset.x - width),
                                y = (1 - zoom) * (centroid.y - offset.y - height)
                            )
                        } else offset += pan
                    }
                }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphScaffoldTopBar(
    viewInitButtonClickListener: () -> Unit,
    onFolderButtonClickListener: () -> Unit,
    onReadMode: Boolean,
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            Row {
                FilledIconButton(
                    onClick = viewInitButtonClickListener,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Adjust,
                        contentDescription = ""
                    )
                }
                FilledIconButton(
                    onClick = onFolderButtonClickListener,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Folder,
                        contentDescription = ""
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = { },
                enabled = !onReadMode,
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    contentColor = Color.Black,
                    disabledContentColor = Color(0x54757575)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.MenuBook,
                    contentDescription = "",
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawNode(
    node: () -> Node,
    dragAble: Boolean,
    onValueChanged: () -> Unit = {},
    onDragStart: () -> Unit = {},
    onDragEnd: () -> Unit = {},
    onNodeMoved: (Offset) -> Unit = { _ -> },
    onClickListener: (Node) -> Unit = {},
) {
    var image by remember { mutableStateOf(node().imgUri) }
    var content by remember { mutableStateOf(node().content) }

    SideEffect {

    }
    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = (node().x - NODE_RADIUS).toInt(),
                    y = (node().y - NODE_RADIUS).toInt()
                )
            }
            .size(pixelToDp(px = NODE_RADIUS * 2))
            .clickable { onClickListener(node()) }
            .pointerInput(Unit) {
                if (dragAble) {
                    detectDragGestures(
                        onDragStart = { onDragStart() },
                        onDragEnd = { onDragEnd() },
                        onDragCancel = { onDragEnd() }
                    ) { change, dragAmount ->
                        change.consume()
                        onNodeMoved(dragAmount)
                    }
                }
            }
    ) {
        Spacer(
            modifier = Modifier
                .offset { IntOffset((NODE_RADIUS / 2).toInt(), (NODE_RADIUS / 2).toInt()) }
                .size(pixelToDp(px = NODE_RADIUS))
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.onSecondary),
        )

        if (image.isNotEmpty()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .border(BorderStroke(1.dp, Color.White)),
                model = image,
                contentScale = ContentScale.Crop,
                contentDescription = "",
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
            )

            Text(
                modifier = Modifier
                    .offset { IntOffset(0, (NODE_RADIUS * 2).toInt() + 8) }
                    .size(width = pixelToDp(px = NODE_RADIUS * 2), height = 8.dp),
                text = content,
                color = Color.White,
                maxLines = 1,
                fontSize = 6.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .border(BorderStroke(1.dp, Color.White)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(0.dp),
                    text = content,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    softWrap = true
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}