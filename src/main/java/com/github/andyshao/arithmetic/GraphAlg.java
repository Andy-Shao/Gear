package com.github.andyshao.arithmetic;

import java.util.Collection;
import java.util.Comparator;

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
        public VertexColor color;
        public DATA data;
        public double key;
        public MstVertex<DATA> parent;
        public double weight;
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

    public static final <DATA> Collection<MstVertex<DATA>> mst(
        Graph<MstVertex<DATA>> graph , MstVertex<DATA> start , Collection<MstVertex<DATA>> result ,
        Comparator<MstVertex<DATA>> comparator) {
        //Initialize all of the vertices in the graph.
        boolean found = false;

        for (AdjList<MstVertex<DATA>> element : graph.graph_adjlists()) {
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

        //Use prim's algorithm to compute a minimum spanning tree.
        for (int i = 0 ; i < graph.graph_vcount() ; i++) {
            //Select the white vertex with the smallest key value.
            double minimum = Double.MAX_VALUE;
            AdjList<MstVertex<DATA>> adjlist = null;
            for (AdjList<MstVertex<DATA>> element : graph.graph_adjlists()) {
                MstVertex<DATA> mst_vertex = element.vertex();

                if (mst_vertex.color == VertexColor.WHITE && mst_vertex.key < minimum) {
                    minimum = mst_vertex.key;
                    adjlist = element;
                }
            }
            //Color the selected vertex black.
            adjlist.vertex().color = VertexColor.BLACK;

            //Traverse each vertex adjacent to the selected vertex.
            for (MstVertex<DATA> adj_vertex : adjlist.adjacent())
                //Find the adjacent vertex in the list of adjacency-list structures.
                ELEMENT: for (AdjList<MstVertex<DATA>> element : graph.graph_adjlists()) {
                    MstVertex<DATA> mst_vertex = element.vertex();

                    if (comparator.compare(mst_vertex , adj_vertex) == 0) {
                        //Decide whether to chagne the key value and parent of the
                        //addjacent vertex in the list of adjacency-list structures.
                        if (mst_vertex.color == VertexColor.WHITE && adj_vertex.weight < mst_vertex.key) {
                            mst_vertex.key = adj_vertex.weight;
                            mst_vertex.parent = adjlist.vertex();
                        }
                        break ELEMENT;
                    }
                }
        }

        //Load the minimum spanning tree into a list.
        for (AdjList<MstVertex<DATA>> element : graph.graph_adjlists()) {
            //Load each black vertex from the list of adjacency-list structures.
            MstVertex<DATA> mst_vertex = element.vertex();

            if (mst_vertex.color == VertexColor.BLACK) result.add(mst_vertex);
        }

        return result;
    }

    private GraphAlg() {
        throw new AssertionError("No " + GraphAlg.class + " for you!");
    }
}
