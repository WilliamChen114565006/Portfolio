package homework3;

public class BookDoesNotExistException extends Exception {

	public BookDoesNotExistException()
	{
		super("Book does not exist");
	}
	
	public BookDoesNotExistException(String x)
	{
		super(x);
	}
}
