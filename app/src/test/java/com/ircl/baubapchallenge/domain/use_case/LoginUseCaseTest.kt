package com.ircl.baubapchallenge.domain.use_case

import com.ircl.baubapchallenge.data.remote.FakeLoginHandler.Status
import com.ircl.baubapchallenge.domain.repository.UserRepository
import com.ircl.baubapchallenge.utils.LoginResponses
import com.ircl.baubapchallenge.utils.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private val userRepository: UserRepository = mock(UserRepository::class.java)

    @Before
    fun setup() {
        loginUseCase = LoginUseCase(userRepository)
    }

    @Test
    fun `invoke returns success and data when repository returns success`() = runTest {
        `when`(userRepository.login("user", "success")).thenReturn(flowOf(Resource.Success(LoginResponses.successExampleResponse)))
        val result = loginUseCase.invoke("user", "success").first()
        assertTrue(result is Resource.Success)
        assertEquals(LoginResponses.successExampleResponse, (result as Resource.Success).data)
    }

    @Test
    fun `invoke returns error and message when repository returns invalid user or password`() = runTest {
        `when`(userRepository.login("user", "failure")).thenReturn(flowOf(Resource.Success(LoginResponses.errorExampleResponse)))
        val result = loginUseCase.invoke("user", "failure").first()
        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals(Status.ERROR.name, data!!.status)
        assertEquals(LoginResponses.errorExampleResponse.message, data.message)
    }

    @Test
    fun `invoke returns error when repository returns failure`() = runTest {
        `when`(userRepository.login("user", "fail")).thenReturn(flowOf(Resource.Error("Error")))
        val result = loginUseCase.invoke("user", "fail").first()
        assertTrue(result is Resource.Error)
        assertEquals("Error", (result as Resource.Error).message)
    }

}