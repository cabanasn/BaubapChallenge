package com.ircl.baubapchallenge.ui.login

import com.ircl.baubapchallenge.data.remote.FakeLoginHandler.Status.SUCCESS
import com.ircl.baubapchallenge.utils.LoginResponses
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginStateTest {

    @Test
    fun `initial state is correct`() = runTest {
        val loginState = LoginState()
        assertTrue(loginState.username.isEmpty())
        assertTrue(loginState.password.isEmpty())
        assertFalse(loginState.showUsernameEmptyError)
        assertFalse(loginState.showPasswordEmptyError)
        assertFalse(loginState.isLoading)
        assertNull(loginState.loginResponse)
    }

    @Test
    fun `setting username updates state correctly`() {
        val loginState = LoginState().apply { username = "newUser" }
        assertTrue(loginState.username == "newUser")
    }

    @Test
    fun `setting password updates state correctly`() {
        val loginState = LoginState().apply { password = "newPass" }
        assertTrue(loginState.password == "newPass")
    }

    @Test
    fun `enabling username empty error updates state correctly`() {
        val loginState = LoginState().apply { showUsernameEmptyError = true }
        assertTrue(loginState.showUsernameEmptyError)
    }

    @Test
    fun `enabling password empty error updates state correctly`() {
        val loginState = LoginState().apply { showPasswordEmptyError = true }
        assertTrue(loginState.showPasswordEmptyError)
    }

    @Test
    fun `setting isLoading updates state correctly`() {
        val loginState = LoginState().apply { isLoading = true }
        assertTrue(loginState.isLoading)
    }

    @Test
    fun `setting loginResponse updates state correctly`() {
        val loginState = LoginState().apply { loginResponse = LoginResponses.successExampleResponse }
        assertTrue(loginState.loginResponse?.status == SUCCESS.name)
    }
}