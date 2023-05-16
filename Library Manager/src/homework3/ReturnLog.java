package homework3;

public class ReturnLog {
	private long ISBN;
	private long userID;
	private Date returnDate;
	private ReturnLog nextLog;
	
	public ReturnLog()
	{
		ISBN=0;
		userID=0;
		returnDate=null;
		nextLog=null;
	}
	
	public ReturnLog(long insISBN, long insUserID, Date insDate)
	{
		ISBN=insISBN;
		userID=insUserID;
		returnDate=insDate;
	}
	
	public long getISBNLog()
	{
		return ISBN;
	}
	
	public long getUserID()
	{
		return userID;
	}
	
	public Date getReturnDate()
	{
		return returnDate;
	}
	
	public ReturnLog getNextLog()
	{
		return nextLog;
	}
	
	public void setISBNLog(long x)
	{
		ISBN=x;
	}
	
	public void setUserIDLog(long x)
	{
		userID=x;
	}
	
	public void setReturnDate(Date x)
	{
		returnDate=x;
	}
	
	public void setNextLog(ReturnLog x)
	{
		nextLog=x;
	}
	
}
