package fhnw.emoba.modules.module07.photobooth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module07.photobooth.data.CameraAppConnector
import fhnw.emoba.modules.module07.photobooth.model.PhotoBooth
import fhnw.emoba.modules.module07.photobooth.ui.PhotoBootUI

object PhotoBoothApp : EmobaApp{
    lateinit var model: PhotoBooth

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        val cameraAppConnector = CameraAppConnector(activity)
        model = PhotoBooth(activity, cameraAppConnector)
    }

    @Composable
    override fun createAppUI() {
        PhotoBootUI(model)
    }

}