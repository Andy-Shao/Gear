package com.github.andyshao.util.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

class CollectorOperationTest {

	@Data
	@AllArgsConstructor
	private static class Person {
		private String name;
		private Integer age;
	}
	
	@Test
	void toList() {
		List<Person> ps = Arrays.<Person>asList(new Person("andy.shao", 29), new Person("andy.shao", 30));
		Map<String, List<Integer>> map = ps.stream()
			.collect(Collectors.groupingBy(Person::getName, CollectorOperation.toList(Person::getAge)));
		Assertions.assertThat(map).isNotNull();
		Assertions.assertThat(map).isNotEmpty();
		Assertions.assertThat(map.get("andy.shao")).isEqualTo(Arrays.asList(29, 30));
	}
	
	@Test
	void toListConcurrent() {
		List<Person> ps = Arrays.<Person>asList(new Person("andy.shao", 29), new Person("andy.shao", 30));
		ConcurrentMap<String, List<Integer>> map = ps.parallelStream()
			.collect(Collectors.groupingByConcurrent(Person::getName, CollectorOperation.toListConcurrent(Person::getAge)));
		Assertions.assertThat(map).isNotNull();
		Assertions.assertThat(map).isNotEmpty();
		Assertions.assertThat(map.get("andy.shao")).isEqualTo(Arrays.asList(29, 30));
	}

}
