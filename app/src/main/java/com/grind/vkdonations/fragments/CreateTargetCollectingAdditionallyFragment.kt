package com.grind.vkdonations.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.grind.vkdonations.R
import com.grind.vkdonations.dialogs.CalendarDialog
import com.grind.vkdonations.models.Collecting
import com.grind.vkdonations.models.CollectingTypes
import java.text.SimpleDateFormat
import java.util.*

class CreateTargetCollectingAdditionallyFragment(val collecting: Collecting) : Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var withoutDeadlineContainer: LinearLayout
    private lateinit var withDeadlineContainer: LinearLayout
    private lateinit var withoutDeadlineRadioButton: RadioButton
    private lateinit var withDeadlineRadioButton: RadioButton
    private lateinit var deadlineContainer: LinearLayout
    private lateinit var createCollectingButton: TextView
    private var deadline = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =
            View.inflate(context, R.layout.fragment_create_target_collecting_additionally, null)
        backButton = v.findViewById(R.id.imv_back_button)
        withoutDeadlineContainer = v.findViewById(R.id.ll_without_deadline_type)
        withDeadlineContainer = v.findViewById(R.id.ll_with_deadline_type)
        withoutDeadlineRadioButton = v.findViewById(R.id.rb_in_amount)
        withDeadlineRadioButton = v.findViewById(R.id.rb_in_date)
        deadlineContainer = v.findViewById(R.id.ll_deadline)
        createCollectingButton = v.findViewById(R.id.tv_create_collecting)


        initListeners()
        return v
    }

    private fun initListeners() {
        backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        withDeadlineContainer.setOnClickListener {
            withDeadlineRadioButton.callOnClick()
        }
        withoutDeadlineContainer.setOnClickListener {
            withoutDeadlineRadioButton.callOnClick()
        }
        withDeadlineRadioButton.setOnClickListener {
            withDeadlineRadioButton.isChecked = true
            withoutDeadlineRadioButton.isChecked = false
            deadlineContainer.visibility = View.VISIBLE
            (view?.findViewById(R.id.tv_deadline_text) as TextView).visibility = View.VISIBLE
            if (deadline == 0L) {
                createCollectingButton.isClickable = false
                createCollectingButton.background = ContextCompat.getDrawable(
                    context!!,
                    R.drawable.button_primary_press
                )
            } else {
                createCollectingButton.isClickable = true
                createCollectingButton.background = ContextCompat.getDrawable(
                    context!!,
                    R.drawable.button_primary_bg
                )
            }
        }
        withoutDeadlineRadioButton.setOnClickListener {
            withoutDeadlineRadioButton.isChecked = true
            withDeadlineRadioButton.isChecked = false
            deadlineContainer.visibility = View.GONE
            (view?.findViewById(R.id.tv_deadline_text) as TextView).visibility = View.GONE
            createCollectingButton.isClickable = true
            createCollectingButton.background = ContextCompat.getDrawable(
                context!!,
                R.drawable.button_primary_bg
            )
        }
        deadlineContainer.setOnClickListener {
            CalendarDialog(object : CalendarDialog.OnDeadlineSelectListener {
                override fun deadlineSelected(time: Long) {
                    deadline = time
                    val dateFormat = SimpleDateFormat("d MMMM")
                    (deadlineContainer.getChildAt(0) as TextView).text = dateFormat.format(
                        Date(
                            deadline
                        )
                    )
                    createCollectingButton.isClickable = true
                    createCollectingButton.background = ContextCompat.getDrawable(
                        context!!,
                        R.drawable.button_primary_bg
                    )
                }
            }).show(fragmentManager!!, "dialog")
        }

        createCollectingButton.setOnClickListener {
            if(withoutDeadlineRadioButton.isChecked){
                collecting.deadlineType = CollectingTypes.AMOUNT_DEADLINE_TYPE
            } else {
                collecting.deadlineType = CollectingTypes.DATE_DEADLINE_TYPE
                collecting.deadline = deadline
            }

            replaceFragment(SnippetPreviewFragment(collecting),true)
        }
    }
}