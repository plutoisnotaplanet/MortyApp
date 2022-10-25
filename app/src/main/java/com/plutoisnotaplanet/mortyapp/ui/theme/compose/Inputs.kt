package com.plutoisnotaplanet.mortyapp.application.utils.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.InputState


@Preview(showBackground = true)
@Composable
fun DefaultInputField(
    modifier: Modifier = Modifier,
    label: String = stringResource(id = R.string.tv_unknown),
    value: String = "",
    onValueChange: (String) -> Unit = {},
    inputState: InputState = InputState.Initialize
) {
    val isError = inputState is InputState.Error
    Column(modifier = modifier) {
        if (label.isNotBlank()) {
            GrayText(
                modifier = Modifier.padding(bottom = 8.dp),
                value = label
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            singleLine = true,
            trailingIcon = {
                if (isError)
                    Icon(
                        imageVector = Icons.Filled.Error,
                        "error",
                        tint = MaterialTheme.colors.error
                    )
            },
            isError = isError,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.DarkGray,
                unfocusedBorderColor = Color.LightGray,
                errorBorderColor = colorResource(id = R.color.colorAccent)
            )
        )
        if (inputState is InputState.Error) {
            Text(
                text = stringResource(id = inputState.errorRes),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    label: String = stringResource(id = R.string.tv_unknown),
    value: String = "",
    onValueChange: (String) -> Unit = {},
    inputState: InputState = InputState.Initialize
) {
    val isError = inputState is InputState.Error
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    Column(modifier = modifier) {
        if (label.isNotBlank()) {
            GrayText(
                modifier = Modifier.padding(bottom = 8.dp),
                value = label
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (isPasswordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (isPasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            isError = isError,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.DarkGray,
                unfocusedBorderColor = Color.LightGray,
                errorBorderColor = colorResource(id = R.color.colorAccent)
            )
        )
        if (inputState is InputState.Error) {
            Text(
                text = stringResource(id = inputState.errorRes),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }
}