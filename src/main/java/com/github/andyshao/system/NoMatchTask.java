package com.github.andyshao.system;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 10, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class NoMatchTask implements Task {

    @Override
    public Task getNextTask() {
        return null;
    }

    @Override
    public boolean isDuty(String[] args) {
        return true;
    }

    @Override
    public void process(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(this.getClass());
    }

}
