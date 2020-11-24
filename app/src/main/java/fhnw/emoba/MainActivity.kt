package fhnw.emoba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import fhnw.emoba.modules.module01.helloemoba.HelloEmobaApp
import fhnw.emoba.modules.module05.flutter.FlutterApp
import fhnw.emoba.modules.module05.mqtt.FlutterApp_Solution
import fhnw.emoba.modules.module05.mqtt.MqttApp
import fhnw.emoba.modules.module06.phrasomat.PhrasOMat
import fhnw.emoba.modules.module06.startrek.StarTrekApp
import fhnw.emoba.modules.module07.fileio.FileIOApp
import fhnw.emoba.modules.module07.gps.GpsApp
import fhnw.emoba.modules.module07.photobooth.PhotoBoothApp


/**
 * Diese Activity wird zum Starten von allen im Unterricht entwickelten Apps verwendet.
 *
 * Eine Activity ist notwendig. Alles weitere soll in der EmobaApp implementiert werden mit möglichst
 * wenig Verwendung der Activity.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var app: EmobaApp  //alle Beispiele implementieren das Interface EmobaApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ToDo: hier die emoba App eintragen, die gestartet werden soll
        //app = HelloEmobaApp
        //app = MqttApp
        app = FlutterApp
        //app = FlutterApp_Solution
        //app = StarTrekApp
        //app = PhrasOMat
        //app = GpsApp
        //app = PhotoBoothApp
        //app = FileIOApp

        app.initialize(activity = this, savedInstanceState = savedInstanceState)

        setContent {
            app.createAppUI()
        }
    }

    /**
     * Eine der Activity-LiveCycle-Methoden. Im Laufe des Semesters werden weitere benötigt
     * werden. Auch die leiten den Aufruf lediglich an die EmobaApp weiter.
     */
    override fun onStop() {
        super.onStop()
        app.onStop(activity = this)
    }
}

