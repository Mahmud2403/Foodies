package com.example.foodies.utils

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpace(height: Dp) = Spacer(modifier = Modifier.height(height))

@Composable
fun HorizontalSpacer(width: Dp) = Spacer(modifier = Modifier.width(width))

fun Modifier.clickableWithRipple(
    radius: Dp = 18.dp,
    color: Color = Color.Unspecified,
    enabled: Boolean = true,
    bounded: Boolean = false,
    onClick: () -> Unit,
) = composed {
    then(
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(radius = radius, bounded = bounded, color = color),
            enabled = enabled,
            onClick = onClick
        )
    )
}
fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = composed {
    then(
        drawBehind {
            drawIntoCanvas { canvas ->
                val paint = Paint()
                val frameworkPaint = paint.asFrameworkPaint()
                if (blurRadius != 0.dp) {
                    frameworkPaint.maskFilter =
                        (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
                }
                frameworkPaint.color = color.toArgb()
                val leftPixel = offsetX.toPx()
                val topPixel = offsetY.toPx()
                val rightPixel = size.width + topPixel
                val bottomPixel = size.height + leftPixel

                canvas.drawRect(
                    left = leftPixel,
                    top = topPixel,
                    right = rightPixel,
                    bottom = bottomPixel,
                    paint = paint,
                )
            }
        }
    )
}

fun Modifier.innerShadow(
    shape: Shape = CircleShape,
    color: Color,
    blur: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
) = drawWithContent {
    drawContent() // Rendering the content

    val rect = Rect(Offset.Zero, size)
    val paint = Paint().apply {
        this.color = color
        this.isAntiAlias = true
    }

    val shadowOutline = shape.createOutline(size, layoutDirection, this)

    drawIntoCanvas { canvas ->

        // Save the current layer.
        canvas.saveLayer(rect, paint)
        // Draw the first layer of the shadow.
        canvas.drawOutline(shadowOutline, paint)

        // Convert the paint to a FrameworkPaint.
        val frameworkPaint = paint.asFrameworkPaint()
        // Set xfermode to DST_OUT to create the inner shadow effect.
        frameworkPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

        // Apply blur if specified.
        if (blur.toPx() > 0) {
            frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }

        // Change paint color to black for the inner shadow.
        paint.color = Color.Black

        // Calculate offsets considering spread.
        val spreadOffsetX = offsetX.toPx() + if (offsetX.toPx() < 0) -spread.toPx() else spread.toPx()
        val spreadOffsetY = offsetY.toPx() + if (offsetY.toPx() < 0) -spread.toPx() else spread.toPx()

        // Move the canvas to specific offsets.
        canvas.translate(spreadOffsetX, spreadOffsetY)

        // Draw the second layer of the shadow.
        canvas.drawOutline(shadowOutline, paint)

        // Restore the canvas to its original state.
        canvas.restore()
    }
}