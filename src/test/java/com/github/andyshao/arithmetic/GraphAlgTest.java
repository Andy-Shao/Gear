package com.github.andyshao.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.arithmetic.GraphAlg.MstVertex;
import com.github.andyshao.arithmetic.GraphAlg.PathVertex;
import com.github.andyshao.arithmetic.GraphAlg.TspVertex;
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

    public static final <DATA> PathVertex<DATA> buildPathVertex(DATA data) {
        PathVertex<DATA> pathVertex = new PathVertex<DATA>();
        pathVertex.data = data;
        return pathVertex;
    }

    public static final <DATA> TspVertex<DATA> buildTspVertex(DATA data , double x , double y) {
        TspVertex<DATA> result = new TspVertex<DATA>();
        result.data = data;
        result.x = x;
        result.y = y;
        return result;
    }

    @Test
    public void testMst() {
        final Comparator<MstVertex<String>> comparator =
            (MstVertex<String> one , MstVertex<String> two) -> StringOperation.getComparator().compare(one.data ,
                two.data);
        final Graph<MstVertex<String>> graph = Graph.<MstVertex<String>> defaultGraph(comparator ,
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
        Assert.assertThat(list.toArray() , Matchers.is(new Object[] {
            a , c , b , d , f , e
        }));
    }

    @Test
    public void testShortest() {
        final Comparator<PathVertex<String>> comparator =
            (PathVertex<String> one , PathVertex<String> two) -> StringOperation.getComparator().compare(one.data ,
                two.data);
        final Graph<PathVertex<String>> graph = Graph.<PathVertex<String>> defaultGraph(comparator ,
            () -> SingleLinked.defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data)));
        final PathVertex<String> a = GraphAlgTest.buildPathVertex("a");
        final PathVertex<String> b = GraphAlgTest.buildPathVertex("b");
        final PathVertex<String> c = GraphAlgTest.buildPathVertex("c");
        final PathVertex<String> d = GraphAlgTest.buildPathVertex("d");
        final PathVertex<String> e = GraphAlgTest.buildPathVertex("e");
        final PathVertex<String> f = GraphAlgTest.buildPathVertex("f");

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
        GraphAlg.findShortest(graph , a , list , comparator);
        Assert.assertThat(list.toArray() , Matchers.is(new Object[] {
            a , c , f , b , e , d
        }));

        Map<PathVertex<String> , Collection<PathVertex<String>>> answer = new HashMap<>();
        GraphAlg.shortest(graph , a , Arrays.asList(e , d) , answer , comparator);
        Assert.assertThat(answer.get(d).toArray() , Matchers.is(new Object[] {
            a , c , f , b , d
        }));
        Assert.assertThat(answer.get(e).toArray() , Matchers.is(new Object[] {
            a , c , e
        }));
    }

    @Test
    public void testTsp() {
        final Comparator<TspVertex<String>> comparator =
            (one , two) -> StringOperation.getComparator().compare(one.data , two.data);
        final TspVertex<String> a = GraphAlgTest.buildTspVertex("a" , 2 , 1);
        final TspVertex<String> b = GraphAlgTest.buildTspVertex("b" , 5 , 2);
        final TspVertex<String> c = GraphAlgTest.buildTspVertex("c" , 1 , 3);
        final TspVertex<String> d = GraphAlgTest.buildTspVertex("d" , 4 , 3);
        final TspVertex<String> e = GraphAlgTest.buildTspVertex("e" , 6 , 3);
        final TspVertex<String> f = GraphAlgTest.buildTspVertex("f" , 2 , 4);
        final TspVertex<String> g = GraphAlgTest.buildTspVertex("g" , 5 , 5);
        final List<TspVertex<String>> list = Arrays.asList(a , b , c , d , e , f , g);

        final List<TspVertex<String>> result = new ArrayList<>();
        GraphAlg.tsp(list , a , result , comparator);
        Assert.assertThat(result.toArray() , Matchers.is(new Object[] {
            a , c , f , d , b , e , g , a
        }));
    }
}
