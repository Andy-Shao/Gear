package com.github.andyshao.reflect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class FieldOperationTest {

    @Test
    public void testgetFieldTypeInfo() {
        Field field = FieldOperation.getDeclaredField(ForField.class , "listP");
        GenericNode fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertTrue(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == List.class);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(1));
        GenericNode node = fieldTypeInfo.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "strP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertFalse(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == String.class);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "intP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertFalse(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == int.class);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "p1");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertFalse(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == null);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "p2");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertTrue(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == List.class);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(1));
        node = fieldTypeInfo.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "p3");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertTrue(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == Map.class);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(2));
        node = fieldTypeInfo.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        node = fieldTypeInfo.getComponentTypes().get(1);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "p4");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertTrue(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == Map.class);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(2));
        node = fieldTypeInfo.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        Assert.assertThat(node.getTypeVariable() , Matchers.is("T"));
        node = fieldTypeInfo.getComponentTypes().get(1);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        Assert.assertThat(node.getTypeVariable() , Matchers.is("T"));
        
        field = FieldOperation.getDeclaredField(ForField.class , "intArrayP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertFalse(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == int[].class);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "strArrayP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertFalse(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == String[].class);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "mapArrayP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        Assert.assertTrue(fieldTypeInfo.isGeneiric());
        Assert.assertTrue(fieldTypeInfo.getDeclareType() == Map[].class);
        Assert.assertThat(fieldTypeInfo.getComponentTypes().size() , Matchers.is(2));
        node = fieldTypeInfo.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        node = fieldTypeInfo.getComponentTypes().get(1);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == Object.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
    }
    
    public class ForField<T extends GenericNode> {
        List<String> listP;
        String strP;
        int intP;
        T p1;
        List<T> p2;
        Map<String, T> p3;
        Map<T, T> p4;
        int[] intArrayP;
        String[] strArrayP;
        Map<String, Object>[] mapArrayP;
    }
}
