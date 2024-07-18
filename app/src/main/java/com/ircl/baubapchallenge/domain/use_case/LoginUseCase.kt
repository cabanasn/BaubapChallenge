package com.ircl.baubapchallenge.domain.use_case

import com.ircl.baubapchallenge.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String) = userRepository.login(username, password)
}