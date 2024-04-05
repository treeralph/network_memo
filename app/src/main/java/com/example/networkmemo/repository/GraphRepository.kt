package com.example.networkmemo.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.networkmemo.data.LinkCapsule
import com.example.networkmemo.data.entities.Edge
import com.example.networkmemo.data.entities.Node
import com.example.networkmemo.database.AppDatabase
import com.example.networkmemo.linkParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class GraphRepository(
    private val db: AppDatabase,
    private val nodeRepository: NodeRepository,
    private val edgeRepository: EdgeRepository
) {
    companion object {
        const val TAG = "GraphRepository"
    }

    val nodes = ArrayList<Node>()
    val edges = ArrayList<Edge>()
    val nodeId2Index = HashMap<Long, Int>()

    var currentFolderId: Long = -1

    suspend fun initGraphByFolder(folderId: Long) {
        currentFolderId = folderId
        nodes.clear()
        edges.clear()
        nodes.addAll(nodeRepository.getAllNodesByFolder(folderId))
        edges.addAll(edgeRepository.getAllEdgesByFolder(folderId))
        nodes.forEachIndexed { index, node ->
            nodeId2Index[node.id] = index
        }
    }
    suspend fun deleteNode(node: Node) {
        db.runInTransaction {
            /** delete edge & node ( including _edges, _nodes, _nodeId2Index ) */
            val targetIndex = nodes.size - 1
            nodeRepository.deleteNode(node)
            edgeRepository.deleteEdgesByNodeId(node.id)
            swapNode(nodeId2Index[node.id]!!, targetIndex) // send target node to list tail

            edges.removeIf { it.node1 == node.id || it.node2 == node.id }
            nodes.removeAt(targetIndex)

            nodeId2Index.remove(node.id)
        }
    }

    /**
     * @param link 노드에 연결된 링크
     * @param onSuccess db에 업로드를 완료하고 ui state update를 위한 callback
     * */
    suspend fun addNode(link: String, onSuccess: (Node) -> Unit) {
        val linkCapsule = try {
            linkParser(link)
        } catch(e: Exception) {
            LinkCapsule(title = link)
        }
        val id = nodeRepository.insertNode(
            x = Random.nextDouble(-30.0, 30.0),
            y = Random.nextDouble(-30.0, 30.0),
            imgUri = linkCapsule.imageUrl,
            linkUrl = link,
            content = linkCapsule.title,
            description = linkCapsule.description,
            folder = currentFolderId
        )
        val node = nodeRepository.getNodeById(id)
        nodes.add(node)
        nodeId2Index[id] = nodes.size - 1
        onSuccess(node)

        Log.i(TAG, "addNode: Success: $node")
    }

    suspend fun addEdge(node1: Long, node2: Long, onSuccess: (Edge) -> Unit) {
        if(edgeRepository.isEdge(node1, node2).isEmpty()) {
            val edgeId = edgeRepository.insertEdge(node1, node2, currentFolderId)
            nodes[nodeId2Index[node1]!!].mass += 1
            nodes[nodeId2Index[node2]!!].mass += 1
            onSuccess(edgeRepository.getEdgeById(edgeId))
        }
    }

    suspend fun editNode(node: Node) {
        db.nodeDao().updateNodes(listOf(node))
        nodes[nodeId2Index[node.id]!!] = node.copy()
    }

    /**
     * @param index1 swap을 수행할 노드1의 index
     * @param index2 swap을 수행할 노드2의 index
     * */
    private fun swapNode(index1: Int, index2: Int) {
        val temp1 = nodes[index1].copy()
        val temp2 = nodes[index2].copy()
        nodes[index2] = temp1
        nodes[index1] = temp2
        nodeId2Index[temp1.id] = index2
        nodeId2Index[temp2.id] = index1
    }
}
