package fhnw.emoba.modules.module05.flutter.model

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.R
import fhnw.emoba.modules.module05.flutter.data.Flap
import fhnw.emoba.modules.module05.flutter.data.MqttConnector

object FlutterModel {
    val title = "FlutterApp";
    val mqttBroker = "broker.hivemq.com"
    val mainTopic = "/fhnw/emoba/flutterApp/"

    val allFlaps = mutableStateListOf<Flap>()
    var strangeMessage by mutableStateOf("")
    var flapsPublished by mutableStateOf(0)

    lateinit var context: Context
    private val mqttConnector by lazy { MqttConnector(mqttBroker, mainTopic) }
    private val soundPlayer by lazy {MediaPlayer.create(context, R.raw.whatsapp_web)}

    fun connectAndSubscribe(){
        mqttConnector.connectAndSubscribe(
            onNewMessage = {
                strangeMessage = ""
                allFlaps.add(it)
                playSound()
            },
            onError = {_, p ->
                strangeMessage = p
                playSound()
            }
        )
    }

    fun publish(){
        val newFlap = Flap(sender = "Geissen Peter", message = "Hallo Heidi ($flapsPublished)");
        mqttConnector.publish(  newFlap, onPublished = { flapsPublished++})
        allFlaps.add(newFlap)
    }

    private fun playSound(){
        if(!soundPlayer.isPlaying){
            soundPlayer.start()
        }
    }


}