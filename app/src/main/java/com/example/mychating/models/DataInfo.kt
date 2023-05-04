package com.example.mychating.models

import com.squareup.moshi.Json

data class DataInfo(
    @Json(name = "data")
    var data: List<UsersInfo>,
    @Json(name = "message")
    var message: String,
    @Json(name = "status")
    var status: String,
) {
    data class UsersInfo(
        @Json(name = "id")
        var id: Long,
        @Json(name = "full_name")
        var full_name: String,
        @Json(name = "phone_number")
        var phone_number: String,
        @Json(name = "email")
        var email: String,
        @Json(name = "password")
        var password: String,
        @Json(name = "is_active")
        var is_active: Long,
        @Json(name = "image_url")
        var image_url: String,
        )
}
