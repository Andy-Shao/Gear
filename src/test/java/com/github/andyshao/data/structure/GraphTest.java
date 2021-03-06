package com.github.andyshao.data.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.andyshao.data.structure.Graph.BfsVertex;
import com.github.andyshao.data.structure.Graph.DfsVertex;
import com.github.andyshao.lang.StringOperation;
import com.github.andyshao.test.CompareOptions;

public class GraphTest {
    static final <DATA> Graph.BfsVertex<DATA> buildBsfvertex(DATA data) {
        Graph.BfsVertex<DATA> result = Graph.BfsVertex.defaultBfsVertex();
        result.data(data);
        return result;
    }

    static final <DATA> DfsVertex<DATA> buildDfsVertex(DATA data) {
        Graph.DfsVertex<DATA> result = DfsVertex.defaultDfsVertex();
        result.data(data);
        return result;
    }

    private volatile Graph<String> graph;

    @BeforeEach
    public void before() {
        this.graph = Graph.<String> defaultGraph(StringOperation.COMPARATOR , () -> SingleLinked.defaultSingleLinked(CycleLinkedElmt::defaultElmt));
    }

    @Test
    public void testBsf() {
        final Graph.BfsVertex<String> node1 = GraphTest.buildBsfvertex("node1");
        final Graph.BfsVertex<String> node2 = GraphTest.buildBsfvertex("node2");
        final Graph.BfsVertex<String> node3 = GraphTest.buildBsfvertex("node3");
        final Graph.BfsVertex<String> node4 = GraphTest.buildBsfvertex("node4");
        final Graph.BfsVertex<String> node5 = GraphTest.buildBsfvertex("node5");
        final Graph.BfsVertex<String> node6 = GraphTest.buildBsfvertex("node6");
        Graph<Graph.BfsVertex<String>> graph =
            Graph.<Graph.BfsVertex<String>> defaultGraph((obj1 , obj2) -> obj1.data().compareTo(obj2.data()) , () -> SingleLinked.defaultSingleLinked(CycleLinkedElmt::defaultElmt));
        graph.insVertex(node1);
        graph.insVertex(node2);
        graph.insVertex(node3);
        graph.insVertex(node4);
        graph.insVertex(node5);
        graph.insVertex(node6);
        assertEquals(graph.vcount() , 6);

        Graph.addUntowardEdge(graph , node1 , node2);
        Graph.addUntowardEdge(graph , node1 , node3);
        Graph.addUntowardEdge(graph , node2 , node3);
        Graph.addUntowardEdge(graph , node2 , node4);
        Graph.addUntowardEdge(graph , node3 , node5);
        Graph.addUntowardEdge(graph , node4 , node5);
        Graph.addUntowardEdge(graph , node5 , node6);
        assertEquals(graph.ecount() , 14);

        Queue<BfsVertex<String>> queue = new PriorityQueue<BfsVertex<String>>((obj1 , obj2) -> Integer.compare(obj1.hops() , obj2.hops()));
        Graph.bfs(graph , node6 , queue);
        assertEquals(queue.poll() , node6);
        assertEquals(queue.poll() , node5);
        assumeTrue(CompareOptions.anyOf(queue.poll(), node4, node3)); 
        assumeTrue(CompareOptions.anyOf(queue.poll(), node4, node3));
        assumeTrue(CompareOptions.anyOf(queue.poll() , node1, node2));
        assumeTrue(CompareOptions.anyOf(queue.poll() , node1, node2));
    }
    
    @Test
    public void testBuildUntowardGraph() {
        final String node1 = "node1";
        final String node2 = "node2";
        final String node3 = "node3";
        final String node4 = "node4";
        final String node5 = "node5";
        final String node6 = "node6";
        this.graph.insVertex(node1);
        this.graph.insVertex(node2);
        this.graph.insVertex(node3);
        this.graph.insVertex(node4);
        this.graph.insVertex(node5);
        this.graph.insVertex(node6);
        assertEquals(this.graph.vcount() , 6);

        Graph.addUntowardEdge(this.graph , node1 , node2);
        Graph.addUntowardEdge(this.graph , node1 , node3);
        Graph.addUntowardEdge(this.graph , node2 , node3);
        Graph.addUntowardEdge(this.graph , node2 , node4);
        Graph.addUntowardEdge(this.graph , node3 , node5);
        Graph.addUntowardEdge(this.graph , node4 , node5);
        Graph.addUntowardEdge(this.graph , node5 , node6);
        assertEquals(this.graph.ecount() , 14);

    }

    @Test
    public void testDfs() {
        final DfsVertex<String> cs100 = GraphTest.buildDfsVertex("CS100");
        final DfsVertex<String> cs200 = GraphTest.buildDfsVertex("CS200");
        final DfsVertex<String> cs300 = GraphTest.buildDfsVertex("CS300");
        final DfsVertex<String> cs150 = GraphTest.buildDfsVertex("CS1500");
        final DfsVertex<String> ma100 = GraphTest.buildDfsVertex("MA100");
        final DfsVertex<String> ma200 = GraphTest.buildDfsVertex("MA200");
        final DfsVertex<String> ma300 = GraphTest.buildDfsVertex("MA300");
        Graph<DfsVertex<String>> graph =
            Graph.<DfsVertex<String>> defaultGraph((obj1 , obj2) -> obj1.data().compareTo(obj2.data()) , () -> SingleLinked.defaultSingleLinked(CycleLinkedElmt::defaultElmt));
        graph.insVertex(cs100);
        graph.insVertex(cs200);
        graph.insVertex(cs300);
        graph.insVertex(ma100);
        graph.insVertex(ma200);
        graph.insVertex(ma300);
        assertEquals(graph.vcount() , 6);

        graph.insEdge(cs100 , cs200);
        graph.insEdge(cs200 , cs300);
        graph.insEdge(ma100 , cs300);
        graph.insVertex(cs150);
        graph.insEdge(cs300 , ma300);
        graph.insEdge(ma100 , ma200);
        graph.insEdge(ma200 , ma300);
        assertEquals(graph.ecount() , 6);

        Queue<DfsVertex<String>> queue = new SimpleQueue<>();
        Graph.dfs(graph , queue);
        DfsVertex<String> dfs = queue.peek();
        if (!dfs.data().equals(cs150.data())) {
            assertEquals(queue.poll() , ma300);
            assertTrue(CompareOptions.anyOf(queue.poll() , ma200, cs300));
            assertTrue(CompareOptions.anyOf(queue.poll() , ma100, cs200));
        } else {
            assertEquals(queue.poll() , cs150);
            assertEquals(queue.poll() , ma300);
            assertTrue(CompareOptions.anyOf(queue.poll() , ma200, cs300));
            assertTrue(CompareOptions.anyOf(queue.poll() , ma100, cs200));
        }
    }
}
