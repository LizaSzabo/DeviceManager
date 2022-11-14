package hu.bme.aut.android.devicemanager.ui.startmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.databinding.FragmentStartMenuBinding

@AndroidEntryPoint
class StartMenuFragment : RainbowCakeFragment<StartMenuViewState, StartMenuViewModel>() {

    private lateinit var binding: FragmentStartMenuBinding
    override fun provideViewModel() = getViewModelFromFactory()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun render(viewState: StartMenuViewState) {
        //TODO("Not yet implemented")
    }
}