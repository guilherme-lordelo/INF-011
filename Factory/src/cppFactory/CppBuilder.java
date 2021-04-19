package cppFactory;

import java.io.File;
import java.io.IOException;

import interfaces.IBuilder;

public class CppBuilder implements IBuilder {

	@Override
	public void compile(File file) {
		
		String fileName = file.getName();
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("g++ "+fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
