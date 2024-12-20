package com.enterprise.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.enterprise.canvas.ui.theme.CanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasTheme {
                CanvasApp()
            }
        }
    }
}

@Composable
fun CanvasApp() {
    Scaffold(modifier = Modifier.systemBarsPadding().fillMaxSize()) { innerPadding ->
        val viewModel = viewModel<DrawingViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DrawingCanvas(
                paths = state.paths,
                currentPath = state.currentPath,
                onAction = viewModel::onAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            CanvasControls(
                selectedColor = state.selectedColor,
                colors = allColors,
                onSelectColor = {
                    viewModel.onAction(DrawingAction.OnSelectColor(it))
                },
                onClearCanvas = {
                    viewModel.onAction(DrawingAction.OnClearCanvasClick)
                }
            )
        }
    }
}
