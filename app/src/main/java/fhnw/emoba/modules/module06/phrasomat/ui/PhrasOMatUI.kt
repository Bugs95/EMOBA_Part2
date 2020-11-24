package fhnw.emoba.modules.module06.phrasomat.ui

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import fhnw.emoba.modules.module06.phrasomat.model.PhrasOMatModel

@Composable
fun PhrasOMatUI(model: PhrasOMatModel){
    MaterialTheme(colors = lightColors(primary = Color(0xFFCC6699))) {
        Scaffold(topBar                  = { Bar(model) },
            bodyContent                  = { Body(model) },
            floatingActionButton         = { FAB(model) },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        )
    }
}

@Composable
private fun Bar(model: PhrasOMatModel) {
    with(model){
        TopAppBar(title = { Text(title) })
    }
}

@Composable
private fun Body(model: PhrasOMatModel) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val margin = 20.dp

        val (phrasesBox) = createRefs()

        //todo: emptyList ersetzen
        PhrasesBox(model.allPhrases, Modifier.constrainAs(phrasesBox){
            start.linkTo(parent.start, margin)
            top.linkTo(parent.top, margin)
            end.linkTo(parent.end, margin)
            bottom.linkTo(parent.bottom, margin * 4)
            width  = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })
    }}

@Composable
private fun PhrasesBox(phrases: List<String>, modifier: Modifier){
    if(phrases.isEmpty()){
        Box(modifier  = modifier.drawLayer(shadowElevation = 2f),
            alignment = Alignment.Center) {
            Text("Noch keine Phrasen", style = MaterialTheme.typography.h5)
        }
    }
    else {
        //todo: ScrollableColumn mit allen bisher erzeugten Phrasen

        val scrollState = rememberScrollState() //Aktuelle Scrolling Zustand
        ScrollableColumn(modifier, scrollState = scrollState) {
            phrases.forEach { SinglePhrase(it) }
        }
        onCommit(callback = {
            scrollState.smoothScrollTo(scrollState.maxValue + 500)
        })
    }
}

@Composable
private fun SinglePhrase(phrase: String){
    Box(Modifier.preferredHeight(80.dp).padding(5.dp)){
        Text(phrase, Modifier.align(Alignment.CenterStart))
    }
    Divider()
}

@Composable
private fun FAB(model: PhrasOMatModel) {
    with(model) {
        FloatingActionButton(
            onClick = { newMarketingMessage() },  //todo : neue Phrase erzeugen
            icon    = { Icon(Icons.Filled.Add) })
    }
}