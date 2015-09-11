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
public class AnnotationEntityFactory extends AbstractEntityFactory implements EntityFactory {
    private final Class<?>[] entities;

    public AnnotationEntityFactory(Class<?>... entities) {
        this.entities = entities;
    }
}
