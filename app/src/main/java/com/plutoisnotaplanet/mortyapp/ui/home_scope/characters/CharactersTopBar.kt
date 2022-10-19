package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.mortyapp.R

@Composable
fun CharactersTopBar(
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.menu_characters), fontSize = 18.sp) },
        backgroundColor = colorResource(id = R.color.colorPrimary),
        contentColor = Color.White,
        actions = {

        }
    )
}