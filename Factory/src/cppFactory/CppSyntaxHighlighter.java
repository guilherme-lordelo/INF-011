package cppFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JScrollPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import interfaces.AbstractSyntaxHighlighter;

public class CppSyntaxHighlighter extends RSyntaxTextArea implements AbstractSyntaxHighlighter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CppSyntaxHighlighter() {
		

		this.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
		this.setEditable(false);
		this.setCodeFoldingEnabled(true);
	}

	@Override
	public JScrollPane highlight(File file) {
		try {
			FileReader fr = new FileReader(file);
			this.read(fr, "Java file");
		} catch(FileNotFoundException ex) {
			System.err.println(ex);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RTextScrollPane(this);
	}

}
