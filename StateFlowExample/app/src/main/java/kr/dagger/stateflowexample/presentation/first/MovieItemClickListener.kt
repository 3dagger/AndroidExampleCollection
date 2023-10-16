package kr.dagger.stateflowexample.presentation.first

import kr.dagger.stateflowexample.domain.model.Popular

interface MovieItemClickListener {

	fun onItemClicked(item: Popular.PopularResults)
}