package com.upsidedowndev.mastermeme.core.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import com.upsidedowndev.mastermeme.ui.theme.MyAppColor
import com.upsidedowndev.mastermeme.ui.theme.manropeFamily

@Composable
fun MemeOutlineButton(
    modifier: Modifier = Modifier, onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(18),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.border(
                width = 1.dp, brush = MyAppColor.buttonGradientColor, shape = RoundedCornerShape(18)
            )
    ) {
        Text(
            text = "Add text",
            fontFamily = manropeFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }

}

@Preview
@Composable
private fun MemeOutlineButtonPreview() {
    MasterMemeTheme {
        MemeOutlineButton(
            onClick = {})
    }
}