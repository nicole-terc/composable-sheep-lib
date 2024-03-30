# Composable Sheep Library!

<i>What nobody asked, now in your hands (and Maven)!</i>

This library includes the standalone Compose functions and DrawScope extensions for Composable Sheep, ComposableFluff and Loading Sheep!

| ComposableSheep | ComposableFluff | LoadingSheep |
| --- | --- | --- |
| <img  width=300 height=300 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/5d7dae8c-5e8c-4cca-8ea9-49dcb6f4de07" > | <img width=300 height=300 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/0b63c1c6-9b2d-429c-8c5f-a1dc577ebcb8"> | <img  width=300 height=300 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/fb9bd8af-b20d-4565-ac9a-07a77ce3fbec"> |

If you're interested in learning more about the Sheep, take a look at the original project of [Composable Sheep](https://github.com/nicole-terc/composable-sheep)!

## Installation

### Kotlin Multiplatform Projects

Just add the dependency in your common module `build.gradle` file. Currently supported: Android, iOS & desktop (no wasm!).

```Kotlin
commonMain.dependencies {
    implementation("dev.nstv:composablesheep:1.0.0")
}
```

### Android Native

Just add the dependency in your module `build.gradle` file.

```Kotlin
dependencies {
    implementation("dev.nstv:composablesheep:1.0.0")
}
```

## How to use

> ComposableSheep use <a href="https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Canvas">Canvas</a> under the hood! <br/> It's mandatory to specify the <b>size</b> strategy of the composables (size, fillMaxSize,...) 

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

### Loading Sheep
Renders a spinning ComposableSheep. For a best result, defining the composable size strategy is recommended. 

```Kotlin
    LoadingSheep(
        modifier = Modifier.size(300.dp),
        spinning = true,
        fluffColor = sheep.fluffColor
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
<img  width=400 height=400 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/c8e9ee6b-8682-4733-ae67-2eee340c1e36" >


If you want to take a more creative approach, the fluff can also be defined using a [Brush](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Brush)

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    fluffBrush = radialGradient(colors = listOf(Color.Black, Color.Yellow)),
)
```
<img width=400 height=400 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/81b5aeda-f1e1-4e8a-b6c3-fe7ab259abf1">


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

<details>
<summary><b>Uniform</b> (default)</summary>
The fluff is divided into a number of chunks of equal size.

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = Sheep(fluffStyle = FluffStyle.Uniform(numberOfFluffChunks = 10))
)
```

- `numberOfFluffChunks` represents how many fluff chunks (each "bump") the final fluff will have. In this example, I'm defining a size of 10, meaning that the final body will have 10 fluff chunks of 10% (100%/10) of the body each. In angles, each chunk takes 360°/10 = 36° degrees.

<img width=400 height=400 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/8ef6f78f-8cd7-4666-b9a9-dc7ff74b3a1b">

</details>

<details>
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

<img width=400 height=400 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/8653c9a6-e4fe-426c-bcd1-068c5cfeef72">

</details>

<details>
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

<img width=400 height=400 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/fba89e71-06a5-4845-b496-d54bc9bd9c2f">

</details>

<details>
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

<img width=400 height=400 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/7fe46ad1-4fd8-496e-b999-2acb2c8f43e8">

</details>

#### Legs

You can potentially define how the legs should be built. Two helper functions are already provided:

<details>
<summary><b>twoLegsStraight</b> (default)</summary>

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = Sheep(
        legs = twoLegsStraight()
    )
)
```
<img width=400 height=400 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/f9399502-2cac-4463-8e43-9667734470bd">

</details>

<details>
<summary><b>fourLegs</b></summary>

```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = Sheep(
        legs = fourLegs()
    )
)
```
<img width=400 height=400 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/a56f4d74-a360-4547-b39b-400f9e95c2c6">

</details>

### Guidelines
Most functions accept the optional flag to showGuidelines. In case you want to take a look at the shapes being used, just change the value to true!
```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
    showGuidelines = true,
)
```
<img width=400 height=400 src="https://github.com/nicole-terc/composable-sheep-lib/assets/26582588/f567e5a8-b46f-445e-be31-408496fdf82e">

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
