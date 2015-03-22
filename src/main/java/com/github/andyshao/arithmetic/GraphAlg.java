package com.github.andyshao.arithmetic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.andyshao.data.structure.CycleLinkedElmt;
import com.github.andyshao.data.structure.Graph;
import com.github.andyshao.data.structure.Graph.AdjList;
import com.github.andyshao.data.structure.Graph.VertexColor;
import com.github.andyshao.data.structure.SingleLinked;
import com.github.andyshao.data.structure.Stack;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 15, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class GraphAlg {
    public static class MstVertex<DATA> {
        public static final <DATA> void setUntowardEdge(
            Graph<MstVertex<DATA>> graph , MstVertex<DATA> one , MstVertex<DATA> two , double weight) {
            Graph.addUntowardEdge(graph , one , two);
            MstVertex.setUntowardWeight(one , two , weight);
        }

        public static final <DATA> void setUntowardWeight(MstVertex<DATA> one , MstVertex<DATA> two , double weight) {
            one.weight.put(two , weight);
            two.weight.put(one , weight);
        }

        public VertexColor color;
        public DATA data;
        public double key;
        public MstVertex<DATA> parent;
        public final Map<MstVertex<DATA> , Double> weight = new HashMap<>();
    }

    public static class PathVertex<DATA> {
        public static final <DATA> void setDoubleEdge(
            Graph<PathVertex<DATA>> graph , PathVertex<DATA> one , PathVertex<DATA> two , double one2two ,
            double two2one) {
            Graph.addUntowardEdge(graph , one , two);
            one.weight.put(two , one2two);
            two.weight.put(one , two2one);
        }

        public static final <DATA> void setEdge(
            Graph<PathVertex<DATA>> graph , PathVertex<DATA> start , PathVertex<DATA> end , double weight) {
            graph.insEdge(start , end);
            start.weight.put(end , weight);
        }

        public VertexColor color;
        public double d;
        public DATA data;
        public PathVertex<DATA> parent;
        public final Map<PathVertex<DATA> , Double> weight = new HashMap<>();
    }

    public static class TspVertex<DATA> {
        public VertexColor color;
        public DATA data;
        public double x , y;
    }

    /**
     * (最小生成树)<br>
     * all the answers will store in the 'result'.In the 'result', the root note
     * of 'result' is the 'start' which has a parent note is null.the
     * other notes' parent is the previous note of 'result'.
     * 
     * @param graph {@link Graph} is a untoward graph
     * @param start start vertex
     * @param result the structure of mst vertex
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return the 'result'
     */
    public static final <DATA> Collection<MstVertex<DATA>> mst(
        Graph<MstVertex<DATA>> graph , MstVertex<DATA> start , Collection<MstVertex<DATA>> result ,
        Comparator<MstVertex<DATA>> comparator) {
        //Initialize all of the vertices in the graph.
        {
            boolean found = false;
            for (AdjList<MstVertex<DATA>> element : graph.adjlists()) {
                MstVertex<DATA> mst_vertex = element.vertex();

                if (comparator.compare(mst_vertex , start) == 0) {
                    //Initialize the start vertex.
                    mst_vertex.color = VertexColor.WHITE;
                    mst_vertex.key = 0;
                    mst_vertex.parent = null;
                    found = true;
                } else {
                    //Initialize vertices other than the start vertex.
                    mst_vertex.color = VertexColor.WHITE;
                    mst_vertex.key = Double.MAX_VALUE;
                    mst_vertex.parent = null;
                }
            }
            //Return if the start vertex was not found.
            if (!found) throw new GraphAlgException("Can't find out the " + start);
        }

        //Use prim's algorithm to compute a minimum spanning tree.
        for (int i = 0 ; i < graph.vcount() ; i++) {
            AdjList<MstVertex<DATA>> adjlist = null;
            //Select the white vertex with the smallest key value.
            double minimum = Double.MAX_VALUE;
            for (AdjList<MstVertex<DATA>> element : graph.adjlists()) {
                MstVertex<DATA> mst_vertex = element.vertex();

                if (mst_vertex.color == VertexColor.WHITE && mst_vertex.key < minimum) {
                    minimum = mst_vertex.key;
                    adjlist = element;
                }
            }
            //Color the selected vertex black.
            adjlist.vertex().color = VertexColor.BLACK;
            result.add(adjlist.vertex());

            //Traverse each vertex adjacent to the selected vertex.
            for (MstVertex<DATA> adj_vertex : adjlist.adjacent())
                if (adj_vertex.color == VertexColor.WHITE && adj_vertex.weight.get(adjlist.vertex()) < adj_vertex.key) {
                    adj_vertex.key = adj_vertex.weight.get(adjlist.vertex());
                    adj_vertex.parent = adjlist.vertex();
                }
        }

        return result;
    }

    static final <DATA> void scam(
        List<PathVertex<DATA>> shortest , PathVertex<DATA> end , Collection<PathVertex<DATA>> result ,
        Comparator<PathVertex<DATA>> comparator) {
        int index = shortest.size() - 1;
        boolean found = false;

        while (index >= 0)
            if (comparator.compare(shortest.get(index--) , end) == 0) {
                found = true;
                break;
            }
        if (!found) return;
        while (index >= 0) {
            PathVertex<DATA> parent = shortest.get(index--);
            VERTEX: for (PathVertex<DATA> vertex : parent.weight.keySet())
                if (comparator.compare(vertex , end) == 0) {
                    result.add(parent);
                    end = parent;
                    break VERTEX;
                }
        }
    }

    /**
     * (最短路径)<br>
     * all of the answer will store in 'result'. the parent note of the start
     * note of 'result''s is null. the others' parent note is the previous
     * position in 'result'.
     * 
     * @param graph {@link Graph} is a toward graph
     * @param start the start vertex
     * @param result the collection which should return to.
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return result
     */
    public static final <DATA> Collection<PathVertex<DATA>> shortest(
        Graph<PathVertex<DATA>> graph , PathVertex<DATA> start , Collection<PathVertex<DATA>> result ,
        Comparator<PathVertex<DATA>> comparator) {
        //Initialize all of the vertices in the graph.
        {
            boolean found = false;
            for (AdjList<PathVertex<DATA>> element : graph.adjlists()) {
                PathVertex<DATA> pth_vertex = element.vertex();
                if (comparator.compare(pth_vertex , start) == 0) {
                    //Initialize the start vertex.
                    pth_vertex.color = VertexColor.WHITE;
                    pth_vertex.d = 0;
                    pth_vertex.parent = null;
                    found = true;
                } else {
                    //Initialize vertices other than the start vertex.
                    pth_vertex.color = VertexColor.WHITE;
                    pth_vertex.d = Double.MAX_VALUE;
                    pth_vertex.parent = null;
                }
            }
            if (!found) throw new GraphAlgException("The start point doesn't exist in graph!");
        }

        for (int i = 0 ; i < graph.vcount() ; i++) {
            AdjList<PathVertex<DATA>> adjlist = null;
            //Select the white vertex with the smallest shortest-path estimate.
            {
                double minimum = Double.MAX_VALUE;
                for (AdjList<PathVertex<DATA>> element : graph.adjlists()) {
                    PathVertex<DATA> pth_vertex = element.vertex();
                    if (pth_vertex.color == VertexColor.WHITE && pth_vertex.d < minimum) {
                        minimum = pth_vertex.d;
                        adjlist = element;
                    }
                }
            }

            //Color the selected vertex black.
            adjlist.vertex().color = VertexColor.BLACK;
            result.add(adjlist.vertex());

            //Traverse each vertex adjacent to the selected vertex.
            for (PathVertex<DATA> adj_vertex : adjlist.adjacent()) {
                PathVertex<DATA> u = adjlist.vertex();
                double weight1 = adjlist.vertex().weight.get(adj_vertex);
                //Relax an edge between two vertices u and v.
                if (adj_vertex.d > u.d + weight1) {
                    adj_vertex.d = u.d + weight1;
                    adj_vertex.parent = u;
                }
            }
        }

        return result;
    }

    public static final <DATA> Map<PathVertex<DATA> , Collection<PathVertex<DATA>>> shortest2end(
        Graph<PathVertex<DATA>> graph , PathVertex<DATA> start , Collection<PathVertex<DATA>> ends ,
        Comparator<PathVertex<DATA>> comparator) {
        final Map<PathVertex<DATA> , Collection<PathVertex<DATA>>> result = new HashMap<>();
        final List<PathVertex<DATA>> answer = new ArrayList<>();
        GraphAlg.shortest(graph , start , answer , comparator);

        for (PathVertex<DATA> end : ends) {
            final Stack<PathVertex<DATA>> temp =
                Stack.defaultStack(SingleLinked.defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data)));
            temp.add(end);
            GraphAlg.scam(answer , end , temp , comparator);
            result.put(end , temp);
        }
        return result;
    }

    private GraphAlg() {
        throw new AssertionError("No " + GraphAlg.class + " for you!");
    }
}
