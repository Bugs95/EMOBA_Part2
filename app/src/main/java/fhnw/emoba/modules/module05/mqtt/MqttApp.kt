package fhnw.emoba.modules.module05.mqtt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module05.mqtt.model.MqttModel
import fhnw.emoba.modules.module05.mqtt.ui.MqttUI

object MqttApp : EmobaApp {

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        MqttModel.connectAndSubscribe()
    }
    
    @Composable
    override fun createAppUI() {
        MqttUI(MqttModel)
    }
}