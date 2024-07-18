package com.ircl.baubapchallenge.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ircl.baubapchallenge.R
import com.ircl.baubapchallenge.data.remote.FakeLoginHandler.Status
import com.ircl.baubapchallenge.data.remote.FakeLoginHandler.Status.ERROR
import com.ircl.baubapchallenge.data.remote.FakeLoginHandler.Status.SUCCESS
import com.ircl.baubapchallenge.ui.utils.BasicAlertDialog

@Composable
fun LoginScreen() {

    val viewModel = hiltViewModel<LoginViewModel>()
    val state by viewModel.loginState.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.baubap_banner),
            contentDescription = stringResource(R.string.baubap_banner),
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )

        // Username field
        OutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.updateUsername(it) },
            label = { Text(stringResource(R.string.username)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            isError = state.showUsernameEmptyError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            )
        )

        // Password field
        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .focusRequester(focusRequester),
            visualTransformation = if (state.passwordVisible)
                VisualTransformation.None else
                    PasswordVisualTransformation(),
            isError = state.showPasswordEmptyError,
            trailingIcon = {
                val image = if (state.passwordVisible) {
                    Icons.Default.Visibility
                } else {
                    Icons.Default.VisibilityOff
                }
                IconButton(onClick = {
                    viewModel.updatePasswordVisible()
                }) {
                    Icon(imageVector = image,
                        contentDescription = if (state.passwordVisible)
                            stringResource(R.string.hide_password) else
                                stringResource(R.string.show_password)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        Box(
            modifier = Modifier
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                // Loading indicator
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp),
                    strokeWidth = 4.dp
                )
            } else {
                // Login button
                Button(
                    onClick = {
                        keyboardController?.hide()
                        viewModel.login()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(200.dp))

        val loginResponse = state.loginResponse
        if (loginResponse != null) {
            when (Status.valueOf(loginResponse.status!!)) {
                SUCCESS -> {
                    BasicAlertDialog(
                        title = stringResource(R.string.login_successful),
                        message = stringResource(
                            R.string.logged_in_successfully,
                            loginResponse.data?.username!!
                        ),
                        onDismiss = { viewModel.clearLoginResponse() }
                    )
                }
                ERROR -> {
                    BasicAlertDialog(
                        title = stringResource(R.string.error),
                        message = "${loginResponse.message}",
                        onDismiss = { viewModel.clearLoginResponse() }
                    )
                }
            }
        }
    }
}