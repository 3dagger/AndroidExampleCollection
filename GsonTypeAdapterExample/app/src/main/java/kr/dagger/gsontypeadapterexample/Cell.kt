package kr.dagger.gsontypeadapterexample

import com.google.gson.annotations.SerializedName

data class Cell(
	@SerializedName("cell_items")
	val cellItems: List<CellItem?>?
)

sealed class CellItem {

	data class CellTypeCompany(
		@SerializedName("cell_type")
		val cellType: String?,
		@SerializedName("logo_path")
		val logoPath: String?,
		@SerializedName("name")
		val name: String?,
		@SerializedName("industry_name")
		val industryName: String?,
		@SerializedName("rate_total_avg")
		val rateTotalAvg: Double?,
		@SerializedName("interview_question")
		val interviewQuestion: String?,
		@SerializedName("salary_avg")
		val salaryAvg: Int?,
		@SerializedName("update_date")
		val updateDate: String?
	) : CellItem()

	data class CellTypeHorizontalTheme(
		@SerializedName("cell_type")
		val cellType: String?,
		@SerializedName("count")
		val count: Int?,
		@SerializedName("section_title")
		val sectionTitle: String?,
		@SerializedName("recommend_recruit")
		val recommendRecruit: List<RecommendRecruit?>?,
	) : CellItem() {
		data class RecommendRecruit(
			@SerializedName("id")
			val id: Int?,
			@SerializedName("title")
			val title: String?,
			@SerializedName("reward")
			val reward: Int?,
			@SerializedName("appeal")
			val appeal: String?,
			@SerializedName("image_url")
			val imageUrl: String?,
			@SerializedName("company")
			val company: Company?
		) {
			data class Company(
				@SerializedName("name")
				val name: String?,
				@SerializedName("logo_path")
				val logoPath: String?,
				@SerializedName("ratings")
				val ratings: List<Rating?>?
			) {
				data class Rating(
					@SerializedName("type")
					val type: String?,
					@SerializedName("rating")
					val rating: Double?
				)
			}
		}
	}

	data class CellTypeReview(
		@SerializedName("cell_type")
		val cellType: String?,
		@SerializedName("logo_path")
		val logoPath: String?,
		@SerializedName("name")
		val name: String?,
		@SerializedName("industry_name")
		val industryName: String?,
		@SerializedName("rate_total_avg")
		val rateTotalAvg: Double?,
		@SerializedName("review_summary")
		val reviewSummary: String?,
		@SerializedName("cons")
		val cons: String?,
		@SerializedName("pros")
		val pros: String?,
		@SerializedName("update_date")
		val updateDate: String?
	) : CellItem()

	data object UnknownType : CellItem()
}
