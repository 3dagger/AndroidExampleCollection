package kr.dagger.gsontypeadapterexample

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

	@GET("mobile-config/test_data_cell_items.json")
	fun getCellItems(): Call<Cell>
}