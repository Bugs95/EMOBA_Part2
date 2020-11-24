package fhnw.emoba.modules.module06.lotto.data

import kotlin.random.Random

fun gewinnzahlenZiehen(): Ziehung {
    // todo: 6 unterschiedliche Gewinnzahlen ziehen und aufsteigend sortieren

    val gewinnZahlen = setOf<Int>().toSortedSet()
    while (gewinnZahlen.size < 6) {
        gewinnZahlen.add(naechsteKugel())
    }

    // todo: Superzahl ziehen, darauf achten, dass die Superzahl nicht in der Ziehung enthalten ist


    //todo: emptyList und 42 ersetzen
    return Ziehung(emptyList(), 42)
}

//liefert eine Zahl von 1..49
private fun naechsteKugel() = Random.nextInt(from = 1, until = 50)