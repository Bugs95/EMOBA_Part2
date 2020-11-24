package fhnw.emoba.modules.module07.gps.model

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import fhnw.emoba.modules.module07.gps.data.GPSConnector
import fhnw.emoba.modules.module07.gps.data.GeoPosition

class GpsModel(private val activity: Activity, private val locator: GPSConnector) {
    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val title    = "'Theseus der Klugscheisser' App"
    val subtitle = "Faden ist sooo 798 v.Chr., Ariadne!"

    val waypoints = mutableStateListOf<GeoPosition>()

    //wird mid modelScope.launch asynchron gemacht damits nicht blockiert
    fun rememberCurrentPosition(){
        modelScope.launch {
            locator.getLocation(onSuccess          = {  waypoints.add(it)
                                                        activity.toast("neuer Wegpunkt")},
                                onFailure          = {}, //todo: was machen wir?
                                onPermissionDenied = {activity.toast("Keine Berechtigung")}  //todo: was machen wir?
            )
        }
    }

    fun removeWaypoint(position: GeoPosition) {
        waypoints.remove(position)
    }

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