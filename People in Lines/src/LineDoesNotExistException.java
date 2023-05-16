
public class LineDoesNotExistException extends Exception{

	public  LineDoesNotExistException()
	{
		super("A line you inputted cannot be removed because it doesn't exist");
	}
	
	public  LineDoesNotExistException(String message)
	{
		super(message);
	}
}
