package com.github.andyshao.data.structure;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.andyshao.lang.Cleanable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 18, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public interface Graph<D> extends Cleanable {

    public interface AdjList<DATA> {
        public static <D> AdjList<D> defaultAdjList(Supplier<Set<D>> setFactory) {
            return new AdjList<D>() {
                private final Set<D> adjacent = setFactory.get();
                private D vertex;

                @Override
                public Set<D> adjacent() {
                    return this.adjacent;
                }

                @Override
                public D vertex() {
                    return this.vertex;
                }

                @Override
                public void vertex(D vertex) {
                    this.vertex = vertex;
                }
            };
        }

        public Set<DATA> adjacent();

        public default void free() {
            this.vertex(null);
            this.adjacent().clear();
        }

        public DATA vertex();

        public void vertex(DATA vertex);
    }

    /**
     * 
     * Title:<br>
     * Descript: Define a structure for vertices in a breadth-first search.<br>
     * Copyright: Copryright(c) Feb 26, 2015<br>
     * Encoding:UNIX UTF-8
     * 
     * @author Andy.Shao
     *
     * @param <DATA> data
     */
    public interface BfsVertex<DATA> {
        public class MyBfsVertex<DAT> implements BfsVertex<DAT> {
            private VertexColor color;
            private DAT data;
            private int hops;

            @Override
            public VertexColor color() {
                return this.color;
            }

            @Override
            public void color(VertexColor color) {
                this.color = color;
            }

            @Override
            public DAT data() {
                return this.data;
            }

            @Override
            public void data(DAT data) {
                this.data = data;
            }

            @Override
            public int hops() {
                return this.hops;
            }

            @Override
            public void hops(int hops) {
                this.hops = hops;
            }

            @Override
            public String toString() {
                return "MyBfsVertex [color=" + this.color + ", data=" + this.data + ", hops=" + this.hops + "]";
            }
        }

        public static <DAT> MyBfsVertex<DAT> defaultBfsVertex() {
            return new MyBfsVertex<>();
        }

        VertexColor color();

        void color(VertexColor color);

        DATA data();

        void data(DATA data);

        int hops();

        void hops(int hops);
    }

    /**
     * 
     * Title:<br>
     * Descript: Define a structure for vertices in a depth-first search.<br>
     * Copyright: Copryright(c) Feb 27, 2015<br>
     * Encoding:UNIX UTF-8
     * 
     * @author Andy.Shao
     *
     * @param <DATA> data
     */
    public interface DfsVertex<DATA> {
        public class MyDfsVertex<DAT> implements DfsVertex<DAT> {
            private VertexColor color;
            private DAT data;

            @Override
            public VertexColor color() {
                return this.color;
            }

            @Override
            public void color(VertexColor color) {
                this.color = color;
            }

            @Override
            public DAT data() {
                return this.data;
            }

            @Override
            public void data(DAT data) {
                this.data = data;
            }

            @Override
            public String toString() {
                return "MyDfsVertex [data=" + this.data + ", color=" + this.color + "]";
            }

        }

        public static <DAT> DfsVertex<DAT> defaultDfsVertex() {
            return new MyDfsVertex<>();
        }

        public VertexColor color();

        public void color(VertexColor color);

        public DATA data();

        public void data(DATA data);
    }

    public class MyGraph<DATA> implements Graph<DATA> {
        private long actionAcount;
        protected Supplier<AdjList<DATA>> adjListFactory = () -> {
            return AdjList.<DATA> defaultAdjList(() -> {
                return new HashSet<>();
            });
        };
        protected Linked<AdjList<DATA> , CycleLinkedElmt<AdjList<DATA>>> adjlists;
        private final Comparator<DATA> comparator;
        protected int ecount;
        protected final Supplier<Linked<AdjList<DATA> , CycleLinkedElmt<AdjList<DATA>>>> linkedFactory;
        protected int vcount;

        public MyGraph(
            Comparator<DATA> comparator , Supplier<Linked<AdjList<DATA> , CycleLinkedElmt<AdjList<DATA>>>> linkedFactory) {
            this.linkedFactory = linkedFactory;
            this.comparator = comparator;
            this.adjlists = this.linkedFactory.get();
            this.vcount = 0;
            this.ecount = 0;
            this.actionAcount = 0;
        }

        @Override
        public Graph.AdjList<DATA> adjlist(DATA data) {
            CycleLinkedElmt<AdjList<DATA>> element;

            //Locate the adjacency list for the vertex.
            element = this.search(data , (elmt) -> {
            });

            //Return if the vertex was not found.
            if (element == null) return null;

            //Pass back the adjacency list for the vertex.
            return element.data();
        }

        @Override
        public Linked<AdjList<DATA> , CycleLinkedElmt<AdjList<DATA>>> adjlists() {
            return this.adjlists;
        }

        @Override
        public void clear() {
            this.vcount = 0;
            this.ecount = 0;
            this.adjlists.clear();
            this.actionAcount++;
        }

        @Override
        public int ecount() {
            return this.ecount;
        }

        @Override
        public Supplier<AdjList<DATA>> getAdjListFactory() {
            return this.adjListFactory;
        }

        @Override
        public void insEdge(DATA data1 , DATA data2) {
            CycleLinkedElmt<AdjList<DATA>> element;

            //Do not allow insertion of an edge without both its vertices in the graph.
            element = this.search(data2 , (elmt) -> {
            });
            if (element == null) throw new GraphOperationException("Can't find out the data2 (" + data2
                + ") in the graph.");

            element = this.search(data1 , (elmt) -> {
            });
            if (element == null) throw new GraphOperationException("Can't find out the data1 (" + data1
                + ") in the graph.");

            //Insert the second vertex into the adjacency list of the first vertex.
            element.data().adjacent().add(data2);

            //Adjust the edge count to account for the inserted edge.
            this.ecount++;
        }

        @Override
        public void insVertex(DATA data) {
            AdjList<DATA> adjlist;

            //Do not allow the insertion of duplicate vertices.
            if (this.search(data , (elmt) -> {
            }) != null) throw new GraphOperationException("Do not allow the insertion of duplicate vertices.");

            //Insert the vertex.
            adjlist = this.adjListFactory.get();

            adjlist.vertex(data);
            this.adjlists.insNext(this.adjlists.tail() , adjlist);

            //Adjust the vertex count to account for the inserted vertex.
            this.vcount++;
        }

        @Override
        public boolean isAdjacent(DATA data1 , DATA data2) {
            CycleLinkedElmt<AdjList<DATA>> element;

            element = this.search(data1 , (elmt) -> {
            });

            //Return if the first vertex was not found.
            if (element == null) throw new GraphOperationException(data1 + "can't find out.");

            //Return whether the second vertex is in the adjacency list of the first.
            return element.data().adjacent().contains(data2);
        }

        @Override
        public boolean match(DATA data1 , DATA data2) {
            if (this.comparator.compare(data1 , data2) == 0) return true;
            else return false;
        }

        @Override
        public void remEdge(DATA data1 , DATA data2) {
            CycleLinkedElmt<AdjList<DATA>> element;

            //Locate the adjacency list for the first vertex.
            element = this.search(data1 , (elmt) -> {
            });

            if (element == null) throw new GraphOperationException("Can't not find out data1 " + data1);

            //Remove the second vertex from the adjacency list of the first vertex.
            element.data().adjacent().remove(data2);

            //Adjust the edge count to account for the removed edge.
            this.ecount--;
        }

        @SuppressWarnings({ "unchecked" , "unused" })
        @Override
        public DATA remVertex(final DATA data) {
            CycleLinkedElmt<AdjList<DATA>> element , prev;
            AdjList<DATA> adjList;
            DATA result = data;

            //Traverse each adjacency list and the vertices it contains.
            prev = null;

            {
                final Object[] object = new Object[1];
                element = this.search(data , (elmt) -> {
                    if (!Graph.MyGraph.this.match(data , elmt.data().vertex())) object[0] = elmt;
                });
                prev = (CycleLinkedElmt<Graph.AdjList<DATA>>) object[0];
            }
            if (element.data().adjacent().contains(data)) throw new GraphOperationException(
                "Do not allow removal of the vertex if it is an adjecency list.");
            //Return if the vertex was not found.
            if (element == null) return result;
            if (element.data().adjacent().size() > 0) throw new GraphOperationException(
                "Do not allow removal of the vertex if its adjacency list is not emtpy.");
            //Remove the vertex.
            adjList = this.adjlists.remNext(prev);
            result = adjList.vertex();
            adjList.free();
            //Adjust the vertex count to account for the removed vertex.
            this.vcount--;

            return result;
        }

        /**
         * search {@link CycleLinkedElmt} by data.
         * 
         * @param data data
         * @param consumer operation
         * @return if can't find out anything return null.
         */
        protected CycleLinkedElmt<AdjList<DATA>> search(DATA data , Consumer<CycleLinkedElmt<AdjList<DATA>>> consumer) {
            CycleLinkedElmt<AdjList<DATA>> result = null;

            for (CycleLinkedElmt<AdjList<DATA>> element = this.adjlists.head() ; element != null ; element =
                element.next()) {
                consumer.accept(element);
                if (this.match(data , element.data().vertex())) {
                    result = element;
                    break;
                }
            }

            return result;
        }

        @Override
        public void setAdjListFactory(Supplier<Graph.AdjList<DATA>> adjListFactory) {
            if (this.actionAcount != 0) throw new SecurityException("Only could set field before insert & remove.");
            this.adjListFactory = adjListFactory;
        }

        @Override
        public int vcount() {
            return this.vcount;
        }
    }

    public enum VertexColor {
        BLACK , GRAY , WHITE
    }

    public static <DATA> void addUntowardEdge(Graph<DATA> graph , DATA data1 , DATA data2) {
        graph.insEdge(data1 , data2);
        graph.insEdge(data2 , data1);
    }

    /**
     * breadth-first search
     * 
     * @param graph {@link Graph}
     * @param start the start side
     * @param result the {@link Collection} which should return
     * @param <DATA> data type
     * @return the result
     */
    public static <DATA> Collection<BfsVertex<DATA>> bfs(
        Graph<BfsVertex<DATA>> graph , BfsVertex<DATA> start , Collection<BfsVertex<DATA>> result) {
        Queue<AdjList<BfsVertex<DATA>>> queue = new SimpleQueue<>();
        //Initialize the queue with the adjacency list of the start vertex.
        AdjList<BfsVertex<DATA>> clr_adjlist = graph.adjlist(start);
        queue.offer(clr_adjlist);

        //Initialize all of the vertices in the graph.
        for (CycleLinkedElmt<AdjList<BfsVertex<DATA>>> element = graph.adjlists().head() ; element != null ; element =
            element.next()) {
            BfsVertex<DATA> vertex = element.data().vertex();
            if (graph.match(vertex , start)) {
                //Initialize the start vertex.
                vertex.color(VertexColor.GRAY);
                vertex.hops(0);
            } else {
                //Initialize vertices other than the start vertex.
                vertex.color(VertexColor.WHITE);
                vertex.hops(-1);
            }
        }

        //Perform breath-first search.
        while (queue.size() > 0) {
            AdjList<BfsVertex<DATA>> adjlist = queue.peek();

            //Traverse each vertex in the current adjacency list.
            for (BfsVertex<DATA> adj_vertex : adjlist.adjacent()) {
                //Determine the color of the next adjacent vertex.
                clr_adjlist = graph.adjlist(adj_vertex);
                BfsVertex<DATA> clr_vertex = clr_adjlist.vertex();

                //Color each white vertex gray and enqueue its adjacency list.
                if (clr_vertex.color() == VertexColor.WHITE) {
                    clr_vertex.color(VertexColor.GRAY);
                    clr_vertex.hops(adjlist.vertex().hops() + 1);
                    queue.offer(clr_adjlist);
                }
            }

            //Dequeue the current adjacency list and color its vertex black.
            if ((adjlist = queue.poll()) != null) adjlist.vertex().color(VertexColor.BLACK);
        }

        queue.clear();

        //Pass back the hop count for each vertex in a list.
        for (CycleLinkedElmt<AdjList<BfsVertex<DATA>>> element = graph.adjlists().head() ; element != null ; element =
            element.next()) {
            //Skip vertices that were not visited (those with hop counts of -1).
            BfsVertex<DATA> vertex = element.data().vertex();

            if (vertex.hops() != -1) result.add(vertex);
        }

        return result;
    }

    public static <DATA> Graph<DATA> defaultGraph(
        Comparator<DATA> comparator ,
        Supplier<Linked<AdjList<DATA> , CycleLinkedElmt<AdjList<DATA>>>> singleLinkedFactory) {
        return new MyGraph<DATA>(comparator , singleLinkedFactory);
    }

    public static <DATA> Collection<DfsVertex<DATA>> dfs(
        Graph<DfsVertex<DATA>> graph , Collection<DfsVertex<DATA>> result) {
        DfsVertex<DATA> vertex;
        CycleLinkedElmt<AdjList<DfsVertex<DATA>>> element;

        //Initialize all of the vertices in the graph.
        for (element = graph.adjlists().head() ; element != null ; element = element.next()) {
            vertex = element.data().vertex();
            vertex.color(VertexColor.WHITE);
        }

        //Perform depth-first search.
        for (element = graph.adjlists().head() ; element != null ; element = element.next()) {
            //Ensure that every component of unconnected graphs is searched.
            vertex = element.data().vertex();

            if (vertex.color() == VertexColor.WHITE) Graph.dfsMain(graph , element.data() , result);
        }

        return result;
    }

    public static <DATA> Collection<DfsVertex<DATA>> dfsMain(
        Graph<DfsVertex<DATA>> graph , AdjList<DfsVertex<DATA>> adjlist , Collection<DfsVertex<DATA>> result) {
        //Color the vertex gray and traverse its adjacency list.
        adjlist.vertex().color(VertexColor.GRAY);

        for (DfsVertex<DATA> adj_vertex : adjlist.adjacent()) {
            AdjList<DfsVertex<DATA>> clr_adjlist = graph.adjlist(adj_vertex);

            DfsVertex<DATA> clr_vertex = clr_adjlist.vertex();

            //Move one vertex deeper when the next adjacent vertex is white.
            if (clr_vertex.color() == VertexColor.WHITE) Graph.dfsMain(graph , clr_adjlist , result);
        }

        //Color the current vertex black and make it first in the list.
        adjlist.vertex().color(VertexColor.BLACK);

        result.add(adjlist.vertex());

        return result;
    }

    /**
     * find the {@link AdjList} by data
     * 
     * @param data data
     * @return if can't find out return null.
     */
    public AdjList<D> adjlist(final D data);

    public Linked<AdjList<D> , CycleLinkedElmt<AdjList<D>>> adjlists();

    public int ecount();

    public Supplier<AdjList<D>> getAdjListFactory();

    public void insEdge(final D data1 , final D data2);

    public void insVertex(final D data);

    public boolean isAdjacent(final D data1 , final D data2);

    public boolean match(D data1 , D data2);

    public void remEdge(final D data1 , final D data2);

    public D remVertex(D data);

    public void setAdjListFactory(Supplier<AdjList<D>> adjListFactory);

    public int vcount();
}
