package fhnw.emoba.modules.module05.flutter_solution.data

import org.json.JSONObject


data class Flap(val sender: String, val message: String) {

    constructor(json : JSONObject): this(json.getString("sender"), json.getString("message"))

    fun asJSON(): String {
        return "{ \"sender\": \"$sender\", " +
                "\"message\": \"$message\" " +
                "}"
    }
}