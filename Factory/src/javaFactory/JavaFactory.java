package javaFactory;

import interfaces.AbstractFactory;
import interfaces.AbstractSyntaxHighlighter;
import interfaces.IBuilder;

public class JavaFactory implements AbstractFactory {
	
	private JavaFactory() {}
	private static JavaFactory instance = null;
	
	 public static JavaFactory getInstance() {
	        if (instance == null)
	            instance = new JavaFactory();
	        return instance;
	    }

	@Override
	public AbstractSyntaxHighlighter createSyntaxHighlighter() {
		return new JavaSyntaxHighlighter();
	}

	@Override
	public IBuilder createBuilder() {
		return new JavaBuilder();
	}

}
