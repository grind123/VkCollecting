package com.grind.vkdonations.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.grind.vkdonations.R
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_type_of_collecting.*

class ChooseTypeCollectingFragment : Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var targetCollecting: View
    private lateinit var regularCollecting: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_type_of_collecting, null)
        backButton = v.findViewById(R.id.imv_back_button)
        targetCollecting = v.findViewById(R.id.ll_target_donation_container)
        regularCollecting = v.findViewById(R.id.ll_regular_donation_container)
        backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        targetCollecting.setOnClickListener {

        }

        regularCollecting.setOnClickListener {

        }

        return v
    }

}