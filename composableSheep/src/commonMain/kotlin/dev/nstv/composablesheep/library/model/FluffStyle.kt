package dev.nstv.composablesheep.library.model

import kotlin.random.Random

private const val MinAnglePercentage = 10.0
private const val MaxAnglePercentage = 20.0

/**
 * FluffStyle is used to define the style of the fluff.
 * The different types of fluffy styles are:
 * - Uniform: The fluff is divided into a number of chunks of equal size.
 * - UniformIntervals: The fluff is divided into a number of chunks of iterating size according to the given intervals.
 * - Random: The fluff is divided into a number of chunks of random size.
 * - Manual: The fluff is divided into a number of chunks of size defined by the user.
 */
sealed class FluffStyle(
    fluffChunksPercentages: List<Double>? = null,
) {
    val fluffChunksPercentages: List<Double> by lazy {
        fluffChunksPercentages ?: buildFluffPercentages()
    }

    data class Uniform(val numberOfFluffChunks: Int = 10) : FluffStyle()

    data class UniformIntervals(
        val percentageIntervals: List<Double> = listOf(
            MinAnglePercentage,
            MaxAnglePercentage
        )
    ) : FluffStyle()

    data class Random(
        val minPercentage: Double = MinAnglePercentage,
        val maxPercentage: Double = MaxAnglePercentage
    ) : FluffStyle()

    class Manual(
        fluffChunksPercentages: List<Double>
    ) : FluffStyle(fluffChunksPercentages)
}

private fun FluffStyle.buildFluffPercentages(
    totalPercentage: Double = 100.0,
): List<Double> {
    val angleChunks: MutableList<Double> = mutableListOf()

    var currentSum = 0.0
    while (currentSum < totalPercentage) {
        var angleChunk = getNextAngleChunkPercentage(
            fluffStyle = this,
            totalPercentage = totalPercentage,
            index = angleChunks.size
        )
        if (currentSum + angleChunk > totalPercentage) {
            angleChunk = totalPercentage - currentSum
        }
        angleChunks.add(angleChunk)
        currentSum += angleChunk
    }
    return angleChunks
}

private fun getNextAngleChunkPercentage(
    fluffStyle: FluffStyle,
    totalPercentage: Double,
    index: Int
): Double =
    when (fluffStyle) {
        is FluffStyle.Uniform -> totalPercentage.div(fluffStyle.numberOfFluffChunks)
        is FluffStyle.UniformIntervals -> fluffStyle.percentageIntervals[index.mod(fluffStyle.percentageIntervals.size)]
        is FluffStyle.Random -> Random.nextDouble(
            fluffStyle.minPercentage,
            fluffStyle.maxPercentage
        )

        is FluffStyle.Manual -> fluffStyle.fluffChunksPercentages.getOrElse(index) { 0.0 }
    }
