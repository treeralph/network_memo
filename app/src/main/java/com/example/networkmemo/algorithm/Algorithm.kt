package com.example.networkmemo.algorithm

import com.example.networkmemo.data.entities.Edge
import com.example.networkmemo.data.entities.Node

interface Algorithm {
    suspend operator fun invoke(
        nodes: ArrayList<Node>,
        edges: ArrayList<Edge>,
        nodeId2Index: HashMap<Long, Int>
    )
}