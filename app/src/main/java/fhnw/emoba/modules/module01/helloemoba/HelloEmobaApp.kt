package fhnw.emoba.modules.module01.helloemoba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module01.helloemoba.ui.AppUI

/**
 * Von der App darf es nur eine Instanz geben.
 *
 * Das wird von Kotlin direkt unterstuetzt. Mit 'object'
 *
 * Alle unsere Apps implementieren das Interface 'EmobaApp' (damit sie in der 'MainActivity'
 * verwendet werden koennen'
 */
object HelloEmobaApp : EmobaApp {

    lateinit var message : String

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        message = "Guten Morgen Emoba"
    }

    @Composable
    override fun createAppUI() {
        AppUI(message)
    }

}

