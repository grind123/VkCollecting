package com.grind.vkdonations.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.grind.vkdonations.R
import com.grind.vkdonations.models.Collecting
import com.grind.vkdonations.models.CollectingTypes

class CreateTargetCollectingFragment: Fragment() {
    private lateinit var backButton: ImageView
    private lateinit var imageContainer: ViewGroup
    private lateinit var cardViewWithImage: CardView
    private lateinit var deleteImageButton: ImageView
    private lateinit var image: ImageView
    private lateinit var collectingName: EditText
    private lateinit var amount: EditText
    private lateinit var target: EditText
    private lateinit var description: EditText
    private lateinit var nextButton: TextView
    private lateinit var loadImageText: LinearLayout

    private val collecting = Collecting()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_create_target_collecting, null)
        backButton = v.findViewById(R.id.imv_back_button)
        imageContainer = v.findViewById(R.id.fl_image_container)
        cardViewWithImage = v.findViewById(R.id.cv_cover)
        deleteImageButton = v.findViewById(R.id.imv_delete_image)
        image = v.findViewById(R.id.imv_main_image)
        collectingName = v.findViewById(R.id.et_name)
        amount = v.findViewById(R.id.et_amount)
        target = v.findViewById(R.id.et_target)
        description = v.findViewById(R.id.et_description)
        nextButton = v.findViewById(R.id.tv_next_button)
        loadImageText = v.findViewById(R.id.ll_load_container)

        collecting.type = CollectingTypes.TARGET_TYPE
        initListeners()
        return v
    }

    private fun initListeners(){
        backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        deleteImageButton.setOnClickListener {
            image.visibility = View.GONE
            cardViewWithImage.visibility = View.GONE
            deleteImageButton.visibility = View.GONE
            loadImageText.visibility = View.VISIBLE
        }
        imageContainer.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Выбор изображения"), 101)
        }
        nextButton.setOnClickListener {
            if(collecting.imageUri == null){
                Toast.makeText(context, "Необходимо загрузить обложку", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!collectingName.text.isNullOrBlank()) {
                collecting.title = collectingName.text.toString()
            }
            else {
                needFillInAllFieldsMessage()
                return@setOnClickListener
            }
            if(!amount.text.isNullOrBlank()) {
                collecting.needAmount = amount.text.toString().also {
                    it.replace(" ", "")
                    it.dropLast(1)
                }.toInt()
            }
            else {
                needFillInAllFieldsMessage()
                return@setOnClickListener
            }
            if(!target.text.isNullOrBlank()) {
                collecting.target = target.text.toString()
            }
            else {
                needFillInAllFieldsMessage()
                return@setOnClickListener
            }

            if(!description.text.isNullOrBlank()) {
                collecting.description = description.text.toString()
            }
            else {
                needFillInAllFieldsMessage()
                return@setOnClickListener
            }
            collecting.author = "Алексей Хоменко"

            replaceFragment(CreateTargetCollectingAdditionallyFragment(collecting), true)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 101 && resultCode == Activity.RESULT_OK){
            collecting.imageUri = data!!.data.toString()
            image.setImageURI(data.data)
            image.visibility = View.VISIBLE
            cardViewWithImage.visibility = View.VISIBLE
            deleteImageButton.visibility = View.VISIBLE
            loadImageText.visibility = View.GONE
        }
    }

    private fun needFillInAllFieldsMessage(){
        Toast.makeText(context, "Необходимо заполнить все поля", Toast.LENGTH_SHORT).show()
    }
}