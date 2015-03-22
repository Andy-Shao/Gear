package com.github.andyshao.arithmetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.arithmetic.GraphAlg.MstVertex;
import com.github.andyshao.arithmetic.GraphAlg.PathVertex;
import com.github.andyshao.data.structure.CycleLinkedElmt;
import com.github.andyshao.data.structure.Graph;
import com.github.andyshao.data.structure.SingleLinked;
import com.github.andyshao.lang.StringOperation;

public class GraphAlgTest {

    static <DATA> MstVertex<DATA> buildMstVertex(DATA data) {
        MstVertex<DATA> mstVertex = new MstVertex<>();
        mstVertex.data = data;
        return mstVertex;
    }

    @Test
    public void testShortest() {
        final Comparator<PathVertex<String>> comparator =
            (PathVertex<String> one , PathVertex<String> two) -> StringOperation.getComparator().compare(one.data ,
                two.data);
        final Graph<PathVertex<String>> graph =
            Graph.defaultGraph(comparator ,
                () -> SingleLinked.defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data)));
        final PathVertex<String> a = buildPathVertex("a");
        final PathVertex<String> b = buildPathVertex("b");
        final PathVertex<String> c = buildPathVertex("c");
        final PathVertex<String> d = buildPathVertex("d");
        final PathVertex<String> e = buildPathVertex("e");
        final PathVertex<String> f = buildPathVertex("f");
        
        graph.insVertex(a);
        graph.insVertex(b);
        graph.insVertex(c);
        graph.insVertex(d);
        graph.insVertex(e);
        graph.insVertex(f);
        PathVertex.setEdge(graph , a , b , 8);
        PathVertex.setEdge(graph , a , c , 4);
        PathVertex.setEdge(graph , b , c , 6);
        PathVertex.setEdge(graph , b , d , 2);
        PathVertex.setEdge(graph , c , f , 1);
        PathVertex.setEdge(graph , c , e , 4);
        PathVertex.setEdge(graph , e , f , 5);
        PathVertex.setEdge(graph , f , b , 2);
        PathVertex.setEdge(graph , f , d , 7);
        
        List<PathVertex<String>> list = new ArrayList<>();
        GraphAlg.shortest(graph , a , list , comparator);
        Iterator<PathVertex<String>> iterator = list.iterator();
        Assert.assertThat(iterator.next() , Matchers.is(a));
        Assert.assertThat(iterator.next() , Matchers.is(c));
        Assert.assertThat(iterator.next() , Matchers.is(f));
        Assert.assertThat(iterator.next() , Matchers.is(b));
        Assert.assertThat(iterator.next() , Matchers.is(e));
        Assert.assertThat(iterator.next() , Matchers.is(d));
    }

    public static final <DATA> PathVertex<DATA> buildPathVertex(DATA data) {
        PathVertex<DATA> pathVertex = new PathVertex<DATA>();
        pathVertex.data = data;
        return pathVertex;
    }

    @Test
    public void testMst() {
        final Comparator<MstVertex<String>> comparator =
            (MstVertex<String> one , MstVertex<String> two) -> StringOperation.getComparator().compare(one.data ,
                two.data);
        final Graph<MstVertex<String>> graph =
            Graph.defaultGraph(comparator ,
                () -> SingleLinked.defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data)));
        final MstVertex<String> a = GraphAlgTest.buildMstVertex("a");
        final MstVertex<String> b = GraphAlgTest.buildMstVertex("b");
        final MstVertex<String> c = GraphAlgTest.buildMstVertex("c");
        final MstVertex<String> d = GraphAlgTest.buildMstVertex("d");
        final MstVertex<String> e = GraphAlgTest.buildMstVertex("e");
        final MstVertex<String> f = GraphAlgTest.buildMstVertex("f");
        graph.insVertex(a);
        graph.insVertex(b);
        graph.insVertex(c);
        graph.insVertex(d);
        graph.insVertex(e);
        graph.insVertex(f);

        MstVertex.setUntowardEdge(graph , a , b , 7);
        MstVertex.setUntowardEdge(graph , a , c , 4);
        MstVertex.setUntowardEdge(graph , b , c , 6);
        MstVertex.setUntowardEdge(graph , b , d , 2);
        MstVertex.setUntowardEdge(graph , b , f , 4);
        MstVertex.setUntowardEdge(graph , c , f , 8);
        MstVertex.setUntowardEdge(graph , c , e , 9);
        MstVertex.setUntowardEdge(graph , d , f , 7);
        MstVertex.setUntowardEdge(graph , e , f , 1);

        List<MstVertex<String>> list = new ArrayList<>();
        GraphAlg.mst(graph , a , list , comparator);
        Iterator<MstVertex<String>> iterator = list.iterator();
        Assert.assertThat(iterator.next() , Matchers.is(a));
        Assert.assertThat(iterator.next() , Matchers.is(c));
        Assert.assertThat(iterator.next() , Matchers.is(b));
        Assert.assertThat(iterator.next() , Matchers.is(d));
        Assert.assertThat(iterator.next() , Matchers.is(f));
        Assert.assertThat(iterator.next() , Matchers.is(e));
    }
}
