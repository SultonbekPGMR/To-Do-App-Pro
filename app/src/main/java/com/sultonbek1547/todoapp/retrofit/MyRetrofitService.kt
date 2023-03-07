package com.sultonbek1547.todoapp.retrofit

import com.sultonbek1547.todoapp.model.MyToDo
import com.sultonbek1547.todoapp.model.PostMyToDo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MyRetrofitService {

    @GET("plan")
    fun getAllToDo(): Call<List<MyToDo>>

    @POST("plan/")
    fun addToDo(@Body postMyToDo: PostMyToDo): Call<MyToDo>

    @DELETE("plan/{id}/")
    fun deleteToDo(@Path("id") id: Int):Call<ResponseBody>
}