package com.ircl.baubapchallenge.ui.login

import com.ircl.baubapchallenge.data.remote.LoginResponse

data class LoginState(
    var username: String = "",
    var password: String = "",
    var showUsernameEmptyError: Boolean = false,
    var showPasswordEmptyError: Boolean = false,
    var isLoading: Boolean = false,
    var loginResponse: LoginResponse? = null
)