package fhnw.emoba.modules.module06.phrasomat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module06.phrasomat.model.PhrasOMatModel
import fhnw.emoba.modules.module06.phrasomat.ui.PhrasOMatUI

object PhrasOMat  : EmobaApp {
    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {

    }

    @Composable
    override fun createAppUI() {
        PhrasOMatUI(PhrasOMatModel)
    }

}
