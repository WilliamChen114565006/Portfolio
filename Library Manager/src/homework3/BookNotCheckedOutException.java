package homework3;

public class BookNotCheckedOutException extends Exception {

	public BookNotCheckedOutException()
	{
		super("Book has not been checked out yet");
	}
	
	public BookNotCheckedOutException(String x)
	{
		super(x);
	}
}
