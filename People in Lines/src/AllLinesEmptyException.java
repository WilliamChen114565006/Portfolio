
public class AllLinesEmptyException extends Exception{
	
	public AllLinesEmptyException()
	{
		super("All lines are empty");
	}
	
	public AllLinesEmptyException(String message)
	{
		super(message);
	}

}
