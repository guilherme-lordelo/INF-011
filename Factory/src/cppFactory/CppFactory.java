package cppFactory;

import interfaces.AbstractFactory;
import interfaces.AbstractSyntaxHighlighter;
import interfaces.IBuilder;

public class CppFactory implements AbstractFactory {
	
	private CppFactory() {}
	private static CppFactory instance = null;
	
	 public static CppFactory getInstance() {
	        if (instance == null)
	            instance = new CppFactory();
	        return instance;
	    }

	@Override
	public AbstractSyntaxHighlighter createSyntaxHighlighter() {
		return new CppSyntaxHighlighter();
	}

	@Override
	public IBuilder createBuilder() {
		return new CppBuilder();
	}

}
