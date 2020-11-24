package fhnw.emoba.modules.module07.fileio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module07.fileio.model.FileIOModel
import fhnw.emoba.modules.module07.fileio.ui.FileIOUI

object FileIOApp : EmobaApp {
    lateinit var model : FileIOModel

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        model = FileIOModel(activity)
    }

    @Composable
    override fun createAppUI() {
        FileIOUI(model)
    }
}