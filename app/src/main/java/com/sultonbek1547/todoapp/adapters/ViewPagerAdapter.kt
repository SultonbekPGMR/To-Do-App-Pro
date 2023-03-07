package com.sultonbek1547.todoapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sultonbek1547.todoapp.databinding.RvLayoutBinding
import com.sultonbek1547.todoapp.model.MyToDo
import com.sultonbek1547.todoapp.utils.MyData.list

class ViewPagerAdapter( private val categoryNames: List<String>) :
    RecyclerView.Adapter<ViewPagerAdapter.Vh>() {


    inner class Vh(val rvLayoutBinding: RvLayoutBinding) : ViewHolder(rvLayoutBinding.root) {
        fun onBind(position: Int) {
            val tempList = ArrayList<MyToDo>()
            for (item in list) {
                if (item.holat.contains("$position")) {
                    tempList.add(item)
                }
            }
            rvLayoutBinding.myRv.adapter = RvAdapter(tempList)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.onBind(position)
    override fun getItemCount(): Int = categoryNames.size


}