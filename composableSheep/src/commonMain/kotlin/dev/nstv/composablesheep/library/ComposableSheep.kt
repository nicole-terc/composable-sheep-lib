package dev.nstv.composablesheep.library

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.nstv.composablesheep.library.model.FluffStyle
import dev.nstv.composablesheep.library.model.Sheep
import dev.nstv.composablesheep.library.parts.drawFluff
import dev.nstv.composablesheep.library.parts.drawHead
import dev.nstv.composablesheep.library.parts.drawLegs

/**
 * A composable that draws a Sheep!
 *
 * Uses Canvas, so you MUST specify size with modifier, whether with exact sizes via Modifier.size
 * modifier, or relative to parent, via Modifier.fillMaxSize, ColumnScope.weight, etc. If parent
 * wraps this child, only exact sizes must be specified.
 *
 * @modifier - mandatory modifier to specify size strategy for this composable
 * @sheep - optional sheep model, default is a default sheep with random fluff color
 * @fluffColor - optional fluff color, overrides sheep.fluffColor
 * @headColor - optional head color, overrides sheep.headColor
 * @legColor - optional leg color, overrides sheep.legColor
 * @eyeColor - optional eye color, overrides sheep.eyeColor
 * @glassesColor - optional glasses color, overrides sheep.glassesColor
 * @glassesTranslation - optional glasses translation, overrides sheep.glassesTranslation
 * @showGuidelines - optional flag to show guidelines, default is false
 */

@Composable
fun ComposableSheep(
    modifier: Modifier,
    sheep: Sheep = remember { Sheep() },
    fluffColor: Color = sheep.fluffColor,
    headColor: Color = sheep.headColor,
    legColor: Color = sheep.legColor,
    eyeColor: Color = sheep.eyeColor,
    glassesColor: Color = sheep.glassesColor,
    glassesTranslation: Float = sheep.glassesTranslation,
    showGuidelines: Boolean = false,
) {
    ComposableSheep(
        fluffBrush = SolidColor(fluffColor),
        modifier = modifier,
        sheep = sheep,
        headColor = headColor,
        legColor = legColor,
        eyeColor = eyeColor,
        glassesColor = glassesColor,
        glassesTranslation = glassesTranslation,
        showGuidelines = showGuidelines
    )
}

/**
 * A composable that draws a Sheep!
 *
 * Uses Canvas, so you MUST specify size with modifier, whether with exact sizes via Modifier.size
 * modifier, or relative to parent, via Modifier.fillMaxSize, ColumnScope.weight, etc. If parent
 * wraps this child, only exact sizes must be specified.
 *
 * @modifier - mandatory modifier to specify size strategy for this composable
 * @fluffBrush - mandatory brush to specify fluff color
 * @sheep - optional sheep model, default is a default sheep with random fluff color
 * @headColor - optional head color, overrides sheep.headColor
 * @legColor - optional leg color, overrides sheep.legColor
 * @eyeColor - optional eye color, overrides sheep.eyeColor
 * @glassesColor - optional glasses color, overrides sheep.glassesColor
 * @glassesTranslation - optional glasses translation, overrides sheep.glassesTranslation
 * @showGuidelines - optional flag to show guidelines, default is false
 */

@Composable
fun ComposableSheep(
    modifier: Modifier,
    fluffBrush: Brush,
    sheep: Sheep = remember { Sheep() },
    headColor: Color = sheep.headColor,
    legColor: Color = sheep.legColor,
    eyeColor: Color = sheep.eyeColor,
    glassesColor: Color = sheep.glassesColor,
    glassesTranslation: Float = sheep.glassesTranslation,
    showGuidelines: Boolean = false,
) {
    Canvas(modifier = modifier) {

        drawComposableSheep(
            sheep = sheep,
            fluffBrush = fluffBrush,
            headColor = headColor,
            legColor = legColor,
            eyeColor = eyeColor,
            glassesColor = glassesColor,
            glassesTranslation = glassesTranslation,
            showGuidelines = showGuidelines,
        )
    }
}

/**
 * Extension function that draws a Sheep!
 *
 * @sheep - optional sheep model, default is a default sheep with random fluff color
 * @fluffColor - optional fluff color, overrides sheep.fluffColor
 * @headColor - optional head color, overrides sheep.headColor
 * @legColor - optional leg color, overrides sheep.legColor
 * @eyeColor - optional eye color, overrides sheep.eyeColor
 * @glassesColor - optional glasses color, overrides sheep.glassesColor
 * @glassesTranslation - optional glasses translation, overrides sheep.glassesTranslation
 * @showGuidelines - optional flag to show guidelines, default is false
 * @circleRadius - optional circle radius, default is 30% of the draw space width
 * @circleCenterOffset - optional value for the sheep fluff center, default is the draw space center
 */

fun DrawScope.drawComposableSheep(
    sheep: Sheep = Sheep(FluffStyle.Uniform(10)),
    fluffColor: Color = sheep.fluffColor,
    headColor: Color = sheep.headColor,
    legColor: Color = sheep.legColor,
    eyeColor: Color = sheep.eyeColor,
    glassesColor: Color = sheep.glassesColor,
    glassesTranslation: Float = sheep.glassesTranslation,
    showGuidelines: Boolean = false,
    circleRadius: Float = getDefaultSheepRadius(),
    circleCenterOffset: Offset = center,
) {
    drawComposableSheep(
        sheep = sheep,
        fluffBrush = SolidColor(fluffColor),
        headColor = headColor,
        legColor = legColor,
        eyeColor = eyeColor,
        glassesColor = glassesColor,
        glassesTranslation = glassesTranslation,
        showGuidelines = showGuidelines,
        circleRadius = circleRadius,
        circleCenterOffset = circleCenterOffset
    )
}

/**
 * Extension function that draws a Sheep!
 *
 * @sheep - optional sheep model, default is a default sheep with random fluff color
 * @fluffBrush - mandatory brush to specify fluff color
 * @headColor - optional head color, overrides sheep.headColor
 * @legColor - optional leg color, overrides sheep.legColor
 * @eyeColor - optional eye color, overrides sheep.eyeColor
 * @glassesColor - optional glasses color, overrides sheep.glassesColor
 * @glassesTranslation - optional glasses translation, overrides sheep.glassesTranslation
 * @showGuidelines - optional flag to show guidelines, default is false
 * @circleRadius - optional circle radius, default is 30% of the draw space width
 * @circleCenterOffset - optional value for the sheep fluff center, default is the draw space center
 */

fun DrawScope.drawComposableSheep(
    sheep: Sheep = Sheep(FluffStyle.Uniform(10)),
    fluffBrush: Brush,
    headColor: Color = sheep.headColor,
    legColor: Color = sheep.legColor,
    eyeColor: Color = sheep.eyeColor,
    glassesColor: Color = sheep.glassesColor,
    glassesTranslation: Float = sheep.glassesTranslation,
    showGuidelines: Boolean = false,
    circleRadius: Float = getDefaultSheepRadius(),
    circleCenterOffset: Offset = center,
) {
    drawLegs(
        circleCenterOffset = circleCenterOffset,
        circleRadius = circleRadius,
        legs = sheep.legs,
        legColor = legColor,
        showGuidelines = showGuidelines
    )

    drawFluff(
        circleCenterOffset = circleCenterOffset,
        circleRadius = circleRadius,
        fluffStyle = sheep.fluffStyle,
        fluffBrush = fluffBrush,
        showGuidelines = showGuidelines
    )

    drawHead(
        circleCenterOffset = circleCenterOffset,
        circleRadius = circleRadius,
        headAngle = sheep.headAngle,
        headColor = headColor,
        eyeColor = eyeColor,
        glassesColor = glassesColor,
        glassesTranslation = glassesTranslation,
        showGuidelines = showGuidelines
    )
}

internal fun DrawScope.getDefaultSheepRadius() = size.width * 0.3f
