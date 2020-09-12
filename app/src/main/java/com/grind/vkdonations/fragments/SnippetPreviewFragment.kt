package com.grind.vkdonations.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.grind.vkdonations.R
import com.grind.vkdonations.models.Collecting
import com.grind.vkdonations.models.CollectingTypes
import com.grind.vkdonations.models.Post
import kotlin.random.Random

class SnippetPreviewFragment(val collecting: Collecting): Fragment() {

    private lateinit var closeButton: ImageView
    private lateinit var uploadButton: ImageView
    private lateinit var message: EditText
    private lateinit var mainImage: ImageView
    private lateinit var title: TextView
    private lateinit var desc: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_snippet_preview, null)
        closeButton = v.findViewById(R.id.imv_dismiss)
        uploadButton = v.findViewById(R.id.imv_upload)
        message = v.findViewById(R.id.et_message)
        mainImage = v.findViewById(R.id.imv_main_image)
        title = v.findViewById(R.id.tv_donation_title)
        desc = v.findViewById(R.id.tv_donation_desc)

        setDataToViews()
        initListeners()

        message.requestFocus()
        return v
    }

    private fun setDataToViews(){
        mainImage.setImageURI(Uri.parse(collecting.imageUri))
        title.text = collecting.title
        var deadline = ""
        if(collecting.type == CollectingTypes.REGULAR_TYPE){
            deadline = "Помощь нужна каждый месяц"
        } else {
            if(collecting.deadlineType == CollectingTypes.AMOUNT_DEADLINE_TYPE){
                deadline = "Закончится когда соберется сумма"
            } else {
                val daysDiff = (collecting.deadline - System.currentTimeMillis()) / 1000/ 60 / 60 / 24 % 365
                deadline = "Закончится через $daysDiff дней"
            }
        }
        desc.text = "${collecting.author} · $deadline"
    }

    private fun initListeners(){
        closeButton.setOnClickListener {
            fragmentManager?.popBackStackImmediate(MainFragment::class.java.simpleName, 0)
        }

        uploadButton.setOnClickListener {
            collecting.currentAmount = Random.nextInt((collecting.needAmount * 0.3).toInt(), (collecting.needAmount * 0.7).toInt())
            replaceFragment(FeedFragment(Post(collecting, message.text.toString())), true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}