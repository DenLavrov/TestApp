package com.test.app.core.presentation.views

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TriangleEdgeShape(
    private val offset: Int,
    private val cornerRadius: Float,
    private val orientation: Orientation
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            if (orientation == Orientation.RIGHT) {
                addOutline(
                    Outline.Rounded(
                        RoundRect(
                            size.toRect(),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius.Zero,
                            CornerRadius(cornerRadius, cornerRadius)
                        )
                    )
                )
                moveTo(x = size.width, y = size.height - offset)
                lineTo(x = size.width, y = size.height)
                lineTo(x = size.width + offset, y = size.height)
            } else {
                addOutline(
                    Outline.Rounded(
                        RoundRect(
                            size.toRect(),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius(cornerRadius, cornerRadius),
                            CornerRadius.Zero
                        )
                    )
                )
                moveTo(x = 0f, y = size.height - offset)
                lineTo(x = 0f, y = size.height)
                lineTo(x = 0f - offset, y = size.height)
            }
        }
        return Outline.Generic(trianglePath)
    }

    @Immutable
    enum class Orientation {
        LEFT,
        RIGHT
    }
}