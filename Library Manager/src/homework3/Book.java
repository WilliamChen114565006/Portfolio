package homework3;

public class Book {
	private String name;
	private String author;
	private String genre;
	private long ISBN;
	private long checkOutUserID;
	private int yearPublished;
	private Date dueDate=new Date();
	private Book nextBook;
	private Boolean checkedOut;
	private Condition bookCondition;
	
	public enum Condition{
		NEW, GOOD, BAD, REPLACE;
	}
	
	public Book()
	{
		name="";
		author="";
		genre="";
		ISBN=-1L;
		checkOutUserID=-1L;
		yearPublished=-1;
		dueDate=new Date();
		checkedOut=false;
		nextBook=null;
		bookCondition=null;
	}
	
	public Book(String insertName, String insertAuthor, String insertGenre, long insertISBN, int insertYearPublished, Condition x)
	{
		name=insertName;
		author=insertAuthor;
		genre=insertGenre;
		ISBN=insertISBN;
		yearPublished=insertYearPublished;
		bookCondition=x;
	}
	
	public Condition getCondition()
	{
		return bookCondition;
	}
	
	public void setCondition(Condition x)
	{
		bookCondition=x;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public String getGenre()
	{
		return genre;
	}
	
	public long getISBN()
	{
		return ISBN;
	}
	
	public long getCheckOutID()
	{
		return checkOutUserID;
	}
	
	public Date getDueDate()
	{
		return dueDate;
	}
	
	public Boolean getCheckedOut()
	{
		return checkedOut;
	}
	
	public Book getNextBook()
	{
		return nextBook;
	}
	
	public int getYearPublished()
	{
		return yearPublished;
	}
	
	public void setName(String x)
	{
		name=x;
	}
	
	public void setAuthor(String x)
	{
		author=x;
	}
	
	public void setGenre(String x)
	{
		genre=x;
	}
	
	public void setISBN(long x)
	{
		ISBN=x;
	}
	
	public void setCheckOutID(long x)
	{
		checkOutUserID=x;
	}
	
	public void setDate(Date x)
	{
		dueDate=x;
	}
	
	public void setCheckedOut(boolean x)
	{
		checkedOut=x;
	}
	
	public void setNextBook(Book x)
	{
		nextBook=x;
	}
	
	
	public void setYearPublished(int x)
	{
		yearPublished=x;
	}
}
