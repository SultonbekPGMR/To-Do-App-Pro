package com.sultonbek1547.todoapp.adapters

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sultonbek1547.todoapp.R
import com.sultonbek1547.todoapp.databinding.RvItemBinding
import com.sultonbek1547.todoapp.model.MyToDo
import com.sultonbek1547.todoapp.retrofit.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RvAdapter(private val list: ArrayList<MyToDo>) :
    RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: RvItemBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(myToDo: MyToDo, position: Int) {
            itemRvBinding.apply {
                tvText.text = myToDo.sarlavha
                tvDate.text = myToDo.oxirgi_muddat
                tvPriority.text = myToDo.matn.substring(5)
                when (myToDo.matn.substring(5)) {
                    "Medium" -> tvPriority.setBackgroundResource(R.drawable.txt_back_med)
                    "High" -> tvPriority.setBackgroundResource(R.drawable.txt_back_high)
                }


                itemRvBinding.root.setOnLongClickListener {
                    itemRvBinding.btnCheck.isChecked = true
                    list.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(0, itemCount)
                    ApiClient.getApiService().deleteToDo(myToDo.id).enqueue(
                        object : Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>,
                            ) {}

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                list.add(position,myToDo)
                                notifyItemInserted(position)
                                notifyItemRangeChanged(0,itemCount)
                            }
                        }
                    )
                    true
                }
                itemRvBinding.btnCheck.setOnCheckedChangeListener { p0, p1 ->
                    if (p1) {
                        itemRvBinding.tvText.paintFlags =
                            itemRvBinding.tvText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        itemRvBinding.tvText.paintFlags =
                            itemRvBinding.tvText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                    }
                }


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) =
        holder.onBind(list[position], position)


    override fun getItemCount(): Int = list.size


}