package fhnw.emoba.modules.module05.mqtt.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.modules.module05.mqtt.data.MqttConnector

object MqttModel {
    val title = "MqttApp"

    val mqttBroker    = "broker.hivemq.com"
    val mainTopic     = "/fhnw/emoba/mqttplayground/"
    val subTopic = "Bugs95/"
    val mqttConnector by lazy { MqttConnector(mqttBroker, mainTopic) }

    var messagesReceived  by mutableStateOf(0)
    var messagesPublished by mutableStateOf(0)

    fun connectAndSubscribe(){
        mqttConnector.connectAndSubscribe(subtopic = subTopic, onNewMessage = { messagesReceived++})
    }
    
    fun publish(){
        mqttConnector.publish("hello", onPublished = { messagesPublished++})
    }
    
}