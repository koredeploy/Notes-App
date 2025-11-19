package com.bankaapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bankaapp.ui.theme.darkBlue

data class DropdownMenuItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun CustomDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    items: List<DropdownMenuItem>
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        offset = DpOffset(x = (-16).dp, y = 8.dp),
        modifier = modifier
            .width(200.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItemContent(
                        title = item.title,
                        icon = item.icon,
                        onClick = {
                            item.onClick()
                            onDismissRequest()
                        }
                    )

                    // Add divider between items except after the last item
                    if (index < items.size - 1) {
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.LightGray.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DropdownMenuItemContent(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = darkBlue,
            modifier = Modifier.padding(end = 12.dp)
        )

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

// Predefined menu configurations for common use cases
object DropdownMenus {
    fun notesMenuItems(
        onCreateNote: () -> Unit,
        onLogout: () -> Unit
    ): List<DropdownMenuItem> = listOf(
        DropdownMenuItem(
            title = "Create Note",
            icon = Icons.Default.Add,
            onClick = onCreateNote
        ),
        DropdownMenuItem(
            title = "Logout",
            icon = Icons.Default.ExitToApp,
            onClick = onLogout
        )
    )
}