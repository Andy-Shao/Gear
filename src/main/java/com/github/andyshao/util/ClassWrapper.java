package com.github.andyshao.util;

import java.lang.reflect.Field;
import java.util.List;

import com.github.andyshao.reflect.FieldOperation;

public final class ClassWrapper {
	private ClassWrapper(){
		throw new AssertionError("No " + ClassWrapper.class + " instance for you!");
	}
	
	public static interface WrapperCovert{
		Object covert(Object in, Class<?> inClazz, Class<?> outClazz);
	}
	
	public static interface FieldMapper<IN, OUT> {
		List<FieldMatch> match(IN in, OUT out);
	}
	
	public static class FieldMatch{
		private Field outField;
		private Field inField;
		
		public FieldMatch(Field inField, Field outField){
			this.inField = inField;
			this.outField = outField;
		}
		
		public void setOutField(Field outField) {
			this.outField = outField;
		}
		
		public void setInField(Field inField) {
			this.inField = inField;
		}
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
	
	public static final <IN, OUT> void wrap(IN in, OUT out, FieldMapper<IN, OUT> fieldMapper){
		wrap(in, out, fieldMapper, (input, inClazz, outClazz)->input);
	}

}
