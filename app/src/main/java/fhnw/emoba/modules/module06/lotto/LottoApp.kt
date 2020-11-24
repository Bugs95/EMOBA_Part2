package fhnw.emoba.modules.module06.lotto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module06.lotto.model.LottoModel
import fhnw.emoba.modules.module06.lotto.ui.LottoUI

object LottoApp : EmobaApp{

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
    }

    @Composable
    override fun createAppUI() {
        LottoUI(LottoModel)
    }
}





