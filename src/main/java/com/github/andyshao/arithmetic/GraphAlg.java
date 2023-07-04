package com.github.andyshao.arithmetic;

import com.github.andyshao.data.structure.CycleLinkedElmt;
import com.github.andyshao.data.structure.Graph;
import com.github.andyshao.data.structure.Graph.AdjList;
import com.github.andyshao.data.structure.Graph.VertexColor;
import com.github.andyshao.data.structure.SingleLinked;
import com.github.andyshao.data.structure.Stack;

import java.util.*;

/**
 * 
 * Title:<br>
 * Descript:图算法<br>
 * Copyright: Copryright(c) Mar 15, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class GraphAlg {
    /**
     * MstVertex
     * @param <DATA> data type
     */
    public static class MstVertex<DATA> {
        public static final <DATA> void setUntowardEdge(Graph<MstVertex<DATA>> graph , MstVertex<DATA> one , MstVertex<DATA> two , double weight) {
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

    /**
     * Path vertex
     * @param <DATA> data type
     */
    public static class PathVertex<DATA> {
        public static final <DATA> void setDoubleEdge(Graph<PathVertex<DATA>> graph , PathVertex<DATA> one , PathVertex<DATA> two , double one2two , double two2one) {
            Graph.addUntowardEdge(graph , one , two);
            one.weight.put(two , one2two);
            two.weight.put(one , two2one);
        }

        public static final <DATA> void setEdge(Graph<PathVertex<DATA>> graph , PathVertex<DATA> start , PathVertex<DATA> end , double weight) {
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
    static final <DATA> Collection<PathVertex<DATA>>
        findShortest(Graph<PathVertex<DATA>> graph , PathVertex<DATA> start , final Collection<PathVertex<DATA>> result , Comparator<PathVertex<DATA>> comparator) {
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
    public static final <DATA> Collection<MstVertex<DATA>>
        mst(Graph<MstVertex<DATA>> graph , MstVertex<DATA> start , final Collection<MstVertex<DATA>> result , Comparator<MstVertex<DATA>> comparator) {
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

    /**
     * (最短路径)<br>
     * all of the answer will store in 'result'. the parent note of the start
     * note of 'result''s is null. the others' parent note is the previous
     * position in 'result'.
     * 
     * @param graph {@link Graph} is a toward graph
     * @param start the start vertex
     * @param ends the end lists
     * @param result the answer which should be returned
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return {@link Map}
     */
    public static final <DATA> Map<PathVertex<DATA> , Collection<PathVertex<DATA>>> shortest(
        Graph<PathVertex<DATA>> graph , PathVertex<DATA> start , Collection<PathVertex<DATA>> ends , final Map<PathVertex<DATA> , Collection<PathVertex<DATA>>> result ,
        Comparator<PathVertex<DATA>> comparator) {
        final List<PathVertex<DATA>> answer = new ArrayList<>();
        GraphAlg.findShortest(graph , start , answer , comparator);

        for (PathVertex<DATA> end : ends) {
            final Stack<PathVertex<DATA>> temp = Stack.defaultStack(SingleLinked.defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data)));
            temp.add(end);
            {
                PathVertex<DATA> target = end;
                int index = answer.size() - 1;
                {
                    boolean found = false;
                    while (index >= 0)
                        if (comparator.compare(answer.get(index--) , target) == 0) {
                            found = true;
                            break;
                        }
                    if (!found) throw new GraphAlgException();
                }
                while (index >= 0) {
                    PathVertex<DATA> parent = answer.get(index--);
                    VERTEX: for (PathVertex<DATA> vertex : parent.weight.keySet())
                        if (comparator.compare(vertex , target) == 0) {
                            temp.add(parent);
                            target = parent;
                            break VERTEX;
                        }
                }
            }
            result.put(end , temp);
        }
        return result;
    }

    /**
     * (旅行商问题)<br>
     * Can't find out the best solution of these questions
     * 
     * @param vertices vertex
     * @param start start position
     * @param result result lists
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return result
     */
    public static final <DATA> Collection<TspVertex<DATA>>
        tsp(Collection<TspVertex<DATA>> vertices , TspVertex<DATA> start , final Collection<TspVertex<DATA>> result , Comparator<TspVertex<DATA>> comparator) {
        TspVertex<DATA> tsp_start = null;
        double x = 0 , y = 0;
        {
            boolean found = false;
            for (TspVertex<DATA> tsp_vertex : vertices)
                if (comparator.compare(tsp_vertex , start) == 0) {
                    result.add(tsp_vertex);

                    //Save the start vertex and its coordinates.
                    tsp_start = tsp_vertex;
                    x = tsp_vertex.x;
                    y = tsp_vertex.y;

                    //Color the start vertex black.
                    tsp_vertex.color = VertexColor.BLACK;
                    found = true;
                } else tsp_vertex.color = VertexColor.WHITE;//Color all other vertices white. 
            if (!found) throw new GraphAlgException();
        }

        for (int i = 0 ; i < vertices.size() - 1 ; i++) {
            //Select the white vertex closest to the previous vertex in the tour.
            double minimum = Double.MAX_VALUE;
            TspVertex<DATA> selection = null;
            for (TspVertex<DATA> tsp_vertex : vertices)
                if (tsp_vertex.color == VertexColor.WHITE) {
                    double distance = Math.sqrt(Math.pow(tsp_vertex.x - x , 2.0) + Math.pow(tsp_vertex.y - y , 2.0));
                    if (distance < minimum) {
                        minimum = distance;
                        selection = tsp_vertex;
                    }
                }
            //Save the coordinates of the selected vertex.
            x = selection.x;
            y = selection.y;

            //Color the selected vertex black.
            selection.color = VertexColor.BLACK;

            //Insert the selected vertex into the tour.
            result.add(selection);
        }

        //Insert the start vertex again to complete the tour.
        result.add(tsp_start);
        return result;
    }

    private GraphAlg() {
        throw new AssertionError("No " + GraphAlg.class + " for you!");
    }
}
