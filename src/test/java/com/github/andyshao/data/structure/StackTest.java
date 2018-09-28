package com.github.andyshao.data.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class StackTest implements ArgumentsProvider{
    private final String[] data = { "Andy" , "Shao" };
    
    @ParameterizedTest
    @ArgumentsSource(StackTest.class)
    public void testEmptyStack(Stack<String> stack) {
        Stack<String> emptyStack = Stack.defaultStack();
        for(String item : emptyStack) {
            item.toString();
            fail();
        }
    }

    @ParameterizedTest
    @ArgumentsSource(StackTest.class)
    public void testIterator(Stack<String> stack) {
        for (int i = 0 ; i < this.data.length ; i++)
            stack.push(this.data[i]);

        Iterator<String> iterator = stack.iterator();
        assertEquals(iterator.next() , this.data[this.data.length - 1]);

        stack.clear();
        assertEquals(stack.size() , 0);
    }

    @ParameterizedTest
    @ArgumentsSource(StackTest.class)
    public void testLogic(Stack<String> stack) {
        assertEquals(stack.size() , 0);

        for (int i = 0 ; i < this.data.length ; i++)
            stack.push(this.data[i]);

        assertEquals(stack.size() , this.data.length);
        assertEquals(stack.peek() , this.data[this.data.length - 1]);

        do
            stack.pop();
        while (stack.size() != 0);

        assertEquals(stack.size() , 0);
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
            Stack.<String , CycleLinkedElmt<String>> defaultStack(SingleLinked.defaultSingleLinked(CycleLinkedElmt::defaultElmt)),
            Stack.<String , CycleLinkedElmt<String>> defaultStack(CycleLinked.defaultCycleLinked(CycleLinkedElmt::defaultElmt)))
            .map(Arguments::of);
    }
}
