# Composable Sheep Library!

<i>What nobody asked, now in your hands (and Maven)!</i>

This library includes the standalone Compose functions and DrawScope extensions for Composable Sheep and Loading Sheep!

## Installation

### Kotlin Multiplatform projects

Just add the dependency in your app `build.gradle` file

```
commonMain.dependencies {
    implementation(dev.nstv:composablesheep-library)
}
```

### Android

Just add the dependency in your app `build.gradle` file

```
dependencies {
    implementation(dev.nstv:composablesheep-library-android)
}
```

### iOS

To add the dependency for iOS projects, follow these steps:

1. Open your Xcode project.
2. Navigate to your project's target settings.
3. Go to the "General" tab.
4. Scroll down to the "Frameworks, Libraries, and Embedded Content" section.
5. Click on the "+" button to add a new framework or library.
6. Select "Add Other..." from the dropdown menu.
7. Navigate to the location where you have the Composable Sheep Library framework.
8. Select the framework file and click "Open".
9. Make sure the framework is added to the "Frameworks, Libraries, and Embedded Content" section.
10. Build and run your iOS project.

That's it! The Composable Sheep Library dependency should now be added to your iOS project.

## How to use

### Composable Sheep

In order to add sheep everywhere, you only need to provide a modifier with a defined size. This will create a sheep with a randomized fluff color.
```Kotlin
ComposableSheep(
    modifier = Modifier.size(300.dp),
)
            
```

If you want more control of your sheep, and don't want to create a random one every time, you can always remember it and pass it along

```Kotlin
val sheep = remember { Sheep() }

ComposableSheep(
    modifier = Modifier.size(300.dp),
    sheep = sheep
)
```

You can always override the sheep colors of your ComposableSheep
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

## Customization

### Color Overrides

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

### Sheep

The Sheep model contains both the base colors and fluff style used to build a ComposableSheep. You can modify all the values to craft the sheep of your dreams from the beginning!

```
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
FluffStyle is used to define the style of the fluff, how each of the "bumps" are built. The different types of fluffy styles are:
 - Uniform: The fluff is divided into a number of chunks of equal size.
 - UniformIntervals: The fluff is divided into a number of chunks of iterating size according to the given intervals.
 - Random: The fluff is divided into a number of chunks of random size.
 - Manual: The fluff is divided into a number of chunks of size defined by the user.

#### Legs
You can potentially define how the legs should be built. Two helper functions are already provided:
- twoLegsStraight (default)
- fourLegs
