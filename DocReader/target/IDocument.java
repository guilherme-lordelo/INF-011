package target;

import java.io.File;

import javax.swing.JFrame;

public interface IDocument {
	
	public void open(File file);
	public JFrame getEditor(File file);

}
