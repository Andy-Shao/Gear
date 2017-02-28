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
	public static interface FieldMapper<IN, OUT> {
		List<FieldMatch> match(IN in, OUT out);
	}
	
	public static class FieldMatch{
		private Field inField;
		private Field outField;
		
		public FieldMatch(Field inField, Field outField){
			this.inField = inField;
			this.outField = outField;
		}
		
		public void setInField(Field inField) {
			this.inField = inField;
		}
		
		public void setOutField(Field outField) {
			this.outField = outField;
		}
	}
	
	public static interface PropertyConvert{
		Object covert(Object in, Class<?> inClazz, Class<?> outClazz);
	}
	
	public static final <IN, OUT> void copyProperties(IN in, OUT out){
		copyProperties(in, out, (input, output) -> {
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
	
	public static final <IN, OUT> void copyProperties(IN in, OUT out, FieldMapper<IN, OUT> fieldMapper){
		copyProperties(in, out, fieldMapper, (input, inClazz, outClazz)->input);
	}
	
	public static final <IN,OUT> void copyProperties(IN in, OUT out, FieldMapper<IN, OUT> fieldMapper, PropertyConvert covert){
		List<FieldMatch> fieldMatchs = fieldMapper.match(in, out);
		for(FieldMatch fieldMatch : fieldMatchs){
			fieldMatch.inField.setAccessible(true);
			fieldMatch.outField.setAccessible(true);
			Object inValue = FieldOperation.getFieldValue(in, fieldMatch.inField);
			FieldOperation.setFieldValue(out, fieldMatch.outField, covert.covert(inValue, fieldMatch.inField.getType(), fieldMatch.outField.getType()));
		}
	}
	
	private EntityOperation(){
		throw new AssertionError("No " + EntityOperation.class + " instance for you!");
	}

}
