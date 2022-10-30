package com.plutoisnotaplanet.mortyapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.mortyapp.application.domain.model.BottomMenuAction
import com.plutoisnotaplanet.mortyapp.ui.components.animations.pushedAnimation

@Composable
fun BottomMenuScreen(
    actionsList: List<BottomMenuAction>,
    onActionClick: (BottomMenuAction) -> Unit
) {
    Column(modifier = Modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        actionsList.forEach { action ->
            BottomMenuItem(
                action = action,
                onActionClick = onActionClick
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun BottomMenuItem(
    action: BottomMenuAction = BottomMenuAction.OpenCamera,
    onActionClick: (BottomMenuAction) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pushedAnimation { (onActionClick(action)) }
            .padding(horizontal = 24.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box {
            Icon(
                modifier = Modifier
                    .padding(all = 2.dp)
                    .size(size = 28.dp),
                imageVector = action.image,
                contentDescription = null,
                tint = colorResource(id = com.plutoisnotaplanet.mortyapp.R.color.colorAccent)
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = action.value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}