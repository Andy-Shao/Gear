package com.github.andyshao.util;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.reflect.ClassOperation;
import com.github.andyshao.reflect.FieldOperation;
import com.github.andyshao.util.annotation.CopyConvertor;
import com.github.andyshao.util.annotation.IgnoreCopy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * 
 * Title:object properties copy<br>
 * Description:<br>
 * Copyright: Copryright(c) 28 Feb 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public final class EntityOperation {
    /**
     * 
     * Title: Field matching procession interface<br>
     * Description:<br>
     * Copyright: Copryright(c) 28 Feb 2017<br>
     * Encoding:UNIX UTF-8
     * @author Andy.Shao
     *
     * @param <IN> input args
     * @param <OUT> outpu args
     */
	public static interface FieldMapper<IN, OUT> {
		/**
		 * match
		 * @param in input
		 * @param out output
		 * @return {@link FieldMatch}
		 */
		List<FieldMatch> match(IN in, OUT out);
	}

	/**
	 * Field mapper operations
	 * @param <IN> input type
	 * @param <OUT> output type
	 */
	public static interface FieldMapperOps<IN, OUT> extends FieldMapper<IN, OUT> {
		/**
		 * replace or add
		 * @param in input {@link Field}
		 * @param out output {@link Field}
		 * @param newValue {@link FieldMatch}
		 * @return {@link FieldMapperOps}
		 */
		FieldMapperOps<IN, OUT> replaceOrAdd(Field in, Field out, FieldMatch newValue);

		/**
		 * remove
		 * @param in input {@link Field}
		 * @param out output {@link Field}
		 * @return {@link FieldMapperOps}
		 */
		FieldMapperOps<IN, OUT> remove(Field in, Field out);
	}
	
	/**
	 * 
	 * Title: Field matching definition<br>
	 * Description:<br>
	 * Copyright: Copryright(c) 28 Feb 2017<br>
	 * Encoding:UNIX UTF-8
	 * @author Andy.Shao
	 *
	 */
	@Getter
	@Setter
	public static class FieldMatch{
		private Field resField;
		private Field destField;
        private PropertyConvert convert;
        private BiPredicate<Object, Object> isConvertable;

		/**
		 * build {@link FieldMatch}
		 * @param resField resource {@link Field}
		 * @param destField destination {@link Field}
		 */
		public FieldMatch(Field resField, Field destField){
			this.resField = resField;
			this.destField = destField;
		}
	}
	
	/**
	 * 
	 * Title: The property value convert interface<br>
	 * Descript:<br>
	 * Copyright: Copryright(c) 28 Feb 2017<br>
	 * Encoding:UNIX UTF-8
	 * @author Andy.Shao
	 *
	 */
	@FunctionalInterface
	public static interface PropertyConvert extends Convert<PropertyNode, Object>{
		/**
		 * convert operation
		 * @param in input
		 * @param inClazz input type
		 * @param outClazz output type
		 * @return output
		 */
		default Object convert(Object in, Class<?> inClazz, Class<?> outClazz) {
			return this.convert(PropertyNode.builder()
					.in(in)
					.inClazz(inClazz)
					.outClazz(outClazz)
					.build());
		}
	}

	/**
	 * property node
	 */
	@Getter
	@Setter
	@Builder
	public static class PropertyNode {
		private Object in;
		private Class<?> inClazz;
		private Class<?> outClazz;
	}
	
	/**
	 * copy properties
	 * @param resource the object which should be copied
	 * @param destination the object which should copy
	 * @param <RES> resource type
	 * @param <DEST> destination type
	 * @return destination object
	 */
	public static final <RES, DEST> DEST copyProperties(RES resource, DEST destination){
		return copyProperties(resource, destination, defaultFieldMapper());
	}

	/**
	 * copy properties and is permitted to ignore resource filed by its name
	 * @param resource the object which should be copied
	 * @param destination the object which should copy
	 * @param ignoreResFieldNames ght ignore list which stores the resources filed names
	 * @param <RES> resource type
	 * @param <DEST> destination type
	 * @return destination object
	 */
	public static final <RES, DEST> DEST copyProperties(RES resource, DEST destination,
														final List<String> ignoreResFieldNames) {
		final FieldMapper<RES, DEST> fieldMapper = defaultFieldMapper();
		return copyProperties(resource, destination, new FieldMapper<RES, DEST>() {
			@Override
			public List<FieldMatch> match(RES res, DEST dest) {
				return fieldMapper.match(res, dest)
						.stream()
						.filter(fieldMatch -> !ignoreResFieldNames.contains(fieldMatch.resField.getName()))
						.collect(Collectors.toList());
			}
		});
	}

	/**
	 * ops
	 * @param origin origin mapper
	 * @return new mapper
	 * @param <RES> resource type
	 * @param <DEST> destination type
	 */
	public static final <RES, DEST> FieldMapperOps<RES, DEST> ops(FieldMapper<RES, DEST> origin) {
		return new FieldMapperOps<RES, DEST>() {
			private Map<Key, FieldMatch> replace = new HashMap<>();
			private Set<Key> remove = new HashSet<>();
			
			@Override
			public List<FieldMatch> match(RES in, DEST out) {
				if(replace.isEmpty() && remove.isEmpty()) {
					return origin.match(in, out);
				}
				
				List<FieldMatch> ret = origin.match(in, out)
					.stream()
					.filter(it -> !remove.contains(new Key(it.resField, it.destField)))
					.filter(it -> !replace.containsKey(new Key(it.resField, it.destField)))
					.collect(Collectors.toList());
				ret.addAll(replace.values());
				return ret;
			}
			
			@Getter
			@Setter
			@AllArgsConstructor
			class Key {
				private Field res;
				private Field dest;
				
				@Override
				public int hashCode() {
					return Objects.hash(this.res, this.dest);
				}
				
				@Override
				public boolean equals(Object obj) {
					if(obj instanceof Key) {
						Key that = (Key) obj;
						return Objects.equals(this.res, that.res) && Objects.equals(this.dest, that.dest);
					} else return false;
				}
			}

			@Override
			public FieldMapperOps<RES, DEST> replaceOrAdd(Field in, Field out, FieldMatch newValue) {
				replace.put(new Key(in, out), newValue);
				return this;
			}

			@Override
			public FieldMapperOps<RES, DEST> remove(Field in, Field out) {
				remove.add(new Key(in, out));
				return this;
			}
		};
	}

	/**
	 * default {@link FieldMapper}
	 * @return {@link FieldMapper}
	 * @param <RES> resource
	 * @param <DEST> destination
	 */
	public static final <RES, DEST> FieldMapper<RES, DEST> defaultFieldMapper() {
		return defaultFieldMapper((in, out) -> false, (in, out) -> null);
	}

	/**
	 * default {@link FieldMapper}
	 * @param predicate {@link BiPredicate}
	 * @param function {@link BiFunction}
	 * @return {@link FieldMapper}
	 * @param <RES> resource
	 * @param <DEST> destination
	 */
	public static final <RES, DEST> FieldMapper<RES, DEST> defaultFieldMapper(BiPredicate<Field, Field> predicate, BiFunction<Field, Field, FieldMatch> function) {
		return (input, output) -> {
			if(input == null || output == null) return Collections.emptyList();
			if(input.getClass().isPrimitive() || output.getClass().isPrimitive()) return Collections.emptyList();
			List<FieldMatch> result = new ArrayList<>();
			Field[] inputFields = FieldOperation.superGetDeclaredFields(input.getClass());
			Field[] outputFields = FieldOperation.superGetDeclaredFields(output.getClass());
			for(Field inputField : inputFields){
				for(Field outputField : outputFields){
					if(predicate.test(inputField, outputField)) {
						result.add(function.apply(inputField, outputField));
					} else if(Objects.equals(inputField.getName(), outputField.getName())){
					    IgnoreCopy ignoreCopy = inputField.getAnnotation(IgnoreCopy.class);
					    if(isMatch(inputField , outputField) && ignoreCopy == null) {
					    	FieldMatch item = new FieldMatch(inputField, outputField);
					    	CopyConvertor copyConvertor = inputField.getAnnotation(CopyConvertor.class);
					    	if(Objects.nonNull(copyConvertor)) item.setConvert(ClassOperation.newInstance(copyConvertor.convertor()));
							result.add(item);
					    }
					}
				}
			}
			return result;
		};
	}

	/**
	 * is match
	 * @param inputField {@link Field}
	 * @param outputField {@link Field}
	 * @return if it is then true
	 */
	public static final boolean isMatch(Field inputField, Field outputField) {
	    final Class<?> outputType = outputField.getType();
        final Class<?> inputType = inputField.getType();
        if(Modifier.isFinal(outputField.getModifiers())) return false;
        boolean result = outputType.isAssignableFrom(inputType);
	    if(result) return true;
	    
	    if(inputType == short.class || inputType == Short.class) {
	        result = outputType == short.class || outputType == Short.class || outputType == int.class || outputType == Integer.class || outputType == long.class || outputType == Long.class;
	    } else if(inputType == int.class || inputType == Integer.class) {
	        result = outputType == int.class || outputType == Integer.class || outputType == long.class || outputType == Long.class;
	    } else if (inputType == long.class || inputType == Long.class) {
	        result = outputType == long.class || outputType == Long.class;
	    }
	    
	    if(inputType == float.class || inputType == Float.class) {
	        result = outputType == float.class || outputType == Float.class || outputType == double.class || outputType == Double.class;
	    } else if(inputType == double.class || inputType == Double.class) {
	        result = outputType == double.class || outputType == double.class;
	    }
	    return result;
	}
	
	/**
	 * copy properties 
	 * @param resource the object which should be copied
	 * @param destination the object which should copy
	 * @param fieldMapper the field matching definition interface
	 * @param <RES> resource type
	 * @param <DEST> destination type
	 * @return destination object
	 */
	public static final <RES, DEST> DEST copyProperties(RES resource, DEST destination, FieldMapper<RES, DEST> fieldMapper){
	    CopyConvertor classCopyConvertor = resource.getClass().getAnnotation(CopyConvertor.class);
	    PropertyConvert convert = null;
	    if(classCopyConvertor == null) {
	        convert = it -> {
	            CopyConvertor copyConvertor = it.getInClazz().getAnnotation(CopyConvertor.class);
	            if(copyConvertor == null) return it.getIn();
	            else {
	                PropertyConvert convertor = ClassOperation.newInstance(copyConvertor.convertor());
	                return convertor.convert(it);
	            }
	        };
	    } else convert = ClassOperation.newInstance(classCopyConvertor.convertor());
	    
        return copyProperties(resource, destination, fieldMapper, convert);
	}
	
	/**
	 * copy properties
	 * @param resource the object which should be copied
	 * @param destination the object which should copy
	 * @param fieldMapper the field matching definition interface
	 * @param covert the properties convert
	 * @param <RES> resource type
	 * @param <DEST> destination type
	 * @return destination object
	 */
	public static final <RES,DEST> DEST copyProperties(RES resource, DEST destination, FieldMapper<RES, DEST> fieldMapper, PropertyConvert covert){
		List<FieldMatch> fieldMatchs = fieldMapper.match(resource, destination);
		for(FieldMatch fieldMatch : fieldMatchs){
			fieldMatch.resField.setAccessible(true);
			fieldMatch.destField.setAccessible(true);
			Object inValue = FieldOperation.getFieldValue(resource, fieldMatch.resField);
			if(fieldMatch.convert == null) {
			    FieldOperation.setFieldValue(destination, fieldMatch.destField, covert.convert(inValue, fieldMatch.resField.getType(), fieldMatch.destField.getType()));
			} else {
				if(Objects.isNull(fieldMatch.isConvertable)) 
					FieldOperation.setFieldValue(destination , fieldMatch.destField , fieldMatch.convert.convert(inValue, fieldMatch.resField.getType(), fieldMatch.destField.getType()));
				else {
					Object origin = FieldOperation.getFieldValue(destination, fieldMatch.destField);
					if(fieldMatch.isConvertable.test(inValue, origin)) 
						FieldOperation.setFieldValue(destination , fieldMatch.destField , fieldMatch.convert.convert(inValue, fieldMatch.resField.getType(), fieldMatch.destField.getType()));
				}
			}
		}
		
		return destination;
	}
	
	private EntityOperation(){
		throw new AssertionError("No " + EntityOperation.class + " instance for you!");
	}

}
