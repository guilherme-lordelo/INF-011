import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import interfaces.AbstractFactory;
import interfaces.AbstractSyntaxHighlighter;
import interfaces.IBuilder;

public class Main {


	public static String [] supportedExtensions(String [] plugins) {

		for(int i = 0; i < plugins.length; i++) {
			plugins[i] = plugins[i].substring(0, plugins[i].length() - 11).toLowerCase();
		}

		return plugins;
	}
	
	
	public static void build(AbstractFactory factory, File file) {
		
		AbstractSyntaxHighlighter textArea = factory.createSyntaxHighlighter();
		JScrollPane sp = textArea.highlight(file);

		IBuilder builder = factory.createBuilder();

		JButton button = new JButton("Compile");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// whatever you need to do
				builder.compile(file);
				System.out.println("The button was pressed!");
			}
		});


		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		JFrame btnFrame = new JFrame();
		btnFrame.setContentPane(button);
		btnFrame.setTitle("Button");
		btnFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnFrame.pack();
		btnFrame.setLocation((int)(ge.getCenterPoint().x*0.95), (int)(ge.getCenterPoint().y*1.35));
		btnFrame.setVisible(true);

		JFrame frame = new JFrame();
		frame.setContentPane(sp);
		frame.setTitle("Highlighter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocation((int)(ge.getCenterPoint().x*0.75), (int)(ge.getCenterPoint().y*0.375));
		frame.setVisible(true);

	}


	public static void main(String []args){

		File currentDir = new File("./plugins");
		String []plugins = currentDir.list();
		URL[] jars = new URL[plugins.length];
		for (int i = 0; i < plugins.length; i++)
		{
			System.out.println(i+1 + " - " + plugins[i].split("\\.")[0]);
			try {
				jars[i] = (new File("./plugins/" + plugins[i])).toURL();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
		URLClassLoader ulc = new URLClassLoader(jars);

		Scanner sc = new Scanner(System.in);
		System.out.println("Provide the file name: ");
		String fileName = sc.nextLine();
		sc.close();
		String fileType = fileName.split("\\.")[1];
		String fileTypeCapitalized = fileType.substring(0, 1).toUpperCase() + fileType.substring(1);

		if(Arrays.asList(supportedExtensions(plugins)).contains(fileType)) {
			try {
				
				Class<?> metaFactory =  Class.forName
						(fileType + "Factory" + "." + fileTypeCapitalized + "Factory", true, ulc);
				Method method = metaFactory.getMethod("getInstance");
				build((AbstractFactory) method.invoke(metaFactory), new File(fileName));
				
			} catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException |
					SecurityException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
		else
			System.out.println("There is no plugin that supports this file");

	}

}
