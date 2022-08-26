package com.otakenne.devicehealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.otakenne.devicehealth.ui.theme.DeviceHealthTheme
import com.otakenne.devicehealthsdk.utility.sdkentry.DeviceHealthSDKEntry

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeviceHealthTheme {
                // A surface container using the 'background' color from the theme
                DeviceHealthSDKEntry.launchHomeActivity(this)
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Android")
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeviceHealthTheme {
        Greeting("Android")
    }
}