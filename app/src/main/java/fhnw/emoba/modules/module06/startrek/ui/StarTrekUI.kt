package fhnw.emoba.modules.module06.startrek.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.TopAppBar
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.FailedResource
import androidx.compose.ui.res.LoadedResource
import androidx.compose.ui.res.PendingResource
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.unit.dp
import fhnw.emoba.R
import fhnw.emoba.modules.module06.startrek.model.StarTrekModel

@Composable
fun StarTrekUI(model: StarTrekModel) {
    MaterialTheme(colors     = lightColors(primary = Color(0xFF9C9CFF)),
                  typography = Typography(defaultFontFamily = StarTrek)
    ) {
        Scaffold(topBar      = { Bar(model) },
                 bodyContent = { Body(model) }
        )
    }
}

@Composable
private fun Bar(model: StarTrekModel) {
    with(model){
        TopAppBar(title = { Text(title, style = MaterialTheme.typography.h3)})
    }
}

@Composable
private fun Body(model: StarTrekModel) {
    with(model) {
        //todo: Durch ConstraintLayout ersetzen

        ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color(0xFFDDFFFF))) {
            val margin = 20.dp

            val(msgKirk, msgScotty, crew, scottie, transporter) = createRefs()
            Msg(messageKirk, Modifier.constrainAs(msgKirk){
                start.linkTo(parent.start, margin)
                top.linkTo(parent.top, margin)
            })

            Msg(reponseScotty, Modifier.constrainAs(msgScotty){
                top.linkTo(msgKirk.bottom)
                end.linkTo(parent.end, margin)
            })

            Panini(model = model, resId = R.drawable.scotty, reversed = false,
                modifier = Modifier.constrainAs(scottie){
                    //bottom.linkTo(transporter.top, margin)
                    start.linkTo(parent.start, margin)
                    end.linkTo(parent.end, margin)
                    top.linkTo(msgScotty.bottom, margin)
                    bottom.linkTo(transporter.top, margin)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                })

            Panini(model = model, resId = R.drawable.star_trek_crew,
                modifier = Modifier.constrainAs(crew){
                    //bottom.linkTo(transporter.top, margin)
                    start.linkTo(transporter.start, margin)
                    end.linkTo(transporter.end, margin)
                    bottom.linkTo(transporter.top, margin)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                })

            Transporter(model = model,
                modifier = Modifier.constrainAs(transporter){
                    start.linkTo(parent.start, margin)
                    end.linkTo(parent.end, margin)
                    bottom.linkTo(parent.bottom, margin)
                    width = Dimension.fillToConstraints //setzt element gemäss constraints mit margin
                })
        }
    }
}

@Composable
private fun Msg(text: String, modifier: Modifier){
    Text(text    = text,
        style    = MaterialTheme.typography.h4,
        modifier = modifier
    )
}

@Composable
private fun Transporter(model: StarTrekModel, modifier: Modifier){
    with(model){
        Slider(
            value = transporterStatus,
            onValueChange = { updateTransporterStatus(it) },
            modifier = modifier)
    }
}

@Composable
private fun Panini(model: StarTrekModel, @DrawableRes resId: Int, reversed: Boolean = true, modifier: Modifier) {
    val deferredResource = loadImageResource(id = resId)
    val resource         = deferredResource.resource

    when (resource) {
        is LoadedResource -> {
            val imageAsset = resource.resource!!
            Image(
                asset        = imageAsset,
                contentScale = ContentScale.FillHeight,
                modifier     = modifier.drawLayer(shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                                                  clip  = true,
                                                  alpha = if (reversed) {
                                                      model.transporterStatus
                                                  } else {
                                                      1.0f - model.transporterStatus
                                                  }
                )
            )
        }
        is FailedResource -> {
            Box(modifier = modifier,
                alignment = Alignment.Center){
                Text("failed", style = MaterialTheme.typography.h6, color = Color.Red)
            }
        }
        is PendingResource -> {
            Box(modifier = modifier,
                alignment = Alignment.Center) {
                LinearProgressIndicator()
            }
        }
    }
}

private val StarTrek = fontFamily(
    font(R.font.star_trek_enterprise, FontWeight.ExtraLight),
    font(R.font.star_trek_enterprise, FontWeight.Thin),
    font(R.font.star_trek_enterprise, FontWeight.Light),
    font(R.font.star_trek_enterprise, FontWeight.Normal),
    font(R.font.star_trek_enterprise, FontWeight.Bold),
    font(R.font.star_trek_enterprise, FontWeight.Black)
)

