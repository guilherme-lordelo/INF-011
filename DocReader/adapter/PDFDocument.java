package adapter;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.PDFRenderer;

import target.IDocument;

public class PDFDocument extends JFrame implements IDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel label;
	private JButton nextBtn;
	private BufferedImage bim;
	private PDFRenderer pdfRenderer;
	private PDDocument document;
	private File file;
	private int index = 0;
	
	private static final Class<?>[] parameters = new Class[]{URL.class};

	private void increment() { 
		index++;
	}

	private void reset() { 
		index = 0;
	}


	@Override
	public void open(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadJars() {
		
		try {
		URL u = (new File("./PDFBoxDep/pdfbox-3.0.0-RC1.jar")).toURL();
		URL u2 = (new File("./PDFBoxDep/fontbox-3.0.0-RC1.jar")).toURL();
		URL u3 = (new File("./PDFBoxDep/commons-logging-1.2.jar")).toURL();
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<?> sysclass = URLClassLoader.class;
		Method method = sysclass.getDeclaredMethod("addURL", parameters);
		method.setAccessible(true);
		method.invoke(sysloader, new Object[]{u});
		method.invoke(sysloader, new Object[]{u2});
		method.invoke(sysloader, new Object[]{u3});

		Class.forName("myplugin.MyPlugin").newInstance();
		
		}catch (MalformedURLException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException |
				InstantiationException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


@Override
public JFrame getEditor(File file) {

	document = null;
	this.file = file;
	setLabel();

	final PDPageTree tree = document.getPages();

	nextBtn = new JButton(">");
	nextBtn.addActionListener(new ActionListener() {

		private PDPageTree tree;
		private Iterator<PDPage> iterator;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(iterator.hasNext()) {
				iterator.next();
				increment();
			}
			else {
				this.iterator = tree.iterator();
				reset();
			}
			redo();

		}

		private ActionListener init(PDPageTree tree){
			this.tree = tree;
			this.iterator = tree.iterator();

			return this;
		}

	}.init(tree));

	this.getContentPane().add(nextBtn, BorderLayout.EAST);
	this.label = new JLabel(new ImageIcon(bim));
	this.add(this.label);
	this.setTitle(file.getName());
	this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	return this;
}

private void setLabel() {
	try {
		document = Loader.loadPDF(this.file);
		pdfRenderer = new PDFRenderer(this.document);
		bim = pdfRenderer.renderImage(this.index);
		document.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

private void redo() {
	setLabel();
	label.setIcon(new ImageIcon(this.bim));
	this.repaint();
}

}
