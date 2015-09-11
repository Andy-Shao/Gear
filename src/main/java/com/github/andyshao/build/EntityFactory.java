package com.github.andyshao.build;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 11, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface EntityFactory {
    <E> E get(String name) throws EntityBuildingException;

    <E> E[] getByType(Class<E> clazz) throws EntityBuildingException;

    <E> E put(String name , E entity) throws EntityBuildingException;

    <E> E putByType(Class<E> clazz , E entity) throws EntityBuildingException;
}
