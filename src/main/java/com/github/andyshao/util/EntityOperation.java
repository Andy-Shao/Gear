package com.github.andyshao.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.github.andyshao.reflect.FieldOperation;

/**
 * 
 * Title:object properties copy<br>
 * Descript:<br>
 * Copyright: Copryright(c) 28 Feb 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public final class EntityOperation {
    /**
     * 
     * Title: Field matching procession interface<br>
     * Descript:<br>
     * Copyright: Copryright(c) 28 Feb 2017<br>
     * Encoding:UNIX UTF-8
     * @author Andy.Shao
     *
     * @param <IN>
     * @param <OUT>
     */
	public static interface FieldMapper<IN, OUT> {
		List<FieldMatch> match(IN in, OUT out);
	}
	
	/**
	 * 
	 * Title: Field matching definition<br>
	 * Descript:<br>
	 * Copyright: Copryright(c) 28 Feb 2017<br>
	 * Encoding:UNIX UTF-8
	 * @author Andy.Shao
	 *
	 */
	public static class FieldMatch{
		private Field resField;
		private Field destField;
		
		public FieldMatch(Field resField, Field destField){
			this.resField = resField;
			this.destField = destField;
		}
		
		public void setResField(Field resField) {
			this.resField = resField;
		}
		
		public void setDestField(Field destField) {
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
	public static interface PropertyConvert{
		Object covert(Object in, Class<?> inClazz, Class<?> outClazz);
	}
	
	/**
	 * copy properties
	 * @param resource the object which should be copied
	 * @param destination the object which should copy
	 * @param <RES> resource type
	 * @param <DEST> destionation type
	 */
	public static final <RES, DEST> void copyProperties(RES resource, DEST destination){
		copyProperties(resource, destination, (input, output) -> {
			if(input == null || output == null) return Collections.emptyList();
			if(input.getClass().isPrimitive() || output.getClass().isPrimitive()) return Collections.emptyList();
			List<FieldMatch> result = new ArrayList<>();
			Field[] inputFields = FieldOperation.superGetDeclaredFields(input.getClass());
			Field[] outputFields = FieldOperation.superGetDeclaredFields(output.getClass());
			for(Field inputField : inputFields){
				for(Field outputField : outputFields){
					if(Objects.equals(inputField.getName(), outputField.getName())){
						if(outputField.getClass().isAssignableFrom(inputField.getClass())) result.add(new FieldMatch(inputField, outputField));
					}
				}
			}
			return result;
		});
	}
	
	/**
	 * copy properties 
	 * @param resource the object which should be copied
	 * @param destination the object which should copy
	 * @param fieldMapper the field matching definition interface
	 * @param <RES> resource type
	 * @param <DEST> destination type
	 */
	public static final <RES, DEST> void copyProperties(RES resource, DEST destination, FieldMapper<RES, DEST> fieldMapper){
		copyProperties(resource, destination, fieldMapper, (input, inClazz, outClazz)->input);
	}
	
	/**
	 * copy properties
	 * @param resource the object which should be copied
	 * @param destination the object which should copy
	 * @param fieldMapper the field matching definition interface
	 * @param covert the properties convert
	 * @param <RES> resource type
	 * @param <DEST> destination type
	 */
	public static final <RES,DEST> void copyProperties(RES resource, DEST destination, FieldMapper<RES, DEST> fieldMapper, PropertyConvert covert){
		List<FieldMatch> fieldMatchs = fieldMapper.match(resource, destination);
		for(FieldMatch fieldMatch : fieldMatchs){
			fieldMatch.resField.setAccessible(true);
			fieldMatch.destField.setAccessible(true);
			Object inValue = FieldOperation.getFieldValue(resource, fieldMatch.resField);
			FieldOperation.setFieldValue(destination, fieldMatch.destField, covert.covert(inValue, fieldMatch.resField.getType(), fieldMatch.destField.getType()));
		}
	}
	
	private EntityOperation(){
		throw new AssertionError("No " + EntityOperation.class + " instance for you!");
	}

}
