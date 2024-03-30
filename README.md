# Composable Sheep Library!

<i>What nobody asked, now in your hands (and Maven)!</i>

This library includes the standalone Compose functions and DrawScope extensions for Composable Sheep and Loading Sheep!

## Installation

### Kotlin Multiplatform Projects

Just add the dependency in your app `build.gradle` file. Currently supported: Android, iOS & desktop (no wasm!).

```Kotlin
commonMain.dependencies {
    implementation("dev.nstv:composablesheep:1.0.0")
}
```

### Android Native

Just add the dependency in your app `build.gradle` file.

```Kotlin
dependencies {
    implementation("dev.nstv:composablesheep:1.0.0")
}
```

## How to use

> <div style="padding:12px">⚠️ <b>IMPORTANT:</b> ComposableSheep use <a href="https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Canvas">Canvas</a> under the hood. It's mandatory to specify the <b>size</b> strategy of the composables (<i>size</i>, <i>fillMaxSize</i>,...)! </div>

### Composable Sheep

In order to add sheep everywhere, you only need to provide a modifier with a size strategy defined, like <i>size()</i>. This will create a sheep with a randomized fluff color.

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
)
```

If you want more control of your sheep, and don't want to create a random one every time, you can always remember it and pass it along.

```Kotlin
val sheep = remember { Sheep() }

ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = sheep
)
```

You can always override the sheep colors of your ComposableSheep.

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = sheep,
    fluffColor = Color.Black,
    headColor = Color.LightGray,
    legColor = Color.Yellow,
    eyeColor = Color.Red,
    glassesColor = Color.Magenta,
)
```

#### DrawScope Extensions

Composable Sheep can also be drawn using drawComposableSheep extension function

```Kotlin
Box(modifier = Modifier.size(300.dp)
    .drawBehind {
        drawComposableSheep(sheep = sheep)
    }
)
```

### Fluff

If a Sheep is too much to handle, you have the option of only adding the Fluff

```Kotlin
ComposableFluff(
    modifier = Modifier.size(300.dp),
)
```

## Customization

### Color

In order to make them more flexible, the colors can be edited independently of the sheep model. This make it easy to "temporary" override colors, keeping the original sheep intact.

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = sheep,
    fluffColor = Color.Black,
    headColor = Color.LightGray,
    legColor = Color.Yellow,
    eyeColor = Color.Red,
    glassesColor = Color.Magenta,
)
```

If you want to take a more creative approach, the fluff can also be defined using a [Brush](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Brush)

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    fluffBrush = radialGradient(colors = listOf(Color.Black, Color.Yellow)),
)
```

### Sheep

The Sheep model contains both the base colors and fluff style used to build a ComposableSheep. You can modify all the values to craft the sheep of your dreams from the beginning!

```Kotlin
data class Sheep(
    val fluffStyle: FluffStyle = FluffStyle.Random(),
    val headAngle: Float = DefaultHeadRotationAngle,
    val glassesTranslation: Float = 0f,
    val legs: List<Leg> = twoLegsStraight(),
    val fluffColor: Color = SheepColor.random(),
    val headColor: Color = SheepColor.Skin,
    val legColor: Color = headColor,
    val eyeColor: Color = SheepColor.Eye,
    val glassesColor: Color = SheepColor.Black
)
```

#### FluffStyle

FluffStyle is used to define the style of the fluff, how each of the fluff chunks (each "bump") are built. Each fluff chunk takes a percentage of the full circle to built its angle. The bigger the percentage, the bigger the fluff chunk will be.

The different types of fluffy styles are:

<details open>
<summary><b>Uniform</b> (default)</summary>
The fluff is divided into a number of chunks of equal size.

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = Sheep(fluffStyle = FluffStyle.Uniform(numberOfFluffChunks = 10))
)
```

- `numberOfFluffChunks` represents how many fluff chunks (each "bump") the final fluff will have. In this example, I'm defining a size of 10, meaning that the final body will have 10 fluff chunks of 10% (100%/10) of the body each. In angles, each chunk takes 360°/10 = 36° degrees.
</details>

<details open>
<summary><b>UniformIntervals</b></summary>

The fluff is divided into a number of chunks of iterating size according to the given intervals.

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = Sheep(
        fluffStyle = FluffStyle.UniformIntervals(
            percentageIntervals = listOf(5.0, 15.0)
        )
    )
)
```

- `percentageIntervals` represents the sequence of percentages the fluff chunks will follow. It will repeat the sequence until the 100% of the fluff is drawn.
</details>

<details open>
<summary><b>Random</b></summary>
The fluff is divided into a number of chunks of random size.

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = Sheep(
        fluffStyle = FluffStyle.Random(
            minPercentage = 10.0,
            maxPercentage = 20.0
        )
    )
)
```

- `minPercentage` & `maxPercentage` represent the range that each fluff chunk can take.
</details>

<details open>
<summary><b>Manual</b></summary>

The fluff is divided into a number of chunks of size defined by the user.

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = Sheep(
        fluffStyle = FluffStyle.Manual(
            fluffChunksPercentages = listOf(5.0, 15.0, 10.0, 20.0, 50.0)
        )
    )
)
```

- `fluffChunksPercentages` represents the percentages the fluff chunks will follow. It will not repeat the sequence and will create a final chunk with the leftover percentage if needed. For the best results, ensure tht the percentages add up to 100%.
</details>

#### Legs

You can potentially define how the legs should be built. Two helper functions are already provided:

<details open>
<summary><b>twoLegsStraight</b> (default)</summary>

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = Sheep(
        legs = twoLegsStraight()
    )
)
```

</details>

<details open>
<summary><b>fourLegs</b></summary>

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = Sheep(
        legs = fourLegs()
    )
)
```

</details>

### Guidelines

## License

```
   Copyright 2024 Nicole Terc

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
