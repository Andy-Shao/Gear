package com.github.andyshao.util.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CollectorImpleTest {
    
    @Data
    @AllArgsConstructor
    class Person {
        private String name;
        private Integer age;
    }

    @Test
    public void testToMap() {
        List<Person> ls = Arrays.asList(new Person("andy" , 12), new Person("shao" , 45));
        CollectorImpl<Person , List<Person> , Person> collectorImpl = new CollectorImpl<Person, List<Person>, Person>(
                ArrayList::new, 
                List::add, 
                (left, right)-> {
                    left.addAll(right);
                    return left;
                },
                a -> a.get(0),
                CollectorImpl.CH_CONCURRENT_NOID);
        ConcurrentHashMap<String , Person> map = ls.stream().collect(Collectors.groupingBy(Person::getName , ConcurrentHashMap::new, collectorImpl));
        assertNotNull(map);
        assertEquals(map.get("andy") , (new Person("andy", 12)));
        assertEquals(map.get("shao") , (new Person("shao" , 45)));
    }
}
