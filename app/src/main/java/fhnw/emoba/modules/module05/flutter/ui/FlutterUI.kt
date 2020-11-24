package fhnw.emoba.modules.module05.flutter.ui

import android.view.Gravity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import fhnw.emoba.modules.module05.flutter.data.Flap
import fhnw.emoba.modules.module05.flutter.model.FlutterModel
import fhnw.emoba.modules.module05.flutter_solution.ui.theme.gray300

@Composable
fun FlutterUI(model: FlutterModel) {
    MaterialTheme() {
        Scaffold(   topBar      = { Bar(model) },
                    bodyContent = { Body(model) }
        )

    }
}

@Composable
private fun Bar(model: FlutterModel) {
    with(model) {
        TopAppBar(title = { Text(title) })
    }
}

@Composable
private fun Body(model: FlutterModel) {
    with(model) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (brokerInfo, topicInfo, allFlapsPanel, publishButton) = createRefs()

                Info(mqttBroker, Modifier.constrainAs(brokerInfo){
                    top.linkTo(parent.top, 10.dp)
                    start.linkTo(parent.start, 10.dp)
                })
                Info(mainTopic, Modifier.constrainAs(topicInfo){
                    top.linkTo(brokerInfo.bottom, 10.dp)
                    start.linkTo(parent.start, 10.dp)
                })
                AllFlapsPanel(flaps = model.allFlaps, Modifier.constrainAs(allFlapsPanel){
                    width  = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(topicInfo.bottom, 15.dp)
                    start.linkTo(parent.start, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    bottom.linkTo(publishButton.top, 15.dp)
                })
                PublishButton(model, Modifier.constrainAs(publishButton){
                    width = Dimension.fillToConstraints
                    start.linkTo(parent.start, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    bottom.linkTo(parent.bottom, 15.dp)
                })
        }
        Toast(model.strangeMessage)
    }
}

@Composable
private fun AllFlapsPanel(flaps: List<Flap>, modifier: Modifier){
    Box(modifier.border( width= 1.dp,
                         brush = SolidColor(gray300),
                         shape = RectangleShape)){
        if(flaps.isEmpty()){
            Text(text = "Noch keine Flaps",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.align(Alignment.Center)
                )
        } else{
            val scrollState = rememberScrollState()
            ScrollableColumn(scrollState = scrollState) {flaps.forEach { FlapView(flap = it) }}
            onCommit(callback = {scrollState.smoothScrollTo(scrollState.maxValue + 500)})
        }
    }
}

@Composable
private fun FlapView(flap: Flap){
    with(flap){
        ListItem(text = { Text(message)},
                overlineText = {Text(sender)}
        )
        Divider()
/*
        ListItem(
            text = { Text("Sender", style = MaterialTheme.typography.caption) },
            secondaryText = { Text("Nachrist vom Sender") },
            )
 */
    }
}

@Composable
private fun PublishButton(model: FlutterModel, modifier: Modifier){
    Button(
        onClick = { model.publish() },
        shape = CircleShape,
        modifier = modifier
    ) {
        Text("Publish (${model.flapsPublished})")
    }
}


@Composable
private fun Info(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        modifier = modifier
    )
}

@Composable
private fun Toast(message: String){
    if(message.isNotBlank()){
        val context = ContextAmbient.current
        android.widget.Toast.makeText(context, "Exotische Nachricht: '$message'", android.widget.Toast.LENGTH_LONG).apply { 
                setGravity(Gravity.CENTER, 0, 0)
                show()
        }
    }
}

