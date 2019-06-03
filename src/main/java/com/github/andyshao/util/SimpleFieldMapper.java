package com.github.andyshao.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

import com.github.andyshao.reflect.FieldOperation;
import com.github.andyshao.util.EntityOperation.FieldMapper;
import com.github.andyshao.util.EntityOperation.FieldMatch;
import com.github.andyshao.util.annotation.IgnoreCopy;

import lombok.Setter;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Aug 21, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 * @deprecated useless class
 *
 * @param <IN> input type
 * @param <OUT> output type
 */
@Deprecated
public class SimpleFieldMapper<IN , OUT> implements FieldMapper<IN , OUT> {
    @Setter
    private BiPredicate<Field , Field> customPredicate = (input, output) -> Objects.equals(input.getName() , output.getName());

    @Override
    public List<FieldMatch> match(IN input , OUT output) {
        if(input == null || output == null) return Collections.emptyList();
        if(input.getClass().isPrimitive() || output.getClass().isPrimitive()) return Collections.emptyList();
        List<FieldMatch> result = new ArrayList<>();
        Field[] inputFields = FieldOperation.superGetDeclaredFields(input.getClass());
        Field[] outputFields = FieldOperation.superGetDeclaredFields(output.getClass());
        for(Field inputField : inputFields){
            for(Field outputField : outputFields){
                if(customPredicate.test(inputField , outputField)){
                    IgnoreCopy ignoreCopy = inputField.getAnnotation(IgnoreCopy.class);
                    if(EntityOperation.isMatch(inputField , outputField) && ignoreCopy == null) 
                        result.add(new FieldMatch(inputField, outputField));
                }
            }
        }
        return result;
    }

}
