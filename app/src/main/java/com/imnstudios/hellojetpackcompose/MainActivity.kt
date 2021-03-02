package com.imnstudios.hellojetpackcompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imnstudios.hellojetpackcompose.HexToJetpackColor.Companion.getColor

class HexToJetpackColor {

    companion object {
        fun getColor(colorString: String): Color {
            return Color(android.graphics.Color.parseColor("#$colorString"))
        }
    }
}


private val DarkColors = darkColors(
    primary = getColor("FFBB86FC"),
    primaryVariant = getColor("FF3700B3"),
    secondary = getColor("FF03DAC5")
)

private val LightColors = lightColors(
    primary = getColor("FF6200EE"),
    primaryVariant = getColor("FF3700B3"),
    secondary = getColor("FF03DAC5")
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(colors = colors) {
        content()
    }

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme() {
                MyScreenContent()
            }

        }
    }
}

@Composable
fun MyScreenContent(names: List<String> = List(100) { "Hello Android #$it" }) {
    val counterState = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NameList(names, Modifier.weight(1f))
        Divider(color = Color.Transparent, thickness = 15.dp)


        /*
        In Composable functions, state that can be useful to calling functions should be exposed
        because it's the only way it can be consumed or controlled—this process is called state hoisting.
         */

        Counter(
            count = counterState.value, updateCount = { newCount ->
                counterState.value = newCount
            }
        )


    }
}

@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Background {
                Greeting(name = name)
            }
            Divider(color = Color.Gray)

        }

    }
}

//@Composable
//fun Greeting(name: String) {
//    var isSelected by remember { mutableStateOf(false) }
//    val backgroundColor by
//        animateColorAsState(if (isSelected.value) Color.Red else Color.Transparent)
//    Text(
//        text = name,
//        modifier = Modifier
//            .padding(24.dp)
//            .background(color = backgroundColor)
//            .clickable(onClick = { isSelected = !isSelected }),
//        color = Color.White,
//        textAlign = TextAlign.Center
//    )
//}

@Composable
fun Greeting(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent)
    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected = !isSelected }),
    )
}


/*
State hoisting is the way to make internal state controllable by the function that called it.
You do so by exposing the state through a parameter of the controlled composable function and instantiate it externally from the controlling composable.
 */
@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = {
            updateCount(count + 1)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {
        Text("I've been clicked $count times")
    }
}

/**
 * Reusable container functions
 */
@Composable
fun Background(content: @Composable () -> Unit) {
    Surface(color = Color.Transparent, modifier = Modifier.fillMaxWidth()) {
        content()
    }
}

@Preview(showBackground = true, name = "Text preview")
@Composable
fun DefaultPreview() {
    MyScreenContent()
}

