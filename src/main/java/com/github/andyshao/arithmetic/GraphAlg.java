package com.github.andyshao.arithmetic;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.github.andyshao.data.structure.Graph;
import com.github.andyshao.data.structure.Graph.AdjList;
import com.github.andyshao.data.structure.Graph.VertexColor;

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
        public VertexColor color;
        public double d;
        public DATA data;
        public PathVertex<DATA> parent;
        public double weight;
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

    private GraphAlg() {
        throw new AssertionError("No " + GraphAlg.class + " for you!");
    }
}
