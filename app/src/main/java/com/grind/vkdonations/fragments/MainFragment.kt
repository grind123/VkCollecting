package com.grind.vkdonations.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.grind.vkdonations.R
import kotlinx.android.synthetic.main.fragment_main.*

fun Fragment.replaceFragment(fragment: Fragment, addToBackStack: Boolean){
    val transaction = fragmentManager?.beginTransaction()
    transaction?.replace(R.id.main_container, fragment)
    transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    if(addToBackStack) transaction?.addToBackStack(fragment::class.java.simpleName)
    transaction?.commit()

}

class MainFragment: Fragment() {

    private lateinit var createButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_main, null)
        createButton = v.findViewById(R.id.tv_create_collecting)
        createButton.setOnClickListener {
            replaceFragment(ChooseTypeCollectingFragment(), true)
        }
        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.finish()
    }
}