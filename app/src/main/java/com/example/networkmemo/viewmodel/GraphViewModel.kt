package com.example.networkmemo.viewmodel
import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkmemo.OP_TIME_THRESHOLD
import com.example.networkmemo.algorithm.Algorithm
import com.example.networkmemo.algorithm.ForcedGraphAlgorithm
import com.example.networkmemo.data.entities.Edge
import com.example.networkmemo.data.entities.Node
import com.example.networkmemo.database.AppDatabase
import com.example.networkmemo.datasource.EdgeLocalSource
import com.example.networkmemo.datasource.FolderLocalSource
import com.example.networkmemo.datasource.HistoryLocalSource
import com.example.networkmemo.datasource.NodeLocalSource
import com.example.networkmemo.repository.EdgeRepository
import com.example.networkmemo.repository.FolderRepository
import com.example.networkmemo.repository.GraphRepository
import com.example.networkmemo.repository.HistoryRepository
import com.example.networkmemo.repository.NodeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

class GraphViewModel(
    application: Application
): ViewModel() {

    private var db: AppDatabase = AppDatabase.getInstance(application)
    private val nodeRepository = NodeRepository(NodeLocalSource(db.nodeDao()))
    private val edgeRepository = EdgeRepository(EdgeLocalSource(db.edgeDao()))
    private val graphRepository = GraphRepository(db, nodeRepository, edgeRepository)
    private val folderRepository = FolderRepository(FolderLocalSource(db.folderDao()))
    private val historyRepository = HistoryRepository(HistoryLocalSource(db.historyDao()))

    private val mutex = Mutex()

    private var algorithm: Algorithm = ForcedGraphAlgorithm()
    private var able = true

    private val _nodeStates = mutableListOf<MutableState<Node>>()
    private val _edgeStates = mutableListOf<MutableState<Edge>>()
    val nodeStates: List<State<Node>> = _nodeStates
    val edgeStates: List<State<Edge>> = _edgeStates

    private val a = mutableStateListOf("")

    fun setGraph() {

    }

    fun draw() {
        opAble()
        thread(start = true) {
            viewModelScope.launch {
                mutex.withLock {
                    while(able) {
                        val opTime = measureTimeMillis {
                            with(graphRepository) {
                                algorithm(nodes, edges, nodeId2Index)
                                nodes.forEachIndexed { index, node ->
                                    _nodeStates[index].value = node
                                }
                            }
                        }
                        if(opTime < OP_TIME_THRESHOLD) delay(OP_TIME_THRESHOLD - opTime)
                    }
                }
            }
        }
    }
    private fun opDisable() { able = false }
    private fun opAble() { able = true }
}
