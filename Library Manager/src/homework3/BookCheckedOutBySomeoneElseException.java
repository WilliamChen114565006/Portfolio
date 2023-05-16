package homework3;

public class BookCheckedOutBySomeoneElseException extends Exception {

	public BookCheckedOutBySomeoneElseException()
	{
		super("Book has already been checked out by someone else");
	}
	
	public BookCheckedOutBySomeoneElseException(String x)
	{
		super(x);
	}
}
