package fhnw.emoba.modules.module07.photobooth.model

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.view.Gravity
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.modules.module07.photobooth.data.CameraAppConnector

class PhotoBooth(private val activity: Activity,
                 private val cameraAppConnector: CameraAppConnector) {
    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val title = "FOTO.FIX"

    var photo by mutableStateOf<Bitmap?>(null)

    fun takePhoto() {
        cameraAppConnector.getBitmap(onSuccess  = { photo = it },
                                     onCanceled = { activity.toast("Kein neues Bild")})  //todo: was soll dann passieren? - done
    }

    fun rotatePhoto() {
        photo?.let { modelScope.launch { photo = photo!!.rotate(90f) } }
    }
}

private fun Bitmap.rotate(degrees: Float) : Bitmap {
    val matrix = Matrix().apply {
        postRotate(degrees)
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}


/**
 * Das zeigt blendet eine Nachricht auf dem Bildschirm ein. Das passiert "ausserhalb"
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

