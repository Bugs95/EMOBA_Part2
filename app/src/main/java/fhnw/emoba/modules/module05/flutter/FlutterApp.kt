package fhnw.emoba.modules.module05.flutter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module05.flutter.model.FlutterModel
import fhnw.emoba.modules.module05.flutter.ui.FlutterUI

object FlutterApp: EmobaApp {
    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {

    }

    @Composable
    override fun createAppUI() {
        FlutterUI(FlutterModel)
    }

}