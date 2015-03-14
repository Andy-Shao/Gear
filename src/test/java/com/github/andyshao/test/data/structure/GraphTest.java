package com.github.andyshao.test.data.structure;

import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.data.structure.CycleLinkedElmt;
import com.github.andyshao.data.structure.SingleLinked;

@Deprecated
public class GraphTest {
    private volatile Graph<String> graph;
    private final String node1 = "node1";
    private final String node2 = "node2";
    private final String node3 = "node3";
    private final String node4 = "node4";
    private final String node5 = "node5";
    private final String node6 = "node6";

    @Before
    public void before() {
        this.graph = Graph.<String> DEFAULT_GRAPH((obj1 , obj2) -> {
            return obj1.compareTo(obj2);
        } , () -> {
            return SingleLinked.defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data));
        });
    }

    @Test
    public void testBuildUntowardGraph() {
        this.graph.graph_ins_vertex(this.node1);
        this.graph.graph_ins_vertex(this.node2);
        this.graph.graph_ins_vertex(this.node3);
        this.graph.graph_ins_vertex(this.node4);
        this.graph.graph_ins_vertex(this.node5);
        this.graph.graph_ins_vertex(this.node6);

        Graph.ADD_UNTOWARD_EDGE(this.graph , this.node1 , this.node2);
        Graph.ADD_UNTOWARD_EDGE(this.graph , this.node1 , this.node3);
        Graph.ADD_UNTOWARD_EDGE(this.graph , this.node2 , this.node3);
        Graph.ADD_UNTOWARD_EDGE(this.graph , this.node2 , this.node4);
        Graph.ADD_UNTOWARD_EDGE(this.graph , this.node3 , this.node5);
        Graph.ADD_UNTOWARD_EDGE(this.graph , this.node4 , this.node5);
        Graph.ADD_UNTOWARD_EDGE(this.graph , this.node5 , this.node6);
    }
}
