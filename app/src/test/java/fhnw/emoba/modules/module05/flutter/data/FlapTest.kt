package fhnw.emoba.modules.module05.flutter.data

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FlapTest {

    @Test
    fun testAll() {


        //given
        val sender  = "Grossvater"
        val message = "Wo ist das Heidi?"
        val t1 = Flap(sender, message)

        //when
        val json = JSONObject(t1.asJSON())
        val t2   = Flap(json)

        //then
        assertEquals(sender,  json.getString("sender"))
        assertEquals(message, json.getString("message"))
        assertEquals(t1, t2)



    }

}