package com.grind.vkdonations.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationSet
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.grind.vkdonations.R
import com.grind.vkdonations.models.Collecting
import com.grind.vkdonations.models.CollectingTypes
import java.text.SimpleDateFormat
import java.util.*

class CollectingInfo(private val collecting: Collecting, private val deadlineText: String) : Fragment() {
    private lateinit var backButton: ImageView
    private lateinit var headerImage: ImageView
    private lateinit var title: TextView
    private lateinit var author: TextView
    private lateinit var deadlineInfo: TextView
    private lateinit var deadlineInfo2: TextView
    private lateinit var progressLine: View
    private lateinit var needAmount: TextView
    private lateinit var currentAmount: TextView
    private lateinit var description: TextView
    private lateinit var amountProgressText: TextView
    private lateinit var progressLine2: View
    private lateinit var helpButton: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_donation_info, null)
        backButton = v.findViewById(R.id.imv_back_button)
        headerImage = v.findViewById(R.id.imv_header)
        title = v.findViewById(R.id.tv_title)
        author = v.findViewById(R.id.tv_author)
        deadlineInfo = v.findViewById(R.id.tv_deadline_info)
        deadlineInfo2 = v.findViewById(R.id.tv_deadline_info_2)
        progressLine = v.findViewById(R.id.progress_green)
        needAmount = v.findViewById(R.id.tv_need_amount)
        currentAmount = v.findViewById(R.id.tv_current_amount)
        description = v.findViewById(R.id.tv_description)
        amountProgressText = v.findViewById(R.id.tv_progress)
        progressLine2 = v.findViewById(R.id.progress_line)
        helpButton = v.findViewById(R.id.tv_help_button)

        setInfo()
        animateProgress()

        backButton.setOnClickListener { fragmentManager?.popBackStack() }
        return v
    }


    private fun setInfo() {
        headerImage.setImageURI(Uri.parse(collecting.imageUri))
        title.text = collecting.title
        author.text = collecting.author
        deadlineInfo.text = deadlineText
        if (collecting.type == CollectingTypes.REGULAR_TYPE) {
            deadlineInfo2.text = "Нужно собрать в этом месяце"
        } else {
            if (collecting.deadlineType == CollectingTypes.DATE_DEADLINE_TYPE) {
                val format = SimpleDateFormat("d MMMM")
                deadlineInfo2.text = "Нужно собрать до ${format.format(Date(collecting.deadline))}"
            } else {
                deadlineInfo2.text = "Нужно собрать за все время"
            }
        }


        needAmount.text = "${collecting.needAmount} ₽"
        currentAmount.text = "${collecting.currentAmount} ₽"
        description.text = collecting.description
        amountProgressText.text =
            "Собрано ${collecting.currentAmount} ₽ из ${collecting.needAmount} ₽"

    }

    private fun animateProgress() {
        val finalScaleX = collecting.currentAmount.toFloat() / collecting.needAmount

        currentAmount.alpha = 0f
        currentAmount.translationX = finalScaleX * 1000
        val nA = ObjectAnimator.ofFloat(currentAmount, "alpha", 1f)
            .setDuration(500)

        progressLine.scaleX = 0f
        val progressAnimation = progressLine.animate()
            .scaleX(finalScaleX)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(1000)
            .start()

        progressLine2.scaleX = 0f
        val progress2Animation = progressLine2.animate()
            .scaleX(finalScaleX)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(1000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    nA.start()
                }
            }).start()


    }
}