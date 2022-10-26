package com.plutoisnotaplanet.mortyapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.AvatarImage


@Composable
fun DrawerContent(
    userProfile: UserProfile,
    itemClick: (String) -> Unit
) {

    val itemsList = NavigationDrawerItem.values().dropLast(1)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.colorAccent),
                        colorResource(id = R.color.colorPrimaryDark)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 36.dp),
        userScrollEnabled = false
    ) {

        item {

            AvatarImage(
                avatar = userProfile.photoData?.photoUri ?: Icons.Filled.AccountCircle,
                onClick = { itemClick(NavigationDrawerItem.ACCOUNT.route) }
            )

            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 30.dp),
                text = if (!userProfile.email.isNullOrBlank()) userProfile.email else "No email",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        items(itemsList) { item ->
            NavigationListItem(item = item) {
                itemClick(item.route)
            }
        }
        item {
            Spacer(modifier = Modifier.height(272.dp))

            NavigationListItem(item = NavigationDrawerItem.LOGOUT) {
                itemClick(NavigationDrawerItem.LOGOUT.route)
            }
        }
    }
}

@Composable
private fun NavigationListItem(
    item: NavigationDrawerItem,
    itemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                itemClick()
            }
            .padding(horizontal = 24.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box {
            Icon(
                modifier = Modifier
                    .padding(all = 2.dp)
                    .size(size = 28.dp),
                imageVector = item.image,
                contentDescription = null,
                tint = Color.White
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = item.route,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Immutable
enum class NavigationDrawerItem(
    val image: ImageVector,
    val route: String
) {
    HOME(Icons.Filled.Home, NavScreen.NavHomeScope.route),
    ACCOUNT(Icons.Filled.AccountBox, NavScreen.Account.route),
    SETTINGS(Icons.Filled.Settings, NavScreen.Settings.route),
    LOGOUT(Icons.Filled.ExitToApp, NavScreen.Splash.route)
}
