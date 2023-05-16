package homework3;

public class BookAlreadyExistsException extends Exception{

	public BookAlreadyExistsException()
	{
		super("The Book already exists");
	}
	
	public BookAlreadyExistsException(String x)
	{
		super(x);
	}
}
