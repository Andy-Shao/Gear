package com.github.andyshao.arithmetic;

import java.util.Collection;
import java.util.Comparator;

import com.github.andyshao.data.structure.CycleLinkedElmt;
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
        public DATA data;
        public double weight;
        public VertexColor color;
        public double key;
        public MstVertex<DATA> parent;
    }

    public static class PathVertex<DATA> {
        public DATA data;
        public double weight;
        public VertexColor color;
        public double d;
        public PathVertex<DATA> parent;
    }

    public static class TspVertex<DATA> {
        public DATA data;
        public double x , y;
        public VertexColor color;
    }

    public static final <DATA> Collection<MstVertex<DATA>> mst(
        Graph<MstVertex<DATA>> graph , MstVertex<DATA> start , Collection<MstVertex<DATA>> result ,
        Comparator<MstVertex<DATA>> comparator) {
        //Initialize all of the vertices in the graph.
        boolean found = false;

        for (CycleLinkedElmt<AdjList<MstVertex<DATA>>> element = graph.graph_adjlists().head() ; element != null ; element =
            element.next()) {
            MstVertex<DATA> mst_vertex = element.data().vertex();

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
        }

        return result;
    }

    private GraphAlg() {
        throw new AssertionError("No " + GraphAlg.class + " for you!");
    }
}
