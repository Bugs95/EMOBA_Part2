package fhnw.emoba.modules.module05.flutter.data

import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import java.nio.charset.StandardCharsets
import java.util.*

class MqttConnector (val mqttBroker: String, val maintopic: String, val qos: MqttQos = MqttQos.EXACTLY_ONCE){

    //Verbindung zum Broker Client
    private val client = Mqtt5Client.builder()
        .serverHost(mqttBroker)
        .identifier(UUID.randomUUID().toString())
        .buildAsync()

    fun connectAndSubscribe(subtopic: String = "",
                            onNewMessage: (String) -> Unit = {},
                            onConnectionFailed: () -> Unit = {}){
        client.connectWith()
            .cleanStart(true)
            .keepAlive(30)
            .send()
            .whenComplete {_, throwable ->
                if(throwable != null){
                    onConnectionFailed.invoke()
                }else{
                    subscribe(subtopic, onNewMessage)
                }
            }
    }

    fun subscribe(subtopic: String = "#", onNewMessage: (String) -> Unit){
        client.subscribeWith()
            .topicFilter(maintopic+subtopic)
            .qos(qos)
            .noLocal(true)
            .callback{ onNewMessage.invoke(it.payloadAsString())}
            .send()
    }

    fun publish(message: String, subtopic: String = "", onPublished: () -> Unit = {}, onError: () -> Unit = {}){
        client.publishWith()
            .topic(maintopic + subtopic)
            .payload(message.asPayload())
            .qos(qos)
            .retain(false)
            .messageExpiryInterval(120)
            .send()
            .whenComplete{_, throwable ->
                if (throwable != null){
                    onError.invoke()
                }
                else{
                    onPublished.invoke()
                }
            }
    }
}

//praktische Extension Functions
private fun String.asPayload() : ByteArray = toByteArray(StandardCharsets.UTF_8)
private fun Mqtt5Publish.payloadAsString() : String = String(payloadAsBytes, StandardCharsets.UTF_8)