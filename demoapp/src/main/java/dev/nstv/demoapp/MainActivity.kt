package dev.nstv.demoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nstv.demoapp.ui.theme.ComposableSheepLibraryTheme
import dev.nstv.composablesheep.library.ComposableSheep
import dev.nstv.composablesheep.library.LoadingSheep
import dev.nstv.composablesheep.library.model.Sheep
import dev.nstv.composablesheep.library.util.Grid
import dev.nstv.composablesheep.library.util.SheepColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposableSheepLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    var fluffColor by remember { mutableStateOf(SheepColor.Magenta) }

    Column(
        verticalArrangement = Arrangement.spacedBy(Grid.Two),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ComposableSheep(
            modifier = modifier
                .size(300.dp)
                .clickable(
                    onClick = { fluffColor = SheepColor.random(fluffColor)},
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            sheep = Sheep(fluffColor = fluffColor)
        )
        LoadingSheep(
            modifier = modifier.size(300.dp),
            fluffColor = SheepColor.Blue,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposableSheepLibraryTheme {
        Content()
    }
}
