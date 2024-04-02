package kr.dagger.gsontypeadapterexample

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class CellTypeAdapter : TypeAdapter<CellItem>() {
	override fun write(out: JsonWriter?, value: CellItem?) {}
	override fun read(reader: JsonReader?): CellItem {
		val jsonObject = JsonParser.parseReader(reader).asJsonObject
		val cellType = jsonObject.get("cell_type").asString
		return when (cellType) {
			"CELL_TYPE_COMPANY" -> Gson().fromJson(jsonObject, CellItem.CellTypeCompany::class.java)
			"CELL_TYPE_HORIZONTAL_THEME" -> Gson().fromJson(jsonObject, CellItem.CellTypeHorizontalTheme::class.java)
			"CELL_TYPE_REVIEW" -> Gson().fromJson(jsonObject, CellItem.CellTypeReview::class.java)
			else -> CellItem.UnknownType
		}
	}
}