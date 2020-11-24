package fhnw.emoba.modules.module06.lotto.ui

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fhnw.emoba.modules.module06.lotto.model.Kaestchen
import fhnw.emoba.modules.module06.lotto.data.Ziehung
import fhnw.emoba.modules.module06.lotto.model.LottoModel

@Composable
fun LottoUI(model: LottoModel) {
    MaterialTheme(colors = lightColors(primary = Color(0xFFCC6699))) {
        Scaffold(topBar                       = { Bar(model) },
                 bodyContent                  = { Body(model) },
                 floatingActionButton         = { FAB(model) },
                 floatingActionButtonPosition = FabPosition.Center,
                 isFloatingActionButtonDocked = true
        )
    }
}

@Composable
private fun Bar(model: LottoModel) {
    with(model){
        TopAppBar(title = { Text(title) })
    }
}

@Composable
private fun Body(model: LottoModel) {

    //todo: dieses Beispiel fuer ein ConstraintLayout genau anschauen
   ConstraintLayout(Modifier.fillMaxSize()) {
       val margin = 20.dp

       val(lottoscheinBox, buttonBox, ziehungenBox) = createRefs()

       LottoscheinBox(model.lottoschein, Modifier.constrainAs(lottoscheinBox){
           top.linkTo(parent.top, margin)
           start.linkTo(parent.start, margin)
           end.linkTo(parent.end, margin)
           width = Dimension.fillToConstraints
       })

       ButtonBox(model, Modifier.constrainAs(buttonBox){
           top.linkTo(lottoscheinBox.bottom, margin)
           start.linkTo(parent.start, margin)
           end.linkTo(parent.end, margin)
           width = Dimension.fillToConstraints
       })

       ZiehungenBox(model.alleZiehungen, Modifier.constrainAs(ziehungenBox){
           top.linkTo(buttonBox.bottom, margin * 3)
           start.linkTo(parent.start, margin)
           bottom.linkTo(parent.bottom, margin * 5)
           end.linkTo(parent.end, margin)
           width = Dimension.fillToConstraints
           height = Dimension.fillToConstraints
       } )
   }
}

@Composable
fun ButtonBox(model: LottoModel, modifier: Modifier) {
    Button(onClick  = { model.neuerLottoschein(4) },
           shape    = CircleShape,
           modifier = modifier) {
        Text("Neuer Lottoschein")
    }
}

@Composable
private fun LottoscheinBox(lottoschein: List<Kaestchen>, modifier: Modifier) {
    if (lottoschein.isEmpty()) {
        Box(modifier  = modifier,
            alignment = Alignment.Center) {
            Text("Noch kein Lottoschein", style = MaterialTheme.typography.h5)
        }
    } else {
        ScrollableRow(modifier) {
            lottoschein.forEach {
                KaestchenBox(it)
            }
        }
    }
}

@Composable
private fun KaestchenBox(kaestchen: Kaestchen) {
    with(kaestchen) {
        Card(Modifier.padding(horizontal = 5.dp), backgroundColor = Color(0xFFEEEEEE)) {
            Column(Modifier.padding(5.dp)) {
                Text(tip.gewinnZahlen.joinToString(separator = "  "), style = MaterialTheme.typography.body1)
                Text("Superzahl ${tip.superzahl}", style = MaterialTheme.typography.body2)

                Text(text = "$richtige Richtige ${if (superzahlRichtig && richtige >=5) " mit Superzahl" else ""}",
                     style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}

@Composable
private fun ZiehungenBox(ziehungen: List<Ziehung>, modifier: Modifier) {
    if (ziehungen.isEmpty()) {
        Box(modifier  = modifier.drawLayer(shadowElevation = 2f),
            alignment = Alignment.Center) {
            Text("Noch keine Ziehungen", style = MaterialTheme.typography.h5)
        }
    } else {
        val scrollState = rememberScrollState()
        ScrollableColumn(modifier = modifier.drawLayer(shadowElevation = 2f),
            scrollState = scrollState
        ) {
            ziehungen.forEach {
                ZiehungBox(it)
                Divider()
            }
        }
        onCommit(callback = {scrollState.smoothScrollTo(scrollState.maxValue + 500)})
    }
}

@Composable
private fun ZiehungBox(ziehung: Ziehung) {
    with(ziehung){
        ListItem(text          = { Text(gewinnZahlen.joinToString(separator = "  "))},
                 secondaryText = { Text("Superzahl ${superzahl}")},
                 overlineText  = { Text("Ziehung vom ${if(Math.random()> 0.5) "Samstag" else "Mittwoch"}")},
                 trailing      = { Icon(Icons.Filled.SentimentSatisfied)}
                 )
    }
}

@Composable
private fun FAB(model: LottoModel) {
    with(model) {
        FloatingActionButton(
            onClick = { neueZiehung()},
            icon    = { Icon(Icons.Filled.Add) })
    }
}
