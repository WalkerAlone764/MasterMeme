package com.upsidedowndev.mastermeme.core.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.mastermeme.ui.theme.MyAppColor
import com.upsidedowndev.mastermeme.ui.theme.manropeFamily

@Composable
fun MemeButton(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(18),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(
            width = 1.dp,
            brush = MyAppColor.buttonGradientColor
        ),
        modifier = modifier
            .background(
                brush = MyAppColor.buttonGradientColor,
                shape = RoundedCornerShape(18)
            )
    ) {
        Text(
            text = title,
            fontFamily = manropeFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
    }
}