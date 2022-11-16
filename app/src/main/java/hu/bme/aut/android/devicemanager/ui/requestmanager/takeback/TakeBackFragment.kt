package hu.bme.aut.android.devicemanager.ui.requestmanager.takeback

import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.databinding.FragmentTakeBackBinding

@AndroidEntryPoint
class TakeBackFragment : RainbowCakeFragment<TakeBackViewState, TakeBackViewModel>() {

    private lateinit var binding: FragmentTakeBackBinding
    override fun provideViewModel() = getViewModelFromFactory()


    override fun render(viewState: TakeBackViewState) {
        //  TODO("Not yet implemented")
    }


}