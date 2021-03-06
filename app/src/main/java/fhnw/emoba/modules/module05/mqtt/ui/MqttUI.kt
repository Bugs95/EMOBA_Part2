package fhnw.emoba.modules.module05.mqtt.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.modules.module05.mqtt.model.MqttModel

@Composable
fun MqttUI(model: MqttModel) {
    MaterialTheme {
        Scaffold(topBar      = { Bar(model) },
                 bodyContent = { Body(model) }
        )
    }
}

@Composable
private fun Bar(model: MqttModel) {
    with(model){
        TopAppBar(title = { Text(title) })
    }
}

@Composable
private fun Body(model: MqttModel) {
    with(model) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Info(mqttBroker)
            Info(mainTopic)
            Box(Modifier.fillMaxSize()) {
                Text(text     = "$messagesReceived",
                     style    = MaterialTheme.typography.h1,
                     modifier = Modifier.align(Alignment.Center)
                )
                Button(onClick  = { publish() },
                       shape    = CircleShape,
                       modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
                    Text("Publish ($messagesPublished)")
                }
            }
        }
    }
}

@Composable
private fun Info(text: String){
    Text(text    = text,
        style    = MaterialTheme.typography.h6,
        modifier = Modifier.padding(vertical = 10.dp)
    )
}
