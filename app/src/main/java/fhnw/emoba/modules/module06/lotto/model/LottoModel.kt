package fhnw.emoba.modules.module06.lotto.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.modules.module06.lotto.data.Ziehung
import fhnw.emoba.modules.module06.lotto.data.gewinnzahlenZiehen

object LottoModel {
    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val title = "Lotto 6 aus 49"

    // todo: ersetzen durch geeignete Variante um die Liste von allen Ziehungen zu verwalten
    val alleZiehungen = listOf<Ziehung>()

    // todo: ersetzen durch geeignete Variante um die Liste aller Kaestchen des aktuellen Lottoscheins zu verwalten
    var lottoschein = listOf<Kaestchen>()


    fun neuerLottoschein(anzahlKaestchen: Int){
        // auch wenn es diesem Beispiel kein Performanceproblem gibt:
        // "Datenservices" ruft man asynchron auf
        modelScope.launch {
            //todo: mittels 'gewinnzahlenZiehen()'( siehe 'Ziehungsgeraet') eine Liste
            //von Kaestchen erzeugen und 'lottoschein' zuweisen

        }
    }

    fun neueZiehung(){
        modelScope.launch {
            //todo: neue Gewinnzahlen ziehen (siehe 'Ziehungsgeraet.gewinnzahlenziehen')
            // und 'alleZiehungen' hinzufuegen


            //todo:
            // bei allen Kaestchen des aktuellen Lottoscheins das ergebnis setzen
        }
    }
}