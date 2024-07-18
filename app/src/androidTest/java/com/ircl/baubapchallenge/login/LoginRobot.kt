package com.ircl.baubapchallenge.login

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.ircl.baubapchallenge.utils.SemanticUtils.doesNotHaveError
import com.ircl.baubapchallenge.utils.SemanticUtils.hasError

class LoginRobot(private val composeTestRule: ComposeTestRule) {

    fun enterUsername(username: String) {
        composeTestRule.onNodeWithText(USERNAME_TEXT).
            performTextInput(username)
    }

    fun enterPassword(password: String) {
        composeTestRule.onNodeWithText(PASSWORD_TEXT)
            .performTextInput(password)
    }

    fun assertEnterUsernameHasError(hasError: Boolean) {
        composeTestRule.onNodeWithText(USERNAME_TEXT)
            .assert(if (hasError) hasError() else doesNotHaveError())
    }

    fun assertEnterPasswordHasError(hasError: Boolean) {
        composeTestRule.onNodeWithText(PASSWORD_TEXT)
            .assert(if (hasError) hasError() else doesNotHaveError())
    }


    fun clickLoginButton() {
        composeTestRule.onNodeWithText(LOGIN_TEXT).performClick()
    }

    fun assertAlertDisplay(alertText: String) {
        composeTestRule.waitUntil(timeoutMillis = 2500) {
            composeTestRule.onAllNodesWithText(alertText)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    companion object {
        const val PASSWORD_TEXT = "Password"
        const val USERNAME_TEXT = "Username"
        const val LOGIN_TEXT = "Login"
    }


}