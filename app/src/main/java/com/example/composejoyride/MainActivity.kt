package com.example.composejoyride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composejoyride.ui.theme.ComposeJoyrideTheme
import com.example.composejoyride.ui.theme.Dimens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeJoyrideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Vengeful")
                    MainInfo(fullname = "Shalomeenko Andrey Alexeevich", groupNumber = "IKBO-07-21")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MainInfo(fullname: String, groupNumber: String, modifier: Modifier = Modifier) {
    Box {
        Text(
            text = "My name is $fullname and the number of my group is $groupNumber!",
            modifier = Modifier
                .padding(Dimens.paddingMedium)
                .align(Alignment.Center),
            color = Color.Cyan,
            fontSize = 32.sp,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeJoyrideTheme {
        Greeting("Android")
    }
}