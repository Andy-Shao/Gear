package com.github.andyshao.lang;

import java.util.function.Supplier;

public class ClassDemo implements Supplier<String> {

    @Override
    public String get() {
        return "Hello,World!";
    }

}
