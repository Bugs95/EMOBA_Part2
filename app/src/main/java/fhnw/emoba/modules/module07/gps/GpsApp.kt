package fhnw.emoba.modules.module07.gps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module07.gps.data.GPSConnector
import fhnw.emoba.modules.module07.gps.model.GpsModel
import fhnw.emoba.modules.module07.gps.ui.GpsUI

object GpsApp : EmobaApp {
    lateinit var model: GpsModel

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        val gps = GPSConnector(activity)
        model = GpsModel(activity, gps)
    }

    @Composable
    override fun createAppUI() {
        GpsUI(model)
    }
}