package com.plutoisnotaplanet.mortyapp.ui.screens.drawer_scope.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.mortyapp.ui.components.DataField
import com.plutoisnotaplanet.mortyapp.ui.components.BorderColumn

@Composable
fun SettingsScreen(

) {

    Column(modifier = Modifier.fillMaxSize()) {

        BorderColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
        ) {

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(top = 8.dp).fillMaxWidth()) {
                Text(
                    text = "DataBase data",
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 18.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 12.dp)
                )

                Text(
                    text = "Uploaded/All",
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }

            DataField(
                modifier = Modifier.padding(top = 16.dp),
                name = "Characters",
                value = "12/894"
            )
            DataField(
                modifier = Modifier.padding(top = 8.dp),
                name = "Locations",
                value = "12/89"
            )
            DataField(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                name = "Episodes",
                value = "12/89"
            )
        }

    }


}