package fhnw.emoba.modules.module01.helloemoba.ui

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview


@Composable
fun AppUI(message: String = "Wow!"){
    Text(text = message, style = TextStyle(fontSize = 28.sp))
}


@Preview
@Composable
fun AppPreview(){
    AppUI()
}