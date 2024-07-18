package com.ircl.baubapchallenge.domain.repository

import com.ircl.baubapchallenge.data.remote.FakeLoginHandler
import com.ircl.baubapchallenge.data.remote.FakeLoginHandler.Status.ERROR
import com.ircl.baubapchallenge.data.remote.FakeLoginHandler.Status.SUCCESS
import com.ircl.baubapchallenge.utils.LoginResponses
import com.ircl.baubapchallenge.utils.Resource
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(FakeLoginHandler())
    }

    @Test
    fun `login with valid credentials returns success`() = runTest {
        val username = "baubap"
        val password = "password"
        val result = userRepository.login(username, password).toList()

        assert(result[0] is Resource.Loading)
        assert(result[1] is Resource.Success)
        val data = (result[1] as Resource.Success).data
        assertEquals(SUCCESS.name, data?.status)
        assertEquals(LoginResponses.successExampleResponse.data, data?.data)
        assert(result[2] is Resource.Loading)
    }

    @Test
    fun `login with invalid credentials returns error`() = runTest {
        val username = "errorUser"
        val password = "errorPassword"
        val result = userRepository.login(username, password).toList()

        assert(result[0] is Resource.Loading)
        assert(result[1] is Resource.Success)
        val data = (result[1] as Resource.Success).data
        assertEquals(ERROR.name, data?.status)
        assertEquals(LoginResponses.errorExampleResponse.message, data?.message)
        assertNull(data?.data)
        assert(result[2] is Resource.Loading)
    }
}