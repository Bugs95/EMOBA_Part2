package fhnw.emoba.modules.module06.phrasomat.model

import androidx.compose.runtime.mutableStateListOf
import kotlin.random.Random

object PhrasOMatModel {
    //todo 2: modelScope definieren
    val title = "PhrasOMat"


    //todo 2: in Data-Layer verschieben
    private val wordListOne  = listOf("verlässliche", "erfolgsorientierte", "webbasierte", "allumfassende",
                                      "clevere", "kundenorientierte", "pfadkritische", "dynamische",
                                      "konkurrenzfähige", "verteilte", "zielgerichtete")

    private val wordListTwo  = listOf("gepowerte", "haftende", "Mehrwert-", "zentrierte", "geclusterte",
                                      "proaktive", "Out-of-the-Box", "positionierte", "vernetzte",
                                      "fokussierte", "kraftvolle", "geordnete", "geteilte", "kooperative",
                                      "beschleunigte", "Multi-Tier-", "Enterprise-", "B2B-", "Frontend-")

    private val wordListThree = listOf("Schicht", "Endstufe", "Lösung", "Architektur", "Kernkompentenz",
                                       "Strategie", "Kooperation", "Ausrichtung", "Räumlichkeit", "Vision",
                                       "Dimension", "Mission")

    private fun generatePhrase(): String = wordListOne[Random.nextInt(1, wordListOne.size)] + " " +
            wordListTwo[Random.nextInt(1, wordListTwo.size)] + " " +
            wordListThree[Random.nextInt(1, wordListOne.size)] + " "

    val allPhrases = mutableStateListOf<String>()

    //todo : Funktion die neue Phrase einer Liste hinzufuegt
    fun newMarketingMessage(){
        allPhrases.add(generatePhrase())
    }

    //übung:
    //todo 2: PhrasenGenerator asnychron aufrufen

}