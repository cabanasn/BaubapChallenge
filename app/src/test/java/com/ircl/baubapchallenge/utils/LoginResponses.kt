package com.ircl.baubapchallenge.utils

import com.ircl.baubapchallenge.data.remote.FakeLoginHandler.Status
import com.ircl.baubapchallenge.data.remote.LoginResponse

object LoginResponses {

    val successExampleResponse =
        LoginResponse(
            status = Status.SUCCESS.name,
            data = LoginResponse.Data(
                userId = "12345",
                username = "Baubap User",
                token = "fake_token",
                expiresIn = 3600
            )
        )

    val errorExampleResponse =
        LoginResponse(
            status = Status.ERROR.name,
            errorCode = 401,
            message = "Invalid username or password"
        )

}