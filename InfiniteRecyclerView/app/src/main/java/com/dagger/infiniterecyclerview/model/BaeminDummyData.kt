package com.dagger.infiniterecyclerview.model

import com.google.gson.annotations.SerializedName

data class BaeminDummyData(
    @SerializedName("data") var data: DummyData
)

data class DummyData(
    @SerializedName("content") var content: ArrayList<Content>?
)

data class Content(
    @SerializedName("title") var title: String?,
    @SerializedName("created") var created: String?
)
