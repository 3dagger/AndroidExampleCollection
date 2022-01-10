package kr.dagger.domain.repository

import kr.dagger.domain.state.ApiResponse
import kr.dagger.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getUserInfo(owner: String?): Flow<ApiResponse<UserProfile>>
//    fun getUserInfo(owner: String?): Flow<ApiResponse<List<MainUserInfoEntity>>>
//    fun getUserInfo(owner: String?): Flow<MainUserInfoEntity>
}

