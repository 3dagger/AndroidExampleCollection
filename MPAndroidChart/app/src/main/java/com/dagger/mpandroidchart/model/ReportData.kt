package com.dagger.mpandroidchart.model

import com.google.gson.annotations.SerializedName

data class ReportData(
    @SerializedName("socReport") var socReport: Map<String, Int>,
    @SerializedName("countReport") var countReport: Map<String, Int>)
