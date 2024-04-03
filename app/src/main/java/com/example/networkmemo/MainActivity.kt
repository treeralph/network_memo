package com.example.networkmemo

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.example.networkmemo.database.AppDatabase
import com.example.networkmemo.datasource.EdgeLocalSource
import com.example.networkmemo.datasource.FolderLocalSource
import com.example.networkmemo.datasource.HistoryLocalSource
import com.example.networkmemo.datasource.NodeLocalSource
import com.example.networkmemo.repository.EdgeRepository
import com.example.networkmemo.repository.FolderRepository
import com.example.networkmemo.repository.HistoryRepository
import com.example.networkmemo.repository.NodeRepository
import com.example.networkmemo.ui.composables.DrawNode
import com.example.networkmemo.ui.composables.GraphScaffold
import com.example.networkmemo.ui.theme.NetWorkMemoTheme
import com.example.networkmemo.viewmodel.GraphViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    companion object {
        init { System.loadLibrary("networkmemo") }
    }

    private lateinit var viewModel: GraphViewModel
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        thread(start = true) {
            viewModel = GraphViewModel(application)
        }

        setContent {
            NetWorkMemoTheme {

            }
        }
    }
}

