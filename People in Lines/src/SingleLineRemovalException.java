
public class SingleLineRemovalException extends Exception {

	public SingleLineRemovalException()
	{
		super("Removing this line would result in no lines left");
	}
	
	public SingleLineRemovalException(String message)
	{
		super(message);
	}
}
