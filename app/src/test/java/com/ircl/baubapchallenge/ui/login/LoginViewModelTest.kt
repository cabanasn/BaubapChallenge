package com.ircl.baubapchallenge.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ircl.baubapchallenge.data.remote.FakeLoginHandler.Status.SUCCESS
import com.ircl.baubapchallenge.domain.use_case.LoginUseCase
import com.ircl.baubapchallenge.utils.LoginResponses
import com.ircl.baubapchallenge.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `login success updates state correctly`() = runTest {
        val username = "baubap"
        val password = "password"
        `when`(loginUseCase.invoke(username, password)).thenReturn(flowOf(Resource.Success(LoginResponses.successExampleResponse)))

        viewModel.updateUsername(username)
        viewModel.updatePassword(password)
        viewModel.login()

        assert(viewModel.loginState.value.isLoading.not())
        assert(viewModel.loginState.value.loginResponse?.status == SUCCESS.name)
        assert(viewModel.loginState.value.loginResponse?.data?.username == "Baubap User")
    }

    @Test
    fun `login failure updates state correctly`() = runTest {
        val username = "user"
        val password = "wrong"
        `when`(loginUseCase.invoke(username, password)).thenReturn(flowOf(Resource.Success(LoginResponses.errorExampleResponse)))

        viewModel.updateUsername(username)
        viewModel.updatePassword(password)
        viewModel.login()

        assert(viewModel.loginState.value.isLoading.not())
        assert(viewModel.loginState.value.loginResponse == LoginResponses.errorExampleResponse)
    }

    @Test
    fun `checkFieldsNotEmpty shows error`() = runTest {
        viewModel.updateUsername("")
        viewModel.updatePassword("")
        viewModel.checkFieldsNotEmpty()
        assert(viewModel.loginState.value.showUsernameEmptyError)
        assert(viewModel.loginState.value.showPasswordEmptyError)
    }

    @Test
    fun `clearLoginResponse clears login response`() = runTest {
        viewModel.loginState.value.loginResponse = LoginResponses.successExampleResponse
        viewModel.clearLoginResponse()
        assert(viewModel.loginState.value.loginResponse == null)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}