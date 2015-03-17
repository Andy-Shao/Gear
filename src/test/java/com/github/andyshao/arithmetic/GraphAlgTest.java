package com.github.andyshao.arithmetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.arithmetic.GraphAlg.MstVertex;
import com.github.andyshao.data.structure.CycleLinkedElmt;
import com.github.andyshao.data.structure.Graph;
import com.github.andyshao.data.structure.SingleLinked;
import com.github.andyshao.lang.StringOperation;

public class GraphAlgTest {

    @Test
    public void testMst() {
        Comparator<MstVertex<String>> mstVertexComparator =
            (MstVertex<String> one , MstVertex<String> two) -> StringOperation.getComparator().compare(one.data ,
                two.data);
        final Graph<MstVertex<String>> graph =
            Graph.defaultGraph(mstVertexComparator ,
                () -> SingleLinked.defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data)));
        final MstVertex<String> a = buildMstVertex("a");
        final MstVertex<String> b = buildMstVertex("b");
        final MstVertex<String> c = buildMstVertex("c");
        final MstVertex<String> d = buildMstVertex("d");
        final MstVertex<String> e = buildMstVertex("e");
        final MstVertex<String> f = buildMstVertex("f");
        graph.graph_ins_vertex(a);
        graph.graph_ins_vertex(b);
        graph.graph_ins_vertex(c);
        graph.graph_ins_vertex(d);
        graph.graph_ins_vertex(e);
        graph.graph_ins_vertex(f);

        MstVertex.setUntowardWeight(a , b , 7);
        MstVertex.setUntowardWeight(a , c , 4);
        MstVertex.setUntowardWeight(b , c , 6);
        MstVertex.setUntowardWeight(b , d , 2);
        MstVertex.setUntowardWeight(b , f , 4);
        MstVertex.setUntowardWeight(c , f , 8);
        MstVertex.setUntowardWeight(c , e , 9);
        MstVertex.setUntowardWeight(d , f , 7);
        MstVertex.setUntowardWeight(e , f , 1);
        Graph.addUntowardEdge(graph , a , b);
        Graph.addUntowardEdge(graph , a , c);
        Graph.addUntowardEdge(graph , b , c);
        Graph.addUntowardEdge(graph , b , d);
        Graph.addUntowardEdge(graph , b , f);
        Graph.addUntowardEdge(graph , c , f);
        Graph.addUntowardEdge(graph , c , e);
        Graph.addUntowardEdge(graph , d , f);
        Graph.addUntowardEdge(graph , e , f);

        List<MstVertex<String>> list = new ArrayList<>();
        GraphAlg.mst(graph , a , list , mstVertexComparator);
        Iterator<MstVertex<String>> iterator = list.iterator();
        Assert.assertThat(iterator.next() , Matchers.is(a));
        Assert.assertThat(iterator.next() , Matchers.is(c));
        Assert.assertThat(iterator.next() , Matchers.is(b));
        Assert.assertThat(iterator.next() , Matchers.is(d));
        Assert.assertThat(iterator.next() , Matchers.is(f));
        Assert.assertThat(iterator.next() , Matchers.is(e));
    }

    static <DATA> MstVertex<DATA> buildMstVertex(DATA data) {
        MstVertex<DATA> mstVertex = new MstVertex<>();
        mstVertex.data = data;
        return mstVertex;
    }
}