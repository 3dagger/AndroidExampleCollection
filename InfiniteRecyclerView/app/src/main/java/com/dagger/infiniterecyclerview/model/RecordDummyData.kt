package com.dagger.infiniterecyclerview.model

import com.google.gson.annotations.SerializedName

data class RecordDummyData(
    @SerializedName("history") var history: List<History>
)

data class History(
    @SerializedName("resultDate") var resultDate: String,
    @SerializedName("rentList") var rentList: List<RentList>
)

data class RentList(
    @SerializedName("title") var title: String,
    @SerializedName("createDate") var createDate: String,
    @SerializedName("mileage") var mileage: String,
    @SerializedName("leftMileage") var leftMileage: String
)
