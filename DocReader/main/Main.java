package main;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;

import target.IDocument;

public class Main {

	private static final Class<?>[] parameters = new Class[]{URL.class};

	private static String loadJar(String fileType) {

		String pluginName = fileType.toUpperCase() + "Document";
		try {
			URL u = (new File("../plugins/" + pluginName + ".jar")).toURL();
			URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Class<?> sysclass = URLClassLoader.class;
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[]{u});


		}catch (MalformedURLException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return pluginName;
	}


	public static void main (String []args) {
		IDocument doc = null;

		System.out.println("Provide the file name:");
		Scanner sc = new Scanner(System.in);
		
		
		File file = new File(sc.nextLine());
		String fileType = file.getName().split("\\.")[1].toUpperCase();
		System.out.println("Press 0 to open or 1 to edit:");
		int input = sc.nextInt();
		
		try {
			doc = (IDocument) Class.forName("adapter." + loadJar(fileType)).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sc.close();

		if(input == 0) {
			doc.open(file);
		}
		else if(input == 1) {
			File dir = new File("./plugins");
			String []plugins = dir.list();
			for(int i = 0; i < plugins.length; i++) {
				plugins[i] = plugins[i].substring(0, plugins[i].length()-12);
				System.out.println(plugins[i]);
			}
			if(Arrays.asList(plugins).contains(fileType)) {
				JFrame frame = doc.getEditor(file);
				frame.pack();
				frame.setVisible(true);
			}
			else
				System.err.println("File not supported");
		}
		else
			System.err.println("Invalid option");
	}

}
