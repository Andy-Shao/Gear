package com.github.andyshao.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.reflect.ClassOperation;
import com.github.andyshao.reflect.FieldOperation;
import com.github.andyshao.util.annotation.CopyConvertor;
import com.github.andyshao.util.annotation.IgnoreCopy;

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
     * @param <IN> input args
     * @param <OUT> outpu args
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
        private Convert<Object, Object> convert;
		
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
		
		@SuppressWarnings("unchecked")
        public void setConvert(Convert<?, ?> convert) {
		    this.convert = (Convert<Object , Object>) convert;
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
	 * @param <DEST> destination type
	 * @return destination object
	 */
	public static final <RES, DEST> DEST copyProperties(RES resource, DEST destination){
		return copyProperties(resource, destination, (input, output) -> {
			if(input == null || output == null) return Collections.emptyList();
			if(input.getClass().isPrimitive() || output.getClass().isPrimitive()) return Collections.emptyList();
			List<FieldMatch> result = new ArrayList<>();
			Field[] inputFields = FieldOperation.superGetDeclaredFields(input.getClass());
			Field[] outputFields = FieldOperation.superGetDeclaredFields(output.getClass());
			for(Field inputField : inputFields){
				for(Field outputField : outputFields){
					if(Objects.equals(inputField.getName(), outputField.getName())){
					    IgnoreCopy ignoreCopy = inputField.getAnnotation(IgnoreCopy.class);
					    if(isMatch(inputField , outputField) && ignoreCopy == null) result.add(new FieldMatch(inputField, outputField));
					}
				}
			}
			return result;
		});
	}
	
	static final boolean isMatch(Field inputField, Field outputField) {
	    final Class<?> outputType = outputField.getType();
        final Class<?> inputType = inputField.getType();
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
	        convert = (input, inClazz, outClazz)->{
	            CopyConvertor copyConvertor = inClazz.getAnnotation(CopyConvertor.class);
	            if(copyConvertor == null) return input;
	            else {
	                PropertyConvert convertor = ClassOperation.newInstance(copyConvertor.convertor());
	                return convertor.covert(input , inClazz , outClazz);
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
			    FieldOperation.setFieldValue(destination, fieldMatch.destField, covert.covert(inValue, fieldMatch.resField.getType(), fieldMatch.destField.getType()));
			} else {
			    FieldOperation.setFieldValue(destination , fieldMatch.destField , fieldMatch.convert.convert(inValue));
			}
		}
		
		return destination;
	}
	
	private EntityOperation(){
		throw new AssertionError("No " + EntityOperation.class + " instance for you!");
	}

}
