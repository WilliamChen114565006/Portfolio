package homework3;

public class EmptyStackException extends Exception{

	public EmptyStackException()
	{
		super("Stack is empty");
	}
	
	public EmptyStackException(String x)
	{
		super(x);
	}
}
