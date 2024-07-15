package com.example.droidchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.droidchat.navigation.ChatNavHost
import com.example.droidchat.ui.ChatApp
import com.example.droidchat.ui.theme.DroidChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroidChatTheme {
                ChatApp()
            }
        }
    }
}