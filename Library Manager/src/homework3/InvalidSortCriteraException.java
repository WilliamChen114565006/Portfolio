package homework3;

public class InvalidSortCriteraException extends Exception {
	public InvalidSortCriteraException()
	{
		super("Invalid character for sorting");
	}
	
	public InvalidSortCriteraException(String x)
	{
		super(x);
	}
}
