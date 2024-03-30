package dev.nstv.composablesheep.library.model

import androidx.compose.ui.graphics.Color
import dev.nstv.composablesheep.library.util.SheepColor

const val DefaultHeadRotationAngle = 5f

/**
 * A sheep model.
 *
 * @param fluffStyle - optional fluff style, default is Uniform with 10 fluff chunks
 * @param headAngle - optional head rotation angle, default is 5f
 * @param glassesTranslation - optional glasses translation, default is 0f
 * @param legs - optional legs, default is two straight legs, other util option is fourLegs
 * @param fluffColor - optional fluff color, default is random
 * @param headColor - optional head color, default is SheepColor.skin (Gray)
 * @param legColor - optional leg color, default is the same as headColor
 * @param eyeColor - optional eye color, default is SheepColor.eye (Orange)
 * @param glassesColor - optional glasses color, default is black
 */
data class Sheep(
    val fluffStyle: FluffStyle = FluffStyle.Uniform(),
    val headAngle: Float = DefaultHeadRotationAngle,
    val glassesTranslation: Float = 0f,
    val legs: List<Leg> = twoLegsStraight(),
    val fluffColor: Color = SheepColor.random(),
    val headColor: Color = SheepColor.Skin,
    val legColor: Color = headColor,
    val eyeColor: Color = SheepColor.Eye,
    val glassesColor: Color = SheepColor.Black
)
