package com.github.andyshao.build;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractEntityFactory implements EntityFactory {
    protected final Map<String , Object> entitiesJar = new HashMap<String , Object>();
    protected volatile boolean hasBuild = false;

    protected void buildEntities() {
        //TODO
        //do something...
        this.hasBuild();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E get(String name) throws EntityBuildingException {
        if (this.isBuild()) this.buildEntities();
        return (E) this.entitiesJar.get(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E[] getByType(Class<E> clazz) throws EntityBuildingException {
        if (this.isBuild()) this.buildEntities();
        List<E> result = new ArrayList<E>();
        for (Entry<String , Object> entry : this.entitiesJar.entrySet())
            if (clazz.isInstance(entry.getValue())) result.add((E) entry.getValue());
        return result.<E> toArray((E[]) Array.newInstance(clazz , result.size()));
    }

    protected void hasBuild() {
        this.hasBuild = true;
    }

    protected boolean isBuild() {
        return this.hasBuild;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E put(String name , E entity) throws EntityBuildingException {
        if (this.isBuild()) this.buildEntities();
        return (E) this.entitiesJar.put(name , entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E putByType(Class<E> clazz , E entity) throws EntityBuildingException {
        if (this.isBuild()) this.buildEntities();
        return (E) this.entitiesJar.put(clazz.getName() , entity);
    }
}
