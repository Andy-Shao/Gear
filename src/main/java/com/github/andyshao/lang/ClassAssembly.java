package com.github.andyshao.lang;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 7, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 */
@FunctionalInterface
public interface ClassAssembly {
    public static final ClassAssembly DEFAULT = new ClassAssembly() {

        @SuppressWarnings("unchecked")
        @Override
        public <E> Class<E> assemble(String name , byte[] bs) {
            final ClassLoader classLoader = new ClassLoader() {

                @Override
                protected Class<?> findClass(String name) throws ClassNotFoundException {
                    return this.defineClass(name , bs , 0 , bs.length);
                }

            };

            try {
                return (Class<E>) classLoader.loadClass(name);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    };

    <E> Class<E> assemble(String name , byte[] bs);
}
