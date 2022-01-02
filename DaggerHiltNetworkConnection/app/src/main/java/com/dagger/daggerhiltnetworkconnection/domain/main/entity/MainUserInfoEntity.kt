package com.dagger.daggerhiltnetworkconnection.domain.main.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainUserInfoEntity(
    @field:Json(name = "login") val login: String?,
    @field:Json(name = "id") var id: Int,
    @field:Json(name = "url") var url: String?,
    @field:Json(name = "avatar_url") var avatarUrl: String?,
    @field:Json(name = "html_url") var htmlUrl: String?,
    @field:Json(name = "name") var name: String?
)
