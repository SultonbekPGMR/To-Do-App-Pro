package com.sultonbek1547.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.sultonbek1547.todoapp.adapters.ViewPagerAdapter
import com.sultonbek1547.todoapp.databinding.ActivityMainBinding
import com.sultonbek1547.todoapp.model.MyToDo
import com.sultonbek1547.todoapp.retrofit.ApiClient
import com.sultonbek1547.todoapp.utils.MyData
import com.sultonbek1547.todoapp.utils.MyData.CATEGORY_NAMES
import com.sultonbek1547.todoapp.utils.MyData.isADDED
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var list: ArrayList<MyToDo>
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        ApiClient.getApiService().getAllToDo().enqueue(object : Callback<List<MyToDo>> {
            override fun onResponse(call: Call<List<MyToDo>>, response: Response<List<MyToDo>>) {
                list = response.body() as ArrayList<MyToDo>
                MyData.list = list
                viewPagerAdapter = ViewPagerAdapter(CATEGORY_NAMES)
                binding.myViewPager.adapter = viewPagerAdapter
                TabLayoutMediator(binding.myTabLayout, binding.myViewPager) { tab, position ->
                    tab.text = CATEGORY_NAMES[position]
                }.attach()
                binding.progressBar.visibility = View.GONE

            }

            override fun onFailure(call: Call<List<MyToDo>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "no internet!", Toast.LENGTH_SHORT).show()
            }
        })


        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))

        }
    }

    override fun onStart() {
        if (isADDED) {
            isADDED = false
            viewPagerAdapter = ViewPagerAdapter(CATEGORY_NAMES)
            binding.myViewPager.adapter = viewPagerAdapter

        }
        super.onStart()
    }


}