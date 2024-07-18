package com.ircl.baubapchallenge.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeLoginHandler {

    enum class Status {
        SUCCESS, ERROR
    }

    suspend fun login(username: String, password: String): LoginResponse {
        // Run in IO dispatcher as a networking library would
        return withContext(Dispatchers.IO) {
            // Add delay to simulate network request
            delay(2000)
            return@withContext performLogin(username, password)
        }
    }

    private fun performLogin(username: String, password: String): LoginResponse {
        return when {
            username == SUCCESS_USERNAME && password == SUCCESS_PASSWORD -> successResponse()
            else -> errorResponse()
        }
    }

    private fun successResponse(): LoginResponse =
        LoginResponse(
            status = Status.SUCCESS.name,
            data = LoginResponse.Data(
                userId = "12345",
                username = "Baubap User",
                token = "fake_token",
                expiresIn = 3600
            )
        )

    private fun errorResponse(): LoginResponse =
        LoginResponse(
            status = Status.ERROR.name,
            errorCode = 401,
            message = "Invalid username or password"
        )

    companion object {
        const val SUCCESS_USERNAME = "baubap"
        const val SUCCESS_PASSWORD = "password"
    }

}