package hu.bme.aut.android.devicemanager.ui.devicemanager.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentDeviceDetailsBinding

@AndroidEntryPoint
class DeviceDetailsFragment :
    RainbowCakeFragment<DeviceDetailsViewState, DeviceDetailsViewModel>() {

    private lateinit var binding: FragmentDeviceDetailsBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_device_details

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun render(viewState: DeviceDetailsViewState) {
        //TODO("Not yet implemented")
    }
}