package fhnw.emoba.modules.module06.startrek.model

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.R

object StarTrekModel {
    val title         = "Star-Trek App"
    val messageKirk   = "Beam us up, Scotty!"
    val reponseScotty = "Ay, Captain."

    var transporterStatus by mutableStateOf(0.0f)
    private set

    lateinit var context: Context

    private val soundPlayer by lazy { MediaPlayer.create(context, R.raw.transporter) }


    fun updateTransporterStatus(status: Float){
        if(transporterStatus == 0.0f && status > 0.0f && !soundPlayer.isPlaying){
            playSound()
        }
        transporterStatus = status
    }

    private fun playSound(){
        if(!soundPlayer.isPlaying){
            soundPlayer.start()
        }
    }
}