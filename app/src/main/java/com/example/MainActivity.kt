package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.data.GaroBibleDatabase
import com.example.data.GaroBibleRepository
import com.example.ui.GaroBibleApp
import com.example.ui.GaroBibleViewModel
import com.example.ui.GaroBibleViewModelFactory
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Room Database
        val database = GaroBibleDatabase.getDatabase(applicationContext)
        val dao = database.bibleDao()
        val repository = GaroBibleRepository(applicationContext, dao)

        // Instantiate ViewModel
        val factory = GaroBibleViewModelFactory(application, repository)
        val viewModel = ViewModelProvider(this, factory)[GaroBibleViewModel::class.java]

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GaroBibleApp(viewModel = viewModel)
                }
            }
        }
    }
}
