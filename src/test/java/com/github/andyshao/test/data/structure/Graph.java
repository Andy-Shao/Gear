package com.github.andyshao.test.data.structure;

import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.andyshao.data.structure.CycleLinkedElmt;
import com.github.andyshao.data.structure.GraphOperationException;
import com.github.andyshao.data.structure.SingleLinked;
import com.github.andyshao.lang.Cleanable;
import com.github.andyshao.test.data.structure.convert.SetConvert;

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
@Deprecated
public interface Graph<D> extends Cleanable {

    public interface AdjList<DATA> {
        public static <D> AdjList<D> DEFAULT_ADJ_LIST(Supplier<Set<D>> setFactory) {
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
        }

        VertexColor color();

        void color(VertexColor color);

        DATA data();

        void data(DATA data);

        int hops();

        void hops(int hops);
    }

    public class MyGraph<DATA> implements Graph<DATA> {
        private long actionAcount;
        protected Supplier<AdjList<DATA>> adjListFactory = () -> {
            return AdjList.DEFAULT_ADJ_LIST(() -> {
                return new SetConvert<DATA>().convert(new HashSet<DATA>());
            });
        };
        protected SingleLinked<AdjList<DATA>> adjlists;
        private final Comparator<DATA> comparator;
        protected int ecount;
        protected final Supplier<SingleLinked<AdjList<DATA>>> singleLinkedFactory;
        protected int vcount;

        public MyGraph(Comparator<DATA> comparator , Supplier<SingleLinked<AdjList<DATA>>> singleLinkedFactory) {
            this.singleLinkedFactory = singleLinkedFactory;
            this.comparator = comparator;
            this.adjlists = this.singleLinkedFactory.get();
            this.vcount = 0;
            this.ecount = 0;
            this.actionAcount = 0;
        }

        @Override
        public void clear() {
            this.vcount = 0;
            this.ecount = 0;
            this.adjlists.clear();
            this.actionAcount++;
        }

        @Override
        public Supplier<AdjList<DATA>> getAdjListFactory() {
            return this.adjListFactory;
        }

        @Override
        public Graph.AdjList<DATA> graph_adjlist(DATA data) {
            CycleLinkedElmt<AdjList<DATA>> element;

            //Locate the adjacency list for the vertex.
            element = this.search(data , (elmt) -> {
            });

            //Return if the vertex was not found.
            if (element == null) throw new GraphOperationException(data + " can't found out.");

            //Pass back the adjacency list for the vertex.
            return element.data();
        }

        @Override
        public SingleLinked<Graph.AdjList<DATA>> graph_adjlists() {
            return this.adjlists;
        }

        @Override
        public int graph_ecount() {
            return this.ecount;
        }

        @Override
        public void graph_ins_edge(DATA data1 , DATA data2) {
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
            element.data().adjacent().set_insert(data2);

            //Adjust the edge count to account for the inserted edge.
            this.ecount++;
        }

        @Override
        public void graph_ins_vertex(DATA data) {
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
        public boolean graph_is_adjacent(DATA data1 , DATA data2) {
            CycleLinkedElmt<AdjList<DATA>> element;

            element = this.search(data1 , (elmt) -> {
            });

            //Return if the first vertex was not found.
            if (element == null) throw new GraphOperationException(data1 + "can't find out.");

            //Return whether the second vertex is in the adjacency list of the first.
            return element.data().adjacent().set_is_member(data2);
        }

        @Override
        public void graph_rem_edge(DATA data1 , DATA data2) {
            CycleLinkedElmt<AdjList<DATA>> element;

            //Locate the adjacency list for the first vertex.
            element = this.search(data1 , (elmt) -> {
            });

            if (element == null) throw new GraphOperationException("Can't not find out data1 " + data1);

            //Remove the second vertex from the adjacency list of the first vertex.
            element.data().adjacent().set_remove(data2);

            //Adjust the edge count to account for the removed edge.
            this.ecount--;
        }

        @SuppressWarnings({
            "unchecked" , "unused"
        })
        @Override
        public DATA graph_rem_vertex(final DATA data) {
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
            if (element.data().adjacent().set_is_member(data)) throw new GraphOperationException(
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

        @Override
        public int graph_vcount() {
            return this.vcount;
        }

        @Override
        public boolean match(DATA data1 , DATA data2) {
            if (this.comparator.compare(data1 , data2) == 0) return true;
            else return false;
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
    }

    public enum VertexColor {
        BLACK , GRAY , WHITE
    }

    public static <DATA> void ADD_UNTOWARD_EDGE(Graph<DATA> graph , DATA data1 , DATA data2) {
        graph.graph_ins_edge(data1 , data2);
        graph.graph_ins_edge(data2 , data1);
    }

    public static <DATA> Graph<DATA> DEFAULT_GRAPH(
        Comparator<DATA> comparator , Supplier<SingleLinked<AdjList<DATA>>> singleLinkedFactory) {
        return new MyGraph<DATA>(comparator , singleLinkedFactory);
    }

    public Supplier<AdjList<D>> getAdjListFactory();

    public AdjList<D> graph_adjlist(final D data);

    public SingleLinked<AdjList<D>> graph_adjlists();

    public int graph_ecount();

    public void graph_ins_edge(final D data1 , final D data2);

    public void graph_ins_vertex(final D data);

    public boolean graph_is_adjacent(final D data1 , final D data2);

    public void graph_rem_edge(final D data1 , final D data2);

    public D graph_rem_vertex(D data);

    public int graph_vcount();

    public boolean match(D data1 , D data2);

    public void setAdjListFactory(Supplier<AdjList<D>> adjListFactory);
}
