package hu.bme.aut.android.devicemanager.ui.requestmanager.takeback

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class TakeBackViewModel @Inject constructor(
    private val takeBackPresenter: TakeBackPresenter
) : RainbowCakeViewModel<TakeBackViewState>(Initial) {
}