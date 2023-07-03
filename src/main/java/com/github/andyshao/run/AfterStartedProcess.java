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
    /**
     * Do the bean properties injection operation
     * @param context the bean context
     */
    public void process(AfterStartedContext context);

    /**
     * Get the bean initialization sequence
     * @param context the bean context
     * @return the sequence of bean initialization
     */
    public List<AfterStartedNode> sequence(AfterStartedContext context);

    /**
     * the default installment of the bean property injection
     * @return the default {@link AfterStartedProcess} object.
     */
    public static AfterStartedProcess DefaultProcess() {
        return new DefaultAfterStaredProcess();
    }
}
