package hu.bme.aut.android.devicemanager.ui.devicemanager.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentRentalRequestBinding

@AndroidEntryPoint
class RentalRequestFragment :
    RainbowCakeFragment<RentalRequestViewState, RentalRequestViewModel>() {

    private lateinit var binding: FragmentRentalRequestBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_rental_request

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRentalRequestBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRentalIntervalTv()
    }

    override fun render(viewState: RentalRequestViewState) {
        when (viewState) {
            is Initial -> {
                binding.loading.isVisible = false
            }
        }
    }

    private fun setupRentalIntervalTv() {
        binding.rentalInterval.setOnClickListener {
            findNavController().navigate(RentalRequestFragmentDirections.actionRentalRequestFragmentToCalendarFragment())
        }
    }
}