package hu.bme.aut.android.devicemanager.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentCalendarBinding
import java.util.*

@AndroidEntryPoint
class CalendarFragment : RainbowCakeFragment<CalendarViewState, CalendarViewModel>() {

    private lateinit var binding: FragmentCalendarBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_NONE

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(2017, 11, 14)

        val calendar1: Calendar = Calendar.getInstance()
        calendar1.set(2017, 11, 16)

        binding.calendarView.setDateSelected(CalendarDay.today(), true)

    }

    override fun render(viewState: CalendarViewState) {
        //TODO("Not yet implemented")
    }
}