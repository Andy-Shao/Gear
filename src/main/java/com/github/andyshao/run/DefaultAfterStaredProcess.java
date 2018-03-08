package com.github.andyshao.run;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.github.andyshao.data.structure.Graph;
import com.github.andyshao.data.structure.Stack;
import com.github.andyshao.data.structure.Graph.AdjList;
import com.github.andyshao.lang.StringOperation;
import com.github.andyshao.reflect.MethodOperation;
import com.github.andyshao.run.AfterStartedNode.NodeColor;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Dec 13, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 *
 */
public class DefaultAfterStaredProcess implements AfterStartedProcess{
    @Data
    @EqualsAndHashCode(of = { "order" })
    static class MethodNode {
        private Method method;
        private int order = 0;
    }
    
    @Override
    public void process(AfterStartedContext context) {
        Graph<AfterStartedNode> graph = Graph.defaultGraph(new Comparator<AfterStartedNode>() {
            @Override
            public int compare(AfterStartedNode o1 , AfterStartedNode o2) {
                return StringOperation.COMPARATOR.compare(o1.getNodeName() , o2.getNodeName());
            }
        });
        
        fillGraph(context , graph);
        Stack<AfterStartedNode> stack = Stack.defaultStack();
        sortBeans(graph , stack);
        processSequenceBeans(context, stack);
    }
    
    private static void processSequenceBeans(AfterStartedContext context , Stack<com.github.andyshao.run.AfterStartedNode> stack) {
        for (AfterStartedNode node : stack) {
            if(node.getColor() == NodeColor.BLACK) {
                node.setColor(NodeColor.RED);
                PriorityQueue<MethodNode> queue = new PriorityQueue<>(new Comparator<MethodNode>() {
                    @Override
                    public int compare(MethodNode o1 , MethodNode o2) {
                        return o1.getOrder() - o2.getOrder();
                    }
                });
                final Object bean = context.getBean(node.getNodeName());
                for (Method method : MethodOperation.getAllMethods(bean.getClass())) {
                    final AfterStartedMethod annotation = method.getAnnotation(AfterStartedMethod.class);
                    if (annotation != null && method.getParameterTypes().length == 0) {
                        final MethodNode methodNode = new MethodNode();
                        methodNode.setMethod(method);
                        methodNode.setOrder(annotation.order());
                        queue.add(methodNode);
                    }
                }
                
                while (!queue.isEmpty()) {
                    final MethodNode methodNode = queue.poll();
                    final Method method = methodNode.getMethod();
                    method.setAccessible(false);
                    MethodOperation.invoked(bean , method);
                }
            }
        }
    }

    private static void pushInStack(Graph<AfterStartedNode> graph , AfterStartedNode parent , Stack<AfterStartedNode> stack) {
        for (AfterStartedNode node : graph.adjlist(parent).adjacent()) {
            stack.push(node);
            pushInStack(graph , node , stack);
        }
    }
    
    private static void sortBeans(Graph<AfterStartedNode> graph , Stack<AfterStartedNode> stack) {
        for (AdjList<AfterStartedNode> adjList : graph.adjlists()) {
            final AfterStartedNode vertex = adjList.vertex();
            if (vertex.isTail()) {
                stack.push(vertex);
                pushInStack(graph , vertex , stack);
            }
        }
    }

    private static void fillGraph(AfterStartedContext context , Graph<AfterStartedNode> graph) {
        for (String beanName : context.getBeanNamesForAnnotation(AfterStartedOpsBean.class)) {
            final Object bean = context.getBean(beanName);
            final AfterStartedOpsBean annotation = bean.getClass().getAnnotation(AfterStartedOpsBean.class);
            if(annotation != null) {
                final AfterStartedNode node = findOrCreate(graph , new AfterStartedNode(beanName)).vertex();
                for (String runAfter : annotation.runAfter()) {
                    final AfterStartedNode runAfterNode = findOrCreate(graph , new AfterStartedNode(runAfter)).vertex();
                    graph.insEdge(node , runAfterNode);
                    graph.adjlist(runAfterNode).vertex().setTail(false);
                }
                for (String runBefore : annotation.runBefore()) {
                    final AfterStartedNode runBeforeNode = findOrCreate(graph , new AfterStartedNode(runBefore)).vertex();
                    graph.insEdge(runBeforeNode , node);
                    graph.adjlist(node).vertex().setTail(false);
                }
            }
        }
    }

    private static AdjList<AfterStartedNode> findOrCreate(Graph<AfterStartedNode> graph , final AfterStartedNode data) {
        AdjList<AfterStartedNode> node = graph.adjlist(data);
        if (node == null) {
            graph.insVertex(data);
            node = graph.adjlist(data);
        }
        return node;
    }
}
