package dev.nstv.composablesheep.library

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import dev.nstv.composablesheep.library.model.Sheep
import dev.nstv.composablesheep.library.util.SheepColor

/**
 * A loading sheep that spins and scales infinitely.
 * @param modifier The modifier to apply to this layout.
 * @param fluffColor The color of the sheep's fluff.
 * @param spinning Whether or not the sheep should spin.
 * @param durationMillis The duration of the animation in milliseconds.
 * @param delayMillis The delay of the animation in milliseconds.
 */

@Composable
fun LoadingSheep(
    modifier: Modifier = Modifier,
    fluffColor: Color = SheepColor.random(),
    spinning: Boolean = true,
    durationMillis: Int = 1000,
    delayMillis: Int = 300,
) {
    val sheep = remember { Sheep(fluffColor = fluffColor) }
    val infiniteTransition = rememberInfiniteTransition(label = "Loading Sheep")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Restart
        ), label = "loadingSheep-rotation"
    )
    val animatedScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "loadingSheep-rotation"
    )
    Box(modifier) {
        Box(Modifier.fillMaxSize()) {
            ComposableSheep(
                modifier = Modifier
                    .fillMaxSize(.5f)
                    .aspectRatio(
                        1f,
                        matchHeightConstraintsFirst = true
                    )
                    .align(Alignment.BottomCenter)
                    .graphicsLayer {
                        if (spinning) {
                            transformOrigin = TransformOrigin(
                                pivotFractionX = 0.5f,
                                pivotFractionY = 0.1f,
                            )
                            rotationZ = rotation
                            scaleX = animatedScale
                            scaleY = animatedScale
                        }
                    },
                sheep = sheep,
            )
        }
    }
}