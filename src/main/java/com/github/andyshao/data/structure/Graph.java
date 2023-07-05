package com.github.andyshao.data.structure;

import com.github.andyshao.lang.Cleanable;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
public interface Graph<D> extends Cleanable, Serializable {

    /**
     * AdjList
     * @param <DATA> data type
     */
    public interface AdjList<DATA> extends Serializable{
        /**
         * Get default {@link AdjList}
         * @param setFactory {@link Set} factory
         * @return {@link AdjList}
         * @param <D> data type
         */
        public static <D> AdjList<D> defaultAdjList(Supplier<Set<D>> setFactory) {
            return new AdjList<D>() {
                @Serial
                private static final long serialVersionUID = 8557667818400609180L;
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

        /**
         * adjacent
         * @return {@link Set}
         */
        public Set<DATA> adjacent();

        /**
         * clean the space
         */
        public default void free() {
            this.vertex(null);
            this.adjacent().clear();
        }

        /**
         * Get the vertex
         * @return the vertex data
         */
        public DATA vertex();

        /**
         * Set the vertex
         * @param vertex the vertex data
         */
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
    public interface BfsVertex<DATA> extends Serializable{
        /**
         * Default Breath First Vertex
         * @param <DAT> data type
         */
        public class MyBfsVertex<DAT> implements BfsVertex<DAT> {
            @Serial
            private static final long serialVersionUID = 6404213558812061680L;
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

        /**
         * Get default {@link BfsVertex}
         * @return {@link BfsVertex}
         * @param <DAT> data type
         */
        public static <DAT> MyBfsVertex<DAT> defaultBfsVertex() {
            return new MyBfsVertex<>();
        }

        /**
         * Get the color of {@link BfsVertex}
         * @return {@link VertexColor}
         */
        VertexColor color();

        /**
         * Set the color of the {@link BfsVertex}
         * @param color {@link VertexColor}
         */
        void color(VertexColor color);

        /**
         * Get the data of the {@link BfsVertex}
         * @return the data
         */
        DATA data();

        /**
         * Set the data
         * @param data the data
         */
        void data(DATA data);

        /**
         * get hops
         * @return hops
         */
        int hops();

        /**
         * set hops
         * @param hops the hops
         */
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
    public interface DfsVertex<DATA> extends Serializable{
        /**
         * Default Deep first Vertex
         * @param <DAT> data type
         */
        public class MyDfsVertex<DAT> implements DfsVertex<DAT> {
            @Serial
            private static final long serialVersionUID = 8854641678739879112L;
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

        /**
         * Get the default {@link DfsVertex}
         * @return {@link DfsVertex}
         * @param <DAT> data type
         */
        public static <DAT> DfsVertex<DAT> defaultDfsVertex() {
            return new MyDfsVertex<>();
        }

        /**
         * Get the color of the {@link DfsVertex}
         * @return {@link VertexColor}
         */
        public VertexColor color();

        /**
         * Set the color of the {@link DfsVertex}
         * @param color {@link DfsVertex}
         */
        public void color(VertexColor color);

        /**
         * Get the data
         * @return the data
         */
        public DATA data();

        /**
         * Set the data
         * @param data the data
         */
        public void data(DATA data);
    }

    /**
     * Default graph
     * @param <DATA> data type
     */
    public class MyGraph<DATA> implements Graph<DATA> {
        @Serial
        private static final long serialVersionUID = 7536136593612372614L;
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

        /**
         * Build the default {@link Graph}
         * @param comparator {@link Comparator}
         * @param linkedFactory {@link CycleLinked} factory
         */
        public MyGraph(Comparator<DATA> comparator , Supplier<Linked<AdjList<DATA> , CycleLinkedElmt<AdjList<DATA>>>> linkedFactory) {
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
            if (element == null) throw new GraphOperationException("Can't find out the data2 (" + data2 + ") in the graph.");

            element = this.search(data1 , (elmt) -> {
            });
            if (element == null) throw new GraphOperationException("Can't find out the data1 (" + data1 + ") in the graph.");

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
            if (element.data().adjacent().contains(data)) throw new GraphOperationException("Do not allow removal of the vertex if it is an adjecency list.");
            //Return if the vertex was not found.
            if (element == null) return result;
            if (element.data().adjacent().size() > 0) throw new GraphOperationException("Do not allow removal of the vertex if its adjacency list is not emtpy.");
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

            for (CycleLinkedElmt<AdjList<DATA>> element = this.adjlists.head() ; element != null ; element = element.next()) {
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

    /**
     * Vertex Color
     */
    public enum VertexColor {
        /**Black*/
        BLACK ,
        /**Gray*/
        GRAY ,
        /**White*/
        WHITE;
    }

    /**
     * Add untoward edge
     * @param graph {@link Graph}
     * @param data1 data one
     * @param data2 data two
     * @param <DATA> data type
     */
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
    public static <DATA> Collection<BfsVertex<DATA>> bfs(Graph<BfsVertex<DATA>> graph , BfsVertex<DATA> start , Collection<BfsVertex<DATA>> result) {
        Queue<AdjList<BfsVertex<DATA>>> queue = new SimpleQueue<>();
        //Initialize the queue with the adjacency list of the start vertex.
        AdjList<BfsVertex<DATA>> clr_adjlist = graph.adjlist(start);
        queue.offer(clr_adjlist);

        //Initialize all of the vertices in the graph.
        for (CycleLinkedElmt<AdjList<BfsVertex<DATA>>> element = graph.adjlists().head() ; element != null ; element = element.next()) {
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
        for (CycleLinkedElmt<AdjList<BfsVertex<DATA>>> element = graph.adjlists().head() ; element != null ; element = element.next()) {
            //Skip vertices that were not visited (those with hop counts of -1).
            BfsVertex<DATA> vertex = element.data().vertex();

            if (vertex.hops() != -1) result.add(vertex);
        }

        return result;
    }

    /**
     * Get default {@link Graph}
     * @param comparator {@link Comparator}
     * @return {@link Comparator}
     * @param <DATA> data type
     */
    public static <DATA> Graph<DATA> defaultGraph(Comparator<DATA> comparator) {
        return Graph.defaultGraph(comparator , () -> SingleLinked.<AdjList<DATA>> defaultSingleLinked(CycleLinkedElmt::defaultElmt));
    }

    /**
     * Get default {@link Graph}
     * @param comparator {@link Comparator}
     * @param singleLinkedFactory {@link Linked} factory
     * @return {@link Graph}
     * @param <DATA> data type
     */
    public static <DATA> Graph<DATA> defaultGraph(Comparator<DATA> comparator , Supplier<Linked<AdjList<DATA> , CycleLinkedElmt<AdjList<DATA>>>> singleLinkedFactory) {
        return new MyGraph<DATA>(comparator , singleLinkedFactory);
    }

    /**
     * deep first search
     * @param graph {@link Graph}
     * @param result the search sequence {@link Collection}
     * @return {@link Collection}
     * @param <DATA> data type
     */
    public static <DATA> Collection<DfsVertex<DATA>> dfs(Graph<DfsVertex<DATA>> graph , Collection<DfsVertex<DATA>> result) {
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

    /**
     * The deep first search
     * @param graph {@link Graph}
     * @param adjlist {@link AdjList}
     * @param result the search sequence {@link Collection}
     * @return {@link Collection}
     * @param <DATA> data type
     */
    public static <DATA> Collection<DfsVertex<DATA>> dfsMain(Graph<DfsVertex<DATA>> graph , AdjList<DfsVertex<DATA>> adjlist , Collection<DfsVertex<DATA>> result) {
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

    /**
     * Get the adjLists
     * @return {@link Linked}
     */
    public Linked<AdjList<D> , CycleLinkedElmt<AdjList<D>>> adjlists();

    /**
     * The number of the edge
     * @return edge number
     */
    public int ecount();

    /**
     * Get Adj list factory
     * @return {@link AdjList} factory
     */
    public Supplier<AdjList<D>> getAdjListFactory();

    /**
     * Insert edge between two data
     * @param data1 data one
     * @param data2 data two
     */
    public void insEdge(final D data1 , final D data2);

    /**
     * Insert vertex in {@link Graph}
     * @param data the data
     */
    public void insVertex(final D data);

    /**
     * is Adjacent between two data
     * @param data1 data one
     * @param data2 data two
     * @return if it is, then true
     */
    public boolean isAdjacent(final D data1 , final D data2);

    /**
     * has edge between two data
     * @param data1 data one
     * @param data2 dat two
     * @return if it matches, then true
     */
    public boolean match(D data1 , D data2);

    /**
     * Remove the edge between two data
     * @param data1 data one
     * @param data2 data two
     */
    public void remEdge(final D data1 , final D data2);

    /**
     * Remove vertex by data
     * @param data the data
     * @return the origin data
     */
    public D remVertex(D data);

    /**
     * set {@link AdjList} factory
     * @param adjListFactory {@link AdjList} factory
     */
    public void setAdjListFactory(Supplier<AdjList<D>> adjListFactory);

    /**
     * The number of vertex
     * @return vertex number
     */
    public int vcount();
}
