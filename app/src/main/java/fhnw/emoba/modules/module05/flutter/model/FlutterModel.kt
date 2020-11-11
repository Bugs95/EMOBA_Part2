package fhnw.emoba.modules.module05.flutter.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.modules.module05.flutter.data.MqttConnector

object FlutterModel {
    val title = "FlutterApp";

    val mqttBroker = "broker.hivemq.com"
    val mainTopic = "/fhnw/emoba/flutterApp/"
    val subTopic = "Bugs95/"

    val mqttConnector by lazy { MqttConnector(mqttBroker, mainTopic) }

    var messagesReceived by mutableStateOf(0)
    var messagesPublished by mutableStateOf(0)

    fun connectAndSubscribe(){
        mqttConnector.connectAndSubscribe (subtopic = subTopic, onNewMessage = {messagesReceived++} )
    }

    fun publish(){
        mqttConnector.publish("This is my Flutter", onPublished = { messagesPublished++})
    }

}