package com.grind.vkdonations.dialogs

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.grind.vkdonations.R
import java.util.*

class CalendarDialog(private val listenerOn: OnDeadlineSelectListener): DialogFragment() {
    private lateinit var calendarView: CalendarView
    private lateinit var selectButton: TextView
    private lateinit var cancelButton: TextView
    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("Moscow"))

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = View.inflate(context, R.layout.dialog_calendar, null)
        calendarView = v.findViewById(R.id.calendar_view)
        selectButton = v.findViewById(R.id.tv_ok)
        cancelButton = v.findViewById(R.id.tv_cancel)
        calendarView.setDate(calendar.timeInMillis, true, false)
        calendarView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, day: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
            }
        })

        selectButton.setOnClickListener {
            if(System.currentTimeMillis() < calendar.timeInMillis){
                listenerOn.deadlineSelected(calendar.timeInMillis)
                dismiss()
            } else {
                Toast.makeText(context!!, "Некорректная дата", Toast.LENGTH_SHORT).show()
            }

        }
        cancelButton.setOnClickListener {
            dismiss()
        }

        val dialog = AlertDialog.Builder(context!!)
            .setView(v)
            .create()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        return dialog
    }

    interface OnDeadlineSelectListener{
        fun deadlineSelected(time: Long)
    }
}