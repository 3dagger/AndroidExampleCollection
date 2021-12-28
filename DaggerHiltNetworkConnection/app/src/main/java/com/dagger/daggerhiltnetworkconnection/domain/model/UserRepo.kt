package com.dagger.daggerhiltnetworkconnection.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRepo(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "id") val id: Long,
//    @field:Json(name = "date") val date: String,
    @field:Json(name = "url") var url: String
)
