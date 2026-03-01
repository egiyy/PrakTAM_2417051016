package com.example.praktam_2417051016

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.praktam_2417051016.fitur.DashboardScreen
import com.example.praktam_2417051016.ui.theme.PrakTAM_2417051016Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PrakTAM_2417051016Theme() {
                CoffeeApp()
            }
        }
    }
}

@Composable
fun CoffeeApp() {
    Scaffold { innerPadding ->
        DashboardScreen(innerPadding)
    }
}