package com.github.andyshao.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.github.andyshao.reflect.FieldOperation;

public final class ClassWrapper {
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
	
	public static interface WrapperCovert{
		Object covert(Object in, Class<?> inClazz, Class<?> outClazz);
	}
	
	public static final <IN, OUT> void wrap(IN in, OUT out){
		wrap(in, out, (input, output) -> {
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
	
	public static final <IN, OUT> void wrap(IN in, OUT out, FieldMapper<IN, OUT> fieldMapper){
		wrap(in, out, fieldMapper, (input, inClazz, outClazz)->input);
	}
	
	public static final <IN,OUT> void wrap(IN in, OUT out, FieldMapper<IN, OUT> fieldMapper, WrapperCovert covert){
		List<FieldMatch> fieldMatchs = fieldMapper.match(in, out);
		for(FieldMatch fieldMatch : fieldMatchs){
			fieldMatch.inField.setAccessible(true);
			fieldMatch.outField.setAccessible(true);
			Object inValue = FieldOperation.getFieldValue(in, fieldMatch.inField);
			FieldOperation.setFieldValue(out, fieldMatch.outField, covert.covert(inValue, fieldMatch.inField.getType(), fieldMatch.outField.getType()));
		}
	}
	
	private ClassWrapper(){
		throw new AssertionError("No " + ClassWrapper.class + " instance for you!");
	}

}
