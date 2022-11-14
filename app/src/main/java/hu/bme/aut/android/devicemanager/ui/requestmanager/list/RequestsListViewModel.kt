package hu.bme.aut.android.devicemanager.ui.requestmanager.list

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RequestsListViewModel @Inject constructor(
    private val requestsListPresenter: RequestStatePresenter
) : RainbowCakeViewModel<RequestsListViewState>(Initial) {
}