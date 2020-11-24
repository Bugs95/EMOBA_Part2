package fhnw.emoba.modules.module07.gps.ui

import java.util.*
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.WrongLocation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.UriHandlerAmbient
import androidx.compose.ui.res.FailedResource
import androidx.compose.ui.res.LoadedResource
import androidx.compose.ui.res.PendingResource
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.R
import fhnw.emoba.modules.module07.gps.data.GeoPosition
import fhnw.emoba.modules.module07.gps.model.GpsModel

@Composable
fun GpsUI(model: GpsModel){
    MaterialTheme(colors     = lightColors(primary = Color(0xFF2E78D7)),
                  typography = Typography(defaultFontFamily = Diogenes)
    ) {
        Scaffold(topBar                  = { Bar(model) },
            bodyContent                  = { Body(model) },
            floatingActionButton         = { FAB(model) },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        )
    }
}

@Composable
private fun Bar(model: GpsModel) {
    with(model){
        TopAppBar(title = { Text(title, style = MaterialTheme.typography.h5) })
    }
}

@Composable
private fun Body(model: GpsModel) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val margin = 20.dp

        val (messageBox, waypointsBox) = createRefs()

        MessageBox(model.subtitle, Modifier.constrainAs(messageBox){
            start.linkTo(parent.start, margin)
            top.linkTo(parent.top, margin)
        })

        WaypointsBox(model, Modifier.constrainAs(waypointsBox){
            start.linkTo(parent.start, margin)
            top.linkTo(messageBox.bottom, margin)
            end.linkTo(parent.end, margin)
            bottom.linkTo(parent.bottom, margin * 4)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })
    }
}

@Composable
private fun MessageBox(text: String, modifier: Modifier){
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.h6)
}

@Composable
private fun WaypointsBox(model: GpsModel, modifier: Modifier){
    with(model){
        if(waypoints.isEmpty()){
            Box(modifier  = modifier.border(border = BorderStroke(1.dp, MaterialTheme.colors.primary))) {
                Text("Noch keine Wegpunkte", style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(30.dp))
                ImageAsync(R.drawable.theseus, Modifier.padding(30.dp).align(Alignment.Center))
            }
        }
        else {
            val scrollState = rememberScrollState()
            ScrollableColumn(modifier   = modifier.border(border = BorderStroke(1.dp, MaterialTheme.colors.primary)),
                            scrollState = scrollState) {
                waypoints.forEach { SingleWaypoint(model, it)}
            }
            onCommit(callback = {scrollState.smoothScrollTo(scrollState.maxValue + 500)})
        }
    }
}

@Composable
private fun SingleWaypoint(model: GpsModel, position: GeoPosition){
    val uriHandler = UriHandlerAmbient.current
    ListItem(text = { Text(position.dms(), style = MaterialTheme.typography.body2) },
        modifier = Modifier.border(border = BorderStroke(width = 1.dp,
                                                         color = MaterialTheme.colors.primary),
                                   shape  = ListItemShape)
                           .padding(vertical = 5.dp)
                           .clickable(onClick = { uriHandler.openUri(position.asGoogleMapsURL()) }),
        secondaryText = { Text(String.format(Locale("de", "CH"), "%.1f m ü.M.", position.altitude)) },
        trailing = {
            IconButton(onClick = { model.removeWaypoint(position) }) {
                Icon(Icons.Filled.WrongLocation, tint = MaterialTheme.colors.primary)
            }
        })
}

@Composable
private fun FAB(model: GpsModel) {
    with(model) {
        FloatingActionButton(
            onClick         = { rememberCurrentPosition() },
            icon            = { Icon(Icons.Filled.AddLocationAlt) },
            backgroundColor = MaterialTheme.colors.primary
        )
    }
}



private val cache =  HashMap<Int, ImageAsset>()

@Composable
private fun ImageAsync(@DrawableRes resId: Int, modifier: Modifier) {
    val imageAsset = cache[resId]

    if(imageAsset == null){
        val deferredResource = loadImageResource(id = resId)
        val resource         = deferredResource.resource

        when (resource) {
            is LoadedResource -> {
                val asset =  resource.resource!!
                cache[resId] = asset
                Image(asset = asset,  modifier = modifier)
            }
            is FailedResource -> {                       // laden ist fehlgeschlagen
                Text("failed", fontSize = 20.sp, color = Color.Red)
            }
            is PendingResource -> {                      // während des Ladens
                CircularProgressIndicator()
            }
        }
    }
    else {
        Image(asset = imageAsset,  modifier = modifier)
    }
}

private val Diogenes = fontFamily(
    font(R.font.diogenes, FontWeight.ExtraLight),
    font(R.font.diogenes, FontWeight.Thin),
    font(R.font.diogenes, FontWeight.Light),
    font(R.font.diogenes, FontWeight.Normal),
    font(R.font.diogenes, FontWeight.Bold),
    font(R.font.diogenes, FontWeight.Black)
)

private val ListItemShape: Shape = object : Shape {
    override fun createOutline(size: Size, density: Density) : Outline{
        val path = Path().apply {
            addRect(Rect(Offset.Zero, Offset(size.width, size.height + density.density)))
        }
        return Outline.Generic(path)
    }
}

