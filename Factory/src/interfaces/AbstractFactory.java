package interfaces;


public interface AbstractFactory {
	
	public AbstractSyntaxHighlighter createSyntaxHighlighter();
	public IBuilder createBuilder();

}
