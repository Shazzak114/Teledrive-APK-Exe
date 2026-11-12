package com.teledrive.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun TeleDriveTheme(darkTheme: Boolean = false, primaryColor: Int = 0xFF6200EE, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) {
            androidx.compose.material3.darkColorScheme(primary = Color(primaryColor))
        } else {
            androidx.compose.material3.lightColorScheme(primary = Color(primaryColor))
        }
    ) { content() }
}
