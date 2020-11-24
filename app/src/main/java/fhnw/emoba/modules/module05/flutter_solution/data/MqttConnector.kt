package fhnw.emoba.modules.module05.flutter_solution.data

import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*


/**
 * ACHTUNG: Das ist nur eine erste Konfiguration eines Mqtt-Brokers.
 *
 * Dient vor allem dazu mit den verschiedenen Parametern experimentieren zu können
 *
 * siehe die Doku:
 * https://hivemq.github.io/hivemq-mqtt-client/
 * https://github.com/hivemq/hivemq-mqtt-client
 *
 * Ein generischer Mqtt-Client (gut um Messages zu kontrollieren)
 * http://www.hivemq.com/demos/websocket-client/
 *
 */
class MqttConnector (val mqttBroker: String, val maintopic: String,
                     val qos: MqttQos = MqttQos.EXACTLY_ONCE){

    private val client = Mqtt5Client.builder()
        .serverHost(mqttBroker)
        .identifier(UUID.randomUUID().toString())
        .buildAsync()

    fun connectAndSubscribe(subtopic: String = "",
                            onNewMessage: (Flap) -> Unit,
                            onError: (exception: Exception, payload: String) -> Unit = {e, _ -> e.printStackTrace()},
                            onConnectionFailed: () -> Unit = {}) {
        client.connectWith()
            .cleanStart(true)
            .keepAlive(30)
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    onConnectionFailed.invoke()
                } else { //erst wenn die Connection aufgebaut ist, kann subscribed werden
                    subscribe(subtopic, onNewMessage, onError)
                }
            }
    }

    fun subscribe(subtopic: String = "#", onNewMessage: (Flap) -> Unit, onError: (exception: Exception, payload: String) -> Unit = {e, _ -> e.printStackTrace()}){
        client.subscribeWith()
            .topicFilter(maintopic + subtopic)
            .qos(qos)
            .noLocal(true)
            .callback {
                try {
                    onNewMessage.invoke(Flap(JSONObject(it.payloadAsString())))
                }
                catch (e: Exception){
                    onError.invoke(e, it.payloadAsString())
                }
            }
            .send()
    }

    fun publish(message: Flap, subtopic: String = "", onPublished: () -> Unit = {}, onError: () -> Unit = {}) {
        client.publishWith()
            .topic(maintopic + subtopic)
            .payload(message.asJSON().asPayload())
            .qos(qos)
            .retain(false)
            .messageExpiryInterval(60)
            .send()
            .whenComplete{_, throwable ->
                if(throwable != null){
                    onError.invoke()
                }
                else {
                    onPublished.invoke()
                }
            }
    }

    fun disconnect() {
        client.disconnectWith()
            .sessionExpiryInterval(0)
            .send()
    }
}

// praktische Extension Functions
private fun String.asPayload() : ByteArray = toByteArray(StandardCharsets.UTF_8)
private fun Mqtt5Publish.payloadAsString() : String = String(payloadAsBytes, StandardCharsets.UTF_8)