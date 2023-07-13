package com.github.andyshao.util.stream;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 * @deprecated unnecessary
 *
 */
@Deprecated(since = "5.0.0.RELEASE")
public class ThrowableException extends Exception {
	private static final long serialVersionUID = 3780569937997515984L;
	
	public ThrowableException(Throwable e) {
		super(e);
	}

	public static Exception convertToException(Throwable e) {
		if(e instanceof Exception) return (Exception)e;
		return new ThrowableException(e);
	}
}
