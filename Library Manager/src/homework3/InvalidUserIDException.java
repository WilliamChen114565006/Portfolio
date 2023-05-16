package homework3;

public class InvalidUserIDException extends Exception{

	public InvalidUserIDException()
	{
		super("User ID is invalid, with too many characters");
	}
	
	public InvalidUserIDException(String x)
	{
		super(x);
	}
}
