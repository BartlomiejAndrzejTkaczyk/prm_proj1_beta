package pl.edu.pjwstk.s22517.prm_proj1.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.TimeNeeder
import java.time.LocalDate
import java.util.Calendar


class DatePicker : DialogFragment(), DatePickerDialog.OnDateSetListener {
    var timeNeedFragment: TimeNeeder? = null


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        timeNeedFragment?.sendTime(LocalDate.of(year, month, day))
    }

}