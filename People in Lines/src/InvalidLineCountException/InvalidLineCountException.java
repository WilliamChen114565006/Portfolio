package InvalidLineCountException;

public class InvalidLineCountException extends Exception {
	public InvalidLineCountException()
	{
		super("You cannot input negative lines");
	}
	
	public InvalidLineCountException(String message)
	{
		super(message);
	}
}
