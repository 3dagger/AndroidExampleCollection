package kr.dagger.gsontypeadapterexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.dagger.gsontypeadapterexample.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding

	private val service = RetrofitInstance.retrofitService

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		binding.btnCalled.setOnClickListener {
			lifecycleScope.launch {
				service.getCellItems().enqueue(object : Callback<Cell> {
					override fun onResponse(call: Call<Cell>, response: Response<Cell>) {
						val result = response.body()
						result?.cellItems?.forEach {
							when (it) {
								is CellItem.CellTypeReview -> {
									Log.d("leeam", "CellTypeReview : $it")
								}
								is CellItem.CellTypeCompany -> {
									Log.d("leeam", "CellTypeCompany: $it")
								}
								is CellItem.CellTypeHorizontalTheme -> {
									Log.d("leeam", "CellTypeHorizontalTheme: $it")
								}
								is CellItem.UnknownType -> {
									Log.d("leeam", "CellItem.UnknownType: $it")
								}
								else -> {
									Log.d("leeam", "else: $it")
								}
							}
						}
					}

					override fun onFailure(call: Call<Cell>, error: Throwable) {
						Log.d("leeam", "error : ${error.message}")
					}
				})
			}
		}
	}
}