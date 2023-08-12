package com.example.duckie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transition.CrossfadeTransition
import coil.transition.Transition
import coil.util.DebugLogger
import coil.util.Logger
import com.example.duckie.ui.theme.DuckieTheme
import com.example.duckie.ui.theme.Purple40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val api: DuckieApi = DuckieApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val url = remember { mutableStateOf("") }
            val scope = rememberCoroutineScope { Dispatchers.Main }
            val imageLoader = ImageLoader(LocalContext.current)
                .newBuilder()
                .logger(DebugLogger())
                .transitionFactory(CrossfadeTransition.Factory())
                .build()

            DuckieTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                getQuack(scope) { newUrl ->
                                    url.value = newUrl
                                }
                            },
                            content = {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = ""
                                )
                            }
                        )
                    }
                ) { paddingValues ->
                        AsyncImage(
                            model = url.value,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Purple40),
                            imageLoader = imageLoader,
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    paddingValues
                }
            }
        }
    }

    private fun getQuack(coroutineScope: CoroutineScope, onSuccess: (String) -> Unit) {
        coroutineScope.launch {
            val quack = api.quack()
            if (quack != null) {
                onSuccess(quack.url)
            }
        }
    }
}
