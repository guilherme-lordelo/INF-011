package interfaces;

import java.io.File;

import javax.swing.JScrollPane;

public interface AbstractSyntaxHighlighter {
	
	public JScrollPane highlight(File file);

}
