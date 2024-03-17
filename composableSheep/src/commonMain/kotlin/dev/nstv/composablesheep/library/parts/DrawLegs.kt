package dev.nstv.composablesheep.library.parts

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import dev.nstv.composablesheep.library.getDefaultSheepRadius
import dev.nstv.composablesheep.library.model.Leg
import dev.nstv.composablesheep.library.model.twoLegsStraight
import dev.nstv.composablesheep.library.util.GuidelineAlpha
import dev.nstv.composablesheep.library.util.GuidelineDashPattern
import dev.nstv.composablesheep.library.util.GuidelineStrokeWidth
import dev.nstv.composablesheep.library.util.SheepColor
import dev.nstv.composablesheep.library.util.getCircumferencePointForAngle
import dev.nstv.composablesheep.library.util.toRadians

private const val OverlapPercentage = 0.5f

fun DrawScope.drawLegs(
    circleCenterOffset: Offset = this.center,
    circleRadius: Float = this.getDefaultSheepRadius(),
    legs: List<Leg> = twoLegsStraight(),
    legColor: Color = SheepColor.Skin,
    showGuidelines: Boolean = false,
) {
    val circleDiameter = circleRadius.times(2f)

    legs.forEach { leg ->

        val legPointInCircumference = getCircumferencePointForAngle(
            angleInRadians = leg.positionAngleInCircle.toRadians(),
            radius = circleRadius,
            circleCenter = circleCenterOffset
        )

        val legOverlapY = circleDiameter.times(leg.legBodyRatioHeight).times(OverlapPercentage)

        val legSize = Size(
            width = circleDiameter.times(leg.legBodyRatioWidth),
            height = circleDiameter.times(leg.legBodyRatioHeight) + legOverlapY
        )

        val topLeft = Offset(
            x = legPointInCircumference.x - legSize.width.div(2),
            y = legPointInCircumference.y - legOverlapY
        )

        rotate(
            degrees = leg.rotationDegrees,
            pivot = legPointInCircumference
        ) {
            drawRoundRect(
                color = legColor,
                topLeft = topLeft,
                size = legSize,
                cornerRadius = CornerRadius(20f)
            )
        }

        if (showGuidelines) {
            drawLegGuideline(
                legPointInCircumference = legPointInCircumference,
                legSize = legSize,
                rotation = leg.rotationDegrees
            )
        }
    }
}

private fun DrawScope.drawLegGuideline(
    legPointInCircumference: Offset,
    legSize: Size,
    rotation: Float
) {
    drawPoints(
        points = listOf(legPointInCircumference),
        color = Color.Magenta.copy(alpha = GuidelineAlpha.normal),
        pointMode = PointMode.Points,
        cap = StrokeCap.Round,
        strokeWidth = 2.dp.toPx()
    )

    rotate(degrees = rotation, pivot = legPointInCircumference) {
        drawLine(
            color = Color.Magenta.copy(alpha = GuidelineAlpha.normal),
            start = legPointInCircumference,
            end = legPointInCircumference.copy(y = legPointInCircumference.y + legSize.height),
            strokeWidth = GuidelineStrokeWidth,
            pathEffect = GuidelineDashPattern,
        )
    }
}
