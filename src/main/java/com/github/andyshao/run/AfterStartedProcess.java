package com.github.andyshao.run;

import java.util.List;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Dec 13, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 *
 */
public interface AfterStartedProcess {
    public void process(AfterStartedContext context);
    public List<AfterStartedNode> sequence(AfterStartedContext context);
    
    public static AfterStartedProcess DefaultProcess() {
        return new DefaultAfterStaredProcess();
    }
}
