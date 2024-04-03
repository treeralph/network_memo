package com.example.networkmemo.algorithm

import com.example.networkmemo.data.entities.Edge
import com.example.networkmemo.data.entities.Node

class ForcedGraphAlgorithm: Algorithm {

    override suspend operator fun invoke(
        nodes: ArrayList<Node>,
        edges: ArrayList<Edge>,
        nodeId2Index: HashMap<Long, Int>
    ) = operate(nodes, edges, nodeId2Index)

    private external suspend fun operate(
        nodes: ArrayList<Node>,
        edges: ArrayList<Edge>,
        nodeId2Index: HashMap<Long, Int>,
    )
}