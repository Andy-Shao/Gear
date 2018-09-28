package com.github.andyshao.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class FieldOperationTest {

    @Test
    public void testgetFieldTypeInfo() {
        Field field = FieldOperation.getDeclaredField(ForField.class , "listP");
        GenericNode fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertTrue(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == List.class);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (1));
        GenericNode node = fieldTypeInfo.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "strP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertFalse(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == String.class);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "intP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertFalse(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == int.class);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "p1");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertFalse(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == null);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "p2");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertTrue(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == List.class);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (1));
        node = fieldTypeInfo.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getComponentTypes().size() , (0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "p3");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertTrue(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == Map.class);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (2));
        node = fieldTypeInfo.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        node = fieldTypeInfo.getComponentTypes().get(1);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getComponentTypes().size() , (0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "p4");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertTrue(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == Map.class);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (2));
        node = fieldTypeInfo.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getComponentTypes().size() , (0));
        assertEquals(node.getTypeVariable() , ("T"));
        node = fieldTypeInfo.getComponentTypes().get(1);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getComponentTypes().size() , (0));
        assertEquals(node.getTypeVariable() , ("T"));
        
        field = FieldOperation.getDeclaredField(ForField.class , "intArrayP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertFalse(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == int[].class);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "strArrayP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertFalse(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == String[].class);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (0));
        
        field = FieldOperation.getDeclaredField(ForField.class , "mapArrayP");
        fieldTypeInfo = FieldOperation.getFieldTypeInfo(field);
        assertTrue(fieldTypeInfo.isGeneiric());
        assertTrue(fieldTypeInfo.getDeclareType() == Map[].class);
        assertEquals(fieldTypeInfo.getComponentTypes().size() , (2));
        node = fieldTypeInfo.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        node = fieldTypeInfo.getComponentTypes().get(1);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == Object.class);
        assertEquals(node.getComponentTypes().size() , (0));
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
