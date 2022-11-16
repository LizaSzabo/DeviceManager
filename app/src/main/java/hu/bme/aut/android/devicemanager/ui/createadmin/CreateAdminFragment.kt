package hu.bme.aut.android.devicemanager.ui.createadmin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentCreateAdminBinding


@AndroidEntryPoint
class CreateAdminFragment : RainbowCakeFragment<CreateAdminViewState, CreateAdminViewModel>() {

    private lateinit var binding: FragmentCreateAdminBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_create_admin

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAdminBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun render(viewState: CreateAdminViewState) {
        //TODO("Not yet implemented")
    }


}