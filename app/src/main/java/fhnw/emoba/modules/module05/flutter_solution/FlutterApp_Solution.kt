package fhnw.emoba.modules.module05.mqtt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module05.flutter_solution.model.FlutterModel
import fhnw.emoba.modules.module05.flutter_solution.ui.FlutterUI

object FlutterApp_Solution : EmobaApp {

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        FlutterModel.connectAndSubscribe()

        FlutterModel.context = activity
    }

    @Composable
    override fun createAppUI() {
        FlutterUI(FlutterModel)
    }

}