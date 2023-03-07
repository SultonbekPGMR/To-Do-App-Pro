package com.sultonbek1547.todoapp.utils

import com.sultonbek1547.todoapp.model.MyToDo

object MyData {
    val CATEGORY_NAMES = arrayListOf<String>("Personal Needs", "Work", "Payments", "Education")
    val PRIORITY_NAMES = arrayListOf<String>("Low", "Medium", "High")

    var isADDED = false
    var myToDo = MyToDo("", 2, "", "", "")
    var list  = ArrayList<MyToDo>()

}