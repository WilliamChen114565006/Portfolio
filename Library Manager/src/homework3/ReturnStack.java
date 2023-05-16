package homework3;

public class ReturnStack extends Exception{
	private ReturnLog topLog;
	private int manyitems=0;
	
	public ReturnStack()
	{
		topLog=null;
	}
	
	public boolean pushLog(long returnISBN, long returnUserID, Date returnDate, BookRepository bookRepoRef) throws InvalidISBNException, InvalidUserIDException, BookNotCheckedOutException, BookCheckedOutBySomeoneElseException
	{
		String tempISBN=String.valueOf(returnISBN);  //checks for the ISBN value if it is valid
		if(tempISBN.length()>13)
		{
			throw new InvalidISBNException();
		}
		
		String tempUser=String.valueOf(returnUserID);   //checks if the userID is less than 10 or 10 to be valid
		if(tempUser.length()>10)
		{
			throw new InvalidUserIDException();
		}
		
		

		Date dueDateBook=null;
		int index=0;
		while (index<bookRepoRef.getShelves().length)   //surfs through the shelves
		{
			Book nodeptr2=bookRepoRef.getShelves()[index].getHeadBook();
			while (nodeptr2!=null && nodeptr2.getISBN()!=returnISBN)   //surfs through the specific shelf to find if the ISBN value is checked out already or hasn't been checked out
			{	
				nodeptr2=nodeptr2.getNextBook();
			}
			
			if(nodeptr2!=null)
			{
				if (nodeptr2.getCheckedOut()==false)     //if false, it means the book has not been checked out, so it can't be checked back in
				{
					throw new BookNotCheckedOutException();
				}
				
				else if(nodeptr2.getCheckedOut()==true && nodeptr2.getCheckOutID()!=returnUserID)  //checks if the ID of the user is the one who checked out the book
				{
					throw new BookCheckedOutBySomeoneElseException();
				}
				
				dueDateBook=nodeptr2.getDueDate();   //gets due date of the book to later be compared
			}
			index++;
		}
		
		int compareValue=dueDateBook.compare(dueDateBook, returnDate);		//compares the value to if it is late or not
		
		ReturnLog temp=new ReturnLog(returnISBN, returnUserID, returnDate);  //adds or pushes the new temp onto the stack to later be checked in
		temp.setNextLog(topLog);
		topLog=temp;
		manyitems++;
		
		if(compareValue==-1)    //sees if the book is checked in on time
		{	
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public ReturnLog popLog() throws EmptyStackException
	{
		ReturnLog answer;
		if (topLog==null)
		{
			throw new EmptyStackException();
		}
		answer=topLog;
		topLog=topLog.getNextLog();
		manyitems--;
		return answer;
	}
	
	public ReturnLog peek() throws EmptyStackException
	{
		if (topLog==null)
		{
			throw new EmptyStackException();
		}
		return topLog;
	}
	
	public int getManyItems()
	{
		return manyitems;
	}
	
	public String toString()
	{
		System.out.println("ISBN             UserID               Return Date");
		String answer="";
		ReturnLog nodeptr=topLog;
		while(nodeptr!=null)   //a loop to get every element in the stack 
		{
			answer+=nodeptr.getISBNLog()+"      "+nodeptr.getUserID()+"      "+nodeptr.getReturnDate().toString();
			nodeptr=nodeptr.getNextLog();
		}	
		return answer;
	}
}
