package fhnw.emoba.modules.module06.startrek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module06.startrek.model.StarTrekModel
import fhnw.emoba.modules.module06.startrek.ui.StarTrekUI

object StarTrekApp  : EmobaApp {

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        StarTrekModel.context = activity
    }

    @Composable
    override fun createAppUI() {
        StarTrekUI(StarTrekModel)
    }

}