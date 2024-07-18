package com.ircl.baubapchallenge.domain.repository

import com.ircl.baubapchallenge.data.remote.LoginResponse
import com.ircl.baubapchallenge.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(username: String, password: String): Flow<Resource<LoginResponse>>
}