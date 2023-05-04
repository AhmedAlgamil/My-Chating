package com.example.mychating.api

import com.example.mychating.Constants.BASE_URL
import com.example.mychating.models.DataInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RetrofitApiServices {

    @FormUrlEncoded
    @POST("user_table/table_Login.php")
    fun makeLogin(@Field("phone_number") phone_number : String,@Field("password") password : String,) : Call<DataInfo>

    @FormUrlEncoded
    @POST("user_table/table_insert.php")
    fun makeSignUp(
        @Field("full_name") full_name : String,
        @Field("phone_number") phone_number : String,
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("is_active") is_active : String,
        @Field("image_url") image_url : String,
    ) : Call<DataInfo>

    @GET("user_table/table_select.php")
    fun getAllUsers() : Call<DataInfo>
}

object ChatApiServices{

    val retrofitGetAllUsers: RetrofitApiServices by lazy {
        retrofit.create(RetrofitApiServices::class.java)
    }

}