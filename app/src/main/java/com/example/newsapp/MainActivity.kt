package com.example.newsapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.components.AppBar
import com.example.newsapp.components.ListOfRecommendation
import com.example.newsapp.components.Slider
import com.example.newsapp.model.SliderModel
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
               GetPermission()
                Show()
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun Show() {

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(40.dp))
        AppBar()
        val slideModel: SliderModel = viewModel(factory = factory)
        Slider(slideModel)
        ListOfRecommendation(slideModel)
    }
}


val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SliderModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return SliderModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetPermission() {

   val permissionAState = rememberPermissionState(permission = android.Manifest.permission.INTERNET)
    val lifecycleOwner= LocalLifecycleOwner.current
    
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver{source, event ->  
            when(event){
                Lifecycle.Event.ON_START ->{
                    permissionAState.launchPermissionRequest()
                }
                else->{

                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if(permissionAState.status.isGranted)
    {
        Log.d("TAG" , "isGranted")
    }
    else{
        Log.d("TAG" , "is not Granted")

    }

}