package javaFactory;

import java.io.File;
import java.io.IOException;

import interfaces.IBuilder;

public class JavaBuilder implements IBuilder {

	@Override
	public void compile(File file) {
		
		String fileName = file.getName();
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("javac "+fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
						
	}

}
