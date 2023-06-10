package com.seozen.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.seozen.example.ui.Permission

import com.seozen.example.ui.theme.PhotoAppWithCameraX_ComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoAppWithCameraX_ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
                    if (imageUri != EMPTY_IMAGE_URI) {
                        Box() {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = rememberAsyncImagePainter(imageUri),
                                contentDescription = "Captured image"
                            )
                            Button(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                onClick = {
                                    imageUri = EMPTY_IMAGE_URI
                                }
                            ) {
                                Text("Remove image")
                            }
                        }
                    } else {
                        var showGallerySelect by remember { mutableStateOf(false) }
                        if (showGallerySelect) {
                            GallerySelect(
                                onImageUri = { uri ->
                                    showGallerySelect = false
                                    imageUri = uri
                                }
                            )
                        } else {
                            Box() {
                                CameraCapture(
                                    onImageFile = { file ->
                                        imageUri = file.toUri()
                                    }
                                )
                                Button(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(4.dp),
                                    onClick = {
                                        showGallerySelect = true
                                    }
                                ) {
                                    Text("Select from Gallery")
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PhotoAppWithCameraX_ComposeTheme {
        Greeting("Android")
    }
}