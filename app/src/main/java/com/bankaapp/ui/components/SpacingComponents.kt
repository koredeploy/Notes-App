package com.bankaapp.ui.components

//class SpacingComponents {
//}

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Vertical spacers with fixed heights
@Composable
fun SmallSpace() {
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun MediumSpace() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun LargeSpace() {
    Spacer(modifier = Modifier.height(24.dp))
}

// Horizontal spacers with fixed widths
@Composable
fun SmallHorizontalSpace() {
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
fun MediumHorizontalSpace() {
    Spacer(modifier = Modifier.width(16.dp))
}