package com.ircl.baubapchallenge.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ircl.baubapchallenge.domain.use_case.LoginUseCase
import com.ircl.baubapchallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            if (checkFieldsNotEmpty()) {
                _loginState.update { it.copy(isLoading = true) }
                val username = _loginState.value.username
                val password = _loginState.value.password
                val loginResponse = loginUseCase.invoke(username, password)
                loginResponse.collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { response ->
                                _loginState.update {
                                    it.copy(isLoading = false, loginResponse = response)
                                }
                            }
                        }

                        is Resource.Error -> {
                            _loginState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            _loginState.update {
                                it.copy(isLoading = it.isLoading)
                            }
                        }
                    }
                }
            }
        }
    }

    fun checkFieldsNotEmpty(): Boolean {
        val isUserNameEmpty = _loginState.value.username.isEmpty()
        val isPasswordEmpty = _loginState.value.password.isEmpty()
        _loginState.update {
            it.copy(
                showUsernameEmptyError = isUserNameEmpty,
                showPasswordEmptyError = isPasswordEmpty
            )
        }
        return !(isUserNameEmpty || isPasswordEmpty)
    }

    fun updateUsername(username: String) {
        _loginState.update {
            it.copy(
                username = username,
                showUsernameEmptyError = username.isEmpty()
            )
        }
    }

    fun updatePassword(password: String) {
        _loginState.update {
            it.copy(
                password = password,
                showPasswordEmptyError = password.isEmpty()
            )
        }
    }

    fun clearLoginResponse() {
        _loginState.update {
            it.copy(loginResponse = null)
        }
    }
}