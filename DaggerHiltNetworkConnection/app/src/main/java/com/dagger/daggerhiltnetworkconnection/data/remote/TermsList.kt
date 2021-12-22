package com.dagger.daggerhiltnetworkconnection.data.remote

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity
@JsonClass(generateAdapter = true)
data class TermsList(
    @field:Json(name = "termsId") @PrimaryKey val termsId: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "content") val content: String,
    @field:Json(name = "isRequired") val isRequired: Boolean
){
    fun getIdString(): String = termsId
}
//{

//    fun getTermsIdString(): String = ""
//    fun
//    fun getTermsTitle(): String {
//        return title
//    }
//}