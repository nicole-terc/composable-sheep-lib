package dev.nstv.composablesheep.library.parts

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import dev.nstv.composablesheep.library.getDefaultSheepRadius
import dev.nstv.composablesheep.library.model.FluffStyle
import dev.nstv.composablesheep.library.model.Sheep
import dev.nstv.composablesheep.library.util.FullCircleAngleInRadians
import dev.nstv.composablesheep.library.util.GuidelineAlpha
import dev.nstv.composablesheep.library.util.SheepColor
import dev.nstv.composablesheep.library.util.distanceToOffset
import dev.nstv.composablesheep.library.util.drawAxis
import dev.nstv.composablesheep.library.util.drawGrid
import dev.nstv.composablesheep.library.util.getCircumferencePointForAngle
import dev.nstv.composablesheep.library.util.getCurveControlPoint
import dev.nstv.composablesheep.library.util.getMiddlePoint
import dev.nstv.composablesheep.library.util.toRadians


@Composable
fun ComposableFluff(
    modifier: Modifier,
    sheep: Sheep,
    showGuidelines: Boolean = false,
) {
    ComposableFluff(
        modifier = modifier,
        color = SolidColor(sheep.fluffColor),
        fluffStyle = sheep.fluffStyle,
        showGuidelines = showGuidelines,
    )
}

@Composable
fun ComposableFluff(
    modifier: Modifier,
    color: Brush = SolidColor(SheepColor.random()),
    fluffStyle: FluffStyle = FluffStyle.Random(),
    showGuidelines: Boolean = false,
) {
    Canvas(modifier) {
        drawFluff(
            fluffStyle = fluffStyle,
            circleRadius = size.width * 0.5f * 0.8f, // add a padding for the fluff circles
            circleCenterOffset = center,
            fluffBrush = color,
            showGuidelines = showGuidelines,
        )
    }
}

fun DrawScope.drawFluff(
    fluffStyle: FluffStyle = FluffStyle.Random(),
    circleRadius: Float = this.getDefaultSheepRadius(),
    circleCenterOffset: Offset = this.center,
    fluffBrush: Brush = SolidColor(SheepColor.random()),
    showGuidelines: Boolean = false
) {

    val fluffPoints: List<Offset> = getFluffPoints(
        fluffPercentages = fluffStyle.fluffChunksPercentages,
        radius = circleRadius,
        circleCenter = circleCenterOffset
    )

    val fluffPath = getFluffPath(
        fluffPoints = fluffPoints,
        circleRadius = circleRadius,
        circleCenterOffset = circleCenterOffset,
    )

    drawPath(path = fluffPath, brush = fluffBrush)

    if (showGuidelines) {
        drawFluffGuidelines(
            fluffPoints = fluffPoints,
            circleRadius = circleRadius,
            circleCenterOffset = circleCenterOffset,
        )
    }
}

/**
 * Returns the coordinates (points) of the middle points between fluff chunks.
 */
fun getFluffPoints(
    fluffPercentages: List<Double>,
    radius: Float,
    circleCenter: Offset = Offset.Zero,
    totalAngleInRadians: Double = FullCircleAngleInRadians
): List<Offset> {
    val fluffPoints = mutableListOf<Offset>()

    var totalPercentage = 0.0
    fluffPercentages.forEach { fluffPercentage ->
        totalPercentage += fluffPercentage
        fluffPoints.add(
            getCircumferencePointForAngle(
                totalPercentage.div(100.0).times(totalAngleInRadians),
                radius,
                circleCenter
            )
        )
    }
    return fluffPoints
}

/**
 * Returns the path of the fluff for the given fluff points.
 * Uses quadratic brazier curves to create the fluff curves.
 */

fun getFluffPath(
    fluffPoints: List<Offset>,
    circleRadius: Float,
    circleCenterOffset: Offset,
) = Path().apply {
    var currentPoint = getCircumferencePointForAngle(
        0.0.toRadians(),
        circleRadius,
        circleCenterOffset
    )

    moveTo(currentPoint.x, currentPoint.y)

    fluffPoints.forEach { fluffPoint ->
        val controlPoint = getCurveControlPoint(currentPoint, fluffPoint, circleCenterOffset)
        quadraticBezierTo(controlPoint.x, controlPoint.y, fluffPoint.x, fluffPoint.y)
        currentPoint = fluffPoint
    }
}

/**
 * Returns the path of the fluff for the given fluff style.
 * Uses quadratic brazier curves to create the fluff curves.
 */

fun getFluffPath(
    circleRadius: Float,
    circleCenterOffset: Offset = Offset.Zero,
    fluffStyle: FluffStyle = FluffStyle.Random(),
): Path = getFluffPath(
    fluffPoints = getFluffPoints(
        fluffPercentages = fluffStyle.fluffChunksPercentages,
        radius = circleRadius,
        circleCenter = circleCenterOffset
    ),
    circleRadius = circleRadius,
    circleCenterOffset = circleCenterOffset,
)

private fun DrawScope.drawFluffGuidelines(
    fluffPoints: List<Offset>,
    circleRadius: Float,
    circleCenterOffset: Offset,
) {
    // Base Circle
    drawCircle(
        color = Color.Blue.copy(alpha = GuidelineAlpha.strong),
        center = center,
        radius = circleRadius
    )

    // Current fluff point in circumference
    var currentPointGuidelines =
        getCircumferencePointForAngle(0.0.toRadians(), circleRadius, circleCenterOffset)

    // Coordinates of middle points between 2 fluff points
    val midPoints: MutableList<Offset> = mutableListOf()

    fluffPoints.forEach { fluffPoint ->
        val middlePoint = getMiddlePoint(currentPointGuidelines, fluffPoint)

        midPoints.add(middlePoint)

        // Circles used to obtain reference point for Quadratic Bezier Curve
        drawCircle(
            color = Color.Cyan.copy(alpha = GuidelineAlpha.normal),
            radius = middlePoint.distanceToOffset(fluffPoint).div(2),
            center = middlePoint
        )

        // Lines between 2 fluff points
        drawLine(
            color = Color.Red.copy(alpha = GuidelineAlpha.strong),
            start = currentPointGuidelines,
            end = fluffPoint
        )

        currentPointGuidelines = fluffPoint
    }

    // Fluff points (start/end of fluff curves)
    drawPoints(
        fluffPoints,
        color = Color.Red,
        pointMode = PointMode.Points,
        cap = StrokeCap.Round,
        strokeWidth = 8.dp.toPx()
    )

    // Mid points between 2 fluff points
    drawPoints(
        midPoints,
        color = Color.Yellow.copy(alpha = GuidelineAlpha.normal),
        pointMode = PointMode.Points,
        cap = StrokeCap.Butt,
        strokeWidth = 8.dp.toPx()
    )

    // Draw axis at the end
    drawGrid()
    drawAxis()
}
