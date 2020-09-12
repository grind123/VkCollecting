package com.grind.vkdonations.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.grind.vkdonations.R
import com.grind.vkdonations.models.CollectingTypes
import com.grind.vkdonations.models.Post

class FeedFragment(private val post: Post) : Fragment() {
    private lateinit var snippetContainer: CardView
    private lateinit var message: TextView
    private lateinit var mainImage: ImageView
    private lateinit var title: TextView
    private lateinit var desc: TextView
    private lateinit var progressText: TextView
    private lateinit var progressLine: View

    private var deadlineText = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_feed, null)
        snippetContainer = v.findViewById(R.id.cv_snippet_container)
        message = v.findViewById(R.id.tv_message)
        mainImage = v.findViewById(R.id.imv_main_image)
        title = v.findViewById(R.id.tv_donation_title)
        desc = v.findViewById(R.id.tv_donation_desc)
        progressText = v.findViewById(R.id.tv_progress)
        progressLine = v.findViewById(R.id.progress_line)

        setDataToViews()
        initListeners()
        return v
    }

    private fun initListeners(){
        snippetContainer.setOnClickListener {
            replaceFragment(CollectingInfo(post.collecting, deadlineText), true)
        }
    }

    private fun setDataToViews() {
        val collecting = post.collecting
        if (post.message != null && !post.message.isNullOrBlank()) {
            message.text = post.message
        } else {
            message.visibility = View.GONE
        }
        mainImage.setImageURI(Uri.parse(collecting.imageUri))
        title.text = collecting.title

        if (collecting.type == CollectingTypes.REGULAR_TYPE) {
            deadlineText = "Помощь нужна каждый месяц"
        } else {
            if (collecting.deadlineType == CollectingTypes.AMOUNT_DEADLINE_TYPE) {
                deadlineText = "Закончится когда соберется сумма"
            } else {
                val daysDiff =
                    (collecting.deadline - System.currentTimeMillis()) / 1000 / 60 / 60 / 24 % 365
                if (daysDiff > 0) {
                    deadlineText = "Закончится через $daysDiff дней"
                } else {
                    deadlineText = "Закончится сегодня"
                }
            }
        }
        desc.text = "${collecting.author} · $deadlineText"

        progressText.text =
            "Собрано ${post.collecting.currentAmount} ₽ из ${post.collecting.needAmount} ₽"
        progressLine.scaleX = post.collecting.currentAmount.toFloat() / post.collecting.needAmount
    }

    override fun onDestroy() {
        fragmentManager?.popBackStack(MainFragment::class.java.simpleName, 0)
        super.onDestroy()
    }

}