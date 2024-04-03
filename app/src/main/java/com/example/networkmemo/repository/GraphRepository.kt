package com.example.networkmemo.repository

import com.example.networkmemo.data.entities.Edge
import com.example.networkmemo.data.entities.Node

class GraphRepository(
    nodeRepository: NodeRepository,
    edgeRepository: EdgeRepository
) {
    val nodes = ArrayList<Node>()
    val edges = ArrayList<Edge>()

}