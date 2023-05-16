
public class TakenSeatException extends Exception {
	public TakenSeatException()
	{
		super("The seat is already taken");
	}
	
	public TakenSeatException(String message)
	{
		super(message);
	}
}
