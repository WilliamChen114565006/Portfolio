package homework3;

public class InvalidISBNException extends Exception {
	public InvalidISBNException()
	{
		super("The ISBN is invalid, with too many characters");
	}

	public InvalidISBNException(String x)
	{
		super(x);
	}
}
