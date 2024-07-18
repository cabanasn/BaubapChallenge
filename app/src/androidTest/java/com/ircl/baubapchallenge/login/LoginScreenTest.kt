package com.ircl.baubapchallenge.login

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ircl.baubapchallenge.ui.login.LoginActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    private val loginRobot = LoginRobot(composeTestRule)

    @Test
    fun testLoginSuccessWithValidValues() = runTest {
        loginRobot.enterUsername("baubap")
        loginRobot.enterPassword("password")
        loginRobot.clickLoginButton()
        loginRobot.assertAlertDisplay("Baubap User logged in successfully.")
    }

    @Test
    fun testLoginErrorWithInvalidValues() = runTest {
        loginRobot.enterUsername("invaliduser")
        loginRobot.enterPassword("password123")
        loginRobot.clickLoginButton()
        loginRobot.assertAlertDisplay("Invalid username or password")
    }

    @Test
    fun testShowErrorWhenEmptyInputs() = runTest {
        loginRobot.clickLoginButton()
        loginRobot.assertEnterUsernameHasError(true)
        loginRobot.assertEnterPasswordHasError(true)
    }

    @Test
    fun testShowErrorOnlyForEmptyInputs() = runTest {
        loginRobot.enterUsername("fillusername")
        loginRobot.clickLoginButton()
        loginRobot.assertEnterUsernameHasError(false)
        loginRobot.assertEnterPasswordHasError(true)
    }

    @Test
    fun testHideErrorWhenEmptyInputIsFilled() = runTest {
        loginRobot.clickLoginButton()
        loginRobot.assertEnterUsernameHasError(true)
        loginRobot.assertEnterPasswordHasError(true)
        loginRobot.enterUsername("fillusername")
        loginRobot.assertEnterUsernameHasError(false)
        loginRobot.enterPassword("fillpassword")
        loginRobot.assertEnterPasswordHasError(false)
    }

}