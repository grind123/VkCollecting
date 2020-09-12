package com.grind.vkdonations.models

import android.net.Uri
import java.io.Serializable

class Collecting {

    var type = 0
    var deadlineType = 0
    var imageUri: String? = null
    var title: String? = null
    var needAmount: Int = 0
    var currentAmount: Int = 0
    var deadline: Long = 0
    var target: String? = null
    var description: String? = null
    var author: String? = null
}