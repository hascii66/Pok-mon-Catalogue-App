package com.example.pokmoncatalogueapp.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokmoncatalogueapp.ui.theme.getTypeColor

@Composable
fun TypeChip(type: String) {
    Surface(
        color = getTypeColor(type),
        shape = CircleShape,
    ) {
        Text(
            text = type.replaceFirstChar { it.titlecase() },
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}