package com.sultonbek1547.todoapp

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sultonbek1547.todoapp.databinding.ActivityAddBinding
import com.sultonbek1547.todoapp.model.MyToDo
import com.sultonbek1547.todoapp.model.PostMyToDo
import com.sultonbek1547.todoapp.retrofit.ApiClient
import com.sultonbek1547.todoapp.utils.MyData.CATEGORY_NAMES
import com.sultonbek1547.todoapp.utils.MyData.PRIORITY_NAMES
import com.sultonbek1547.todoapp.utils.MyData.isADDED
import com.sultonbek1547.todoapp.utils.MyData.list
import com.sultonbek1547.todoapp.utils.MyData.myToDo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private val myCalendar: Calendar = Calendar.getInstance()
    private var dateState = false
    private var categoryPosition = 0
    private var priorityPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val date = DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
            myCalendar.set(Calendar.YEAR, p1)
            myCalendar.set(Calendar.MONTH, p2)
            myCalendar.set(Calendar.DAY_OF_MONTH, p3)
            dateState = true
            val myFormat = "dd/MM/yyyy"
            val dateFormat = SimpleDateFormat(myFormat, Locale.US)
            binding.edtDate.setText(dateFormat.format(myCalendar.time))
        }
        binding.apply {

            /** Categories spinner */
            categorySpinner.adapter = ArrayAdapter<String>(
                this@AddActivity,
                R.layout.spinner_item,
                CATEGORY_NAMES
            )
            categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    categoryPosition = p2
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

            /** Priority spinner */
            prioritySpinner.adapter = ArrayAdapter<String>(
                this@AddActivity,
                R.layout.spinner_item,
                PRIORITY_NAMES
            )
            prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    priorityPosition = p2
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }




            edtDate.setOnClickListener {
                val dialogDate = DatePickerDialog(
                    this@AddActivity,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                )
                dialogDate.show()

            }

            /**adding*/
            var taskName = ""
            btnSave.setOnClickListener {
                taskName = edtName.text.toString().trim();
                if (taskName.isNotEmpty() && dateState) {
                    ApiClient.getApiService().addToDo(
                        PostMyToDo(
                            taskName,
                            categoryPosition.toString(),
                            "77_77${PRIORITY_NAMES[priorityPosition]}",
                            edtDate.text.toString()
                        )
                    ).enqueue(object : Callback<MyToDo> {
                        override fun onResponse(call: Call<MyToDo>, response: Response<MyToDo>) {
                            if (response.isSuccessful){
                                isADDED = true
                                myToDo = response.body()!!
                                list.add(myToDo)
                                Toast.makeText(this@AddActivity, "Saved", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<MyToDo>, t: Throwable) {

                        }
                    })

                }


            }


        }


    }
}