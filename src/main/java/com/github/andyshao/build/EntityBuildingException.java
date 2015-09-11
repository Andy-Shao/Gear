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
public class EntityBuildingException extends RuntimeException {
    private static final long serialVersionUID = 4467089871782604492L;

    public EntityBuildingException() {
    }

    public EntityBuildingException(String message) {
        super(message);
    }

    public EntityBuildingException(String message , Throwable exception) {
        super(message , exception);
    }

    public EntityBuildingException(Throwable exception) {
        super(exception);
    }
}
