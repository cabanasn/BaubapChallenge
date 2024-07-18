package com.ircl.baubapchallenge.data.remote

data class LoginResponse(
    val data: Data? = null,
    val errorCode: Int? = null,
    val message: String? = null,
    val status: String? = null
) {
    data class Data(
        val expiresIn: Int? = null,
        val token: String? = null,
        val userId: String? = null,
        val username: String? = null
    )
}