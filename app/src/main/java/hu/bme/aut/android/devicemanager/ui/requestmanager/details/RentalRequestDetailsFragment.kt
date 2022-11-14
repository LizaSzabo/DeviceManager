package hu.bme.aut.android.devicemanager.ui.requestmanager.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.databinding.FragmentRequestDetailsBinding

@AndroidEntryPoint
class RentalRequestDetailsFragment :
    RainbowCakeFragment<RentalRequestDetailsViewState, RentalRequestDetailsViewModel>() {

    private lateinit var binding: FragmentRequestDetailsBinding
    override fun provideViewModel() = getViewModelFromFactory()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun render(viewState: RentalRequestDetailsViewState) {
        //  TODO("Not yet implemented")
    }
}