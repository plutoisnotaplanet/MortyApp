package com.plutoisnotaplanet.mortyapp.ui.screens.drawer_scope.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.ui.components.animations.pushedAnimation
import com.plutoisnotaplanet.mortyapp.ui.components.AvatarImage
import timber.log.Timber

@Preview
@Composable
fun AccountAvatar(
    modifier: Modifier = Modifier,
    userProfile: UserProfile = UserProfile(),
    onClick: () -> Unit = {}
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        Timber.e("avatar compose")

        val (profileImage, addPhotoBtn) = createRefs()

        AvatarImage(
            modifier = Modifier
                .constrainAs(profileImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            avatar = userProfile.photoData?.photoUri ?: Icons.Filled.AccountCircle,
            onClick = onClick
        )

        Image(
            modifier = Modifier
                .pushedAnimation { onClick() }
                .constrainAs(addPhotoBtn) {
                    top.linkTo(profileImage.bottom, margin = (-16).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = painterResource(id = R.drawable.ic_btn_add_photo),
            contentDescription = null,
        )
    }
}