package fhnw.emoba.modules.module07.photobooth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.foundation.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.UriHandlerAmbient
import androidx.compose.ui.unit.dp
import fhnw.emoba.modules.module07.photobooth.model.PhotoBooth

@Composable
fun PhotoBootUI(model: PhotoBooth) {
    MaterialTheme(colors = lightColors(primary = Color(0xFF2E78D7)) ) {
        Scaffold(
            topBar                       = { Bar(model) },
            bodyContent                  = { Body(model) },
            floatingActionButton         = { FAB(model) },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        )
    }
}

@Composable
private fun Bar(model: PhotoBooth) {
    with(model) {
        TopAppBar(title = { Text(title) })
    }
}

@Composable
private fun Body(model: PhotoBooth) {
    with(model) {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val margin = 10.dp

            val (messageBox, selfie) = createRefs()

            if (photo != null) {
                Image(asset   = photo!!.asImageAsset(),
                     modifier = Modifier.constrainAs(selfie) {
                        top.linkTo(parent.top, margin)
                        start.linkTo(parent.start, margin)
                        end.linkTo(parent.end, margin)
                        width = Dimension.fillToConstraints
                    }
                    .clickable(onClick = { model.rotatePhoto() }))
            } else {
                Text("Take a Picture",
                    style    = MaterialTheme.typography.h5,
                    modifier = Modifier.constrainAs(messageBox) {
                        centerTo(parent)
                    })
            }
        }
    }
}

@Composable
private fun FAB(model: PhotoBooth) {
    with(model) {
        FloatingActionButton(
            onClick         = { takePhoto() },
            icon            = { Icon(Icons.Filled.CameraAlt) },
            backgroundColor = MaterialTheme.colors.primary
        )
    }
}