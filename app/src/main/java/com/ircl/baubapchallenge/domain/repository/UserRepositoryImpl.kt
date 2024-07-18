package com.ircl.baubapchallenge.domain.repository

import com.ircl.baubapchallenge.data.remote.FakeLoginHandler
import com.ircl.baubapchallenge.data.remote.LoginResponse
import com.ircl.baubapchallenge.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val fakeLoginHandler: FakeLoginHandler
) : UserRepository {

    override suspend fun login(username: String, password: String): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading())
            val loginResponse = fakeLoginHandler.login(username, password)
            emit(Resource.Success(loginResponse))
            emit(Resource.Loading(false))
        }
    }

}