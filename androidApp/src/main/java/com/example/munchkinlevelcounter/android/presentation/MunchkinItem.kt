package com.example.munchkinlevelcounter.android.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharedmainfeature.composeColor
import com.example.sharedmainfeature.objects.Munchkin

@Composable
fun MunchkinItem(
    munchkin: Munchkin,
    selected: Boolean = false,
    editMode: Boolean = false,
    onDelete: (Munchkin) -> Unit = {},
    onClick: (Munchkin) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = { onClick(munchkin) })
            .background(if (selected) Color(0xFFeeeeee) else Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(
                    munchkin.composeColor()
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = munchkin.name.first().uppercaseChar().toString(),
                fontSize = 24.sp,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f, fill = false),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = munchkin.name,
                fontSize = 16.sp
            )
            Icon(
                imageVector = munchkin.getSexIcon(),
                contentDescription = "sex",
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(4.dp)
        )

        if (editMode) {
            IconButton(
                onClick = { onClick(munchkin) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "edit item"
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            IconButton(
                onClick = { onDelete(munchkin) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "delete item"
                )
            }
        } else {
            MunchkinCharacteristics(
                munchkin.level.toString(),
                Icons.Default.Bolt,
                "level"
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            MunchkinCharacteristics(
                munchkin.strength.toString(),
                Icons.Default.Colorize,
                "strength"
            )
        }
    }
}

@Composable
fun MunchkinCharacteristics(
    characteristic: String,
    icon: ImageVector,
    contentDescription: String
) {
    Text(
        text = characteristic,
        fontSize = 16.sp
    )

    Icon(
        imageVector = icon,
        contentDescription = contentDescription
    )
}

fun Munchkin.getSexIcon() = when (sex) {
    "male" -> Icons.Default.Male
    "female" -> Icons.Default.Female
    else -> Icons.Default.Transgender
}

@Preview
@Composable
fun ComposablePreview() {
    MunchkinItem(
        Munchkin(
            id = null,
            name = "one",
            level = 1,
            sex = "male",
            strength = 1,
            colorRGB = Munchkin.colorsListRGB.first()
        )
    ) {

    }
}