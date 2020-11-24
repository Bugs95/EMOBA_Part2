package fhnw.emoba.modules.module07.fileio.model

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.Gravity
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.R
import fhnw.emoba.modules.module07.fileio.data.downloadBitmapFromFileIO
import fhnw.emoba.modules.module07.fileio.data.uploadBitmapToFileIO

class FileIOModel(val activity: Activity) {
    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val title = "file.io App"
    var currentTab by mutableStateOf(HomeScreenTab.UPLOAD)

    // ausnahmsweise mal nicht asynchron
    val originalCrew by lazy { BitmapFactory.decodeResource(activity.resources, R.drawable.star_trek_crew) }

    var fileioURL          by mutableStateOf<String?>(null)
    var uploadInProgress   by mutableStateOf(false)

    var downloadedCrew     by mutableStateOf<Bitmap?>(null)
    var downloadInProgress by mutableStateOf(false)
    var downloadMessage    by mutableStateOf("")

    fun uploadToFileIO() {
        uploadInProgress = true
        fileioURL = null
        modelScope.launch {
            uploadBitmapToFileIO(bitmap    = originalCrew,
                                 onSuccess = { fileioURL = it},
                                 onError   = {_, _ -> })  //todo: was machen wir denn nun?
            uploadInProgress = false
        }
    }

    fun downloadFromFileIO(){
        if(fileioURL != null){
            downloadedCrew = null
            downloadInProgress = true
            modelScope.launch {
                downloadBitmapFromFileIO(url       = fileioURL!!,
                                         onSuccess = { downloadedCrew = it; activity.toast("Download successful")},
                                         onDeleted = {  downloadMessage = "File is deleted"; activity.toast(downloadMessage)},    // todo: Stellen Sie hier auf toast um
                                         onError   = { downloadMessage = "Connection failed"; activity.toast(downloadMessage)})  // raeumen Sie die Properties weg, die nicht mehr vom UI verwertet werden
                downloadInProgress = false
            }
        }
    }
}

/**
 * Das blendet eine Nachricht auf dem Bildschirm ein. Das passiert "ausserhalb"
 * von Jetpack Compose.
 *
 * Wir haben also einen Widerspruch zu unserer bisherigen Architektur-Regel, dass das
 * ViewModel keine Visualisierung macht.
 *
 * In diesem Fall sind die Implementierungsalternativen jedoch so umstaendlich, dass
 * eine Aenderung der Regeln sinnvoll erscheint.
 */
private fun Context.toast(message: String) {
    if (message.isNotBlank()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }
}