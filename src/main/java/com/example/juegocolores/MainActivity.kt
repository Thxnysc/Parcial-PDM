package com.example.juegocolores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.juegocolores.navigation.NavGraph
import com.example.juegocolores.ui.theme.JuegoColoresTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            JuegoColoresTheme {

                NavGraph()
            }
        }
    }
}