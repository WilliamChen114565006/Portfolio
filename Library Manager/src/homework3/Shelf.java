package homework3;

public class Shelf extends Exception{
	private Book headBook;
	private Book tailBook;
	private int length;
	private SortCriteria shelfSortCriteria;
	
	public Shelf()
	{
		headBook=null;
		tailBook=null;
		length=0;
		shelfSortCriteria=shelfSortCriteria.N;
	}

	public Book getHeadBook()
	{
		return headBook;
	}
	
	public enum SortCriteria
	{
		I, N, A, G, Y, C;
	}
	
	public void addBook(Book addedBook) throws BookAlreadyExistsException
	{
		Book nodeptr=headBook;
		while (nodeptr!=null)
		{
			if(nodeptr.getISBN()==addedBook.getISBN())
			{
				throw new BookAlreadyExistsException();
			}
			nodeptr=nodeptr.getNextBook();
		}
		
		
		//where it starts
		
		
		Book answer=addedBook;
		answer.setNextBook(headBook);
		headBook=answer;
		if(tailBook==null)
		{
			tailBook=headBook;
		}
		sort(shelfSortCriteria);
		
		
		
		
		length++;
		
	}
	
	public void removeBook(long removedISBN) throws InvalidISBNException, BookDoesNotExistException
	{
		String temp=String.valueOf(removedISBN);
		if(temp.length()>13)
		{
			throw new InvalidISBNException();
		}
		
		Book nodeptr=headBook;
		Book prevptr=null;
		
		if(nodeptr.getISBN()==removedISBN)   //finds if the head node is automatically the one that needs to be removed
		{
			headBook=nodeptr.getNextBook();
			if(headBook.getNextBook()==null)
			{
				tailBook=headBook;
			}
		}
		
		else if(nodeptr.getNextBook()==null) //sees if there is only one book to remove
		{
			headBook=null;
		}
		
		else {
		
			while(nodeptr!=null && nodeptr.getISBN()!=removedISBN)  //checks to see if nodeptr is the correct ISBN value to remove then sets prevptr to skip nodeptr so it can be removed
			{
				prevptr=nodeptr;
				nodeptr=nodeptr.getNextBook();
			}
			
			
			if(nodeptr!=null)
			{
				if (nodeptr.getNextBook()==null)  //checks if tail is the one that needs to be removed
				{
					prevptr.setNextBook(null);
					tailBook=prevptr;
				}
				else
				{
					System.out.println(nodeptr.getName()+" has successfully been removed.");
					prevptr.setNextBook(nodeptr.getNextBook());    //sets prevptr to the node past nodeptr
					length--;
				}
			}
		
			else if(nodeptr==null)
			{
				throw new BookDoesNotExistException(); //the book does not exist if nodeptr is null because it hasn't found the correct value
			}
		}
		
	}
	
	public void sort(SortCriteria sortCriteria)
	{
		shelfSortCriteria=sortCriteria;   //sets the shelf sort criteria to be default
		
					
	}
    
	
	
	
	
	
	public String toString()
	{
		//first part has to be conditioned to be sorted :/
		if (shelfSortCriteria==SortCriteria.I)
		{
			System.out.println("ISBN                Checked Out        Due Date      Checkout UserID");
			String answer="";
			Book nodeptr3=headBook;
			while(nodeptr3!=null)
			{
				String ans=String.valueOf(nodeptr3.getISBN());
				int numZeros=13-ans.length();
				if(numZeros>0)
				{
					for (int i=0; i<numZeros;i++)
					{
						ans="0"+ans;
					}
				}
					
				System.out.print(ans+"             "+nodeptr3.getCheckedOut()+"          "+nodeptr3.getDueDate().toString()+
					       "            "+nodeptr3.getCheckOutID()+"\n");
				nodeptr3=nodeptr3.getNextBook();
			}
			return answer;
		}
		
		else if(shelfSortCriteria==SortCriteria.N)
		{
			System.out.println("Name                Checked Out        Due Date      Checkout UserID");
			String answer="";
			Book nodeptr3=headBook;
			while(nodeptr3!=null)
			{
				System.out.print(nodeptr3.getName()+"             "+nodeptr3.getCheckedOut()+"          "+nodeptr3.getDueDate().toString()+
					       "            "+nodeptr3.getCheckOutID()+"\n");
				nodeptr3=nodeptr3.getNextBook();
			}
			return answer;
		}
		else if(shelfSortCriteria==SortCriteria.A)
		{
			System.out.println("Author                Checked Out        Due Date      Checkout UserID");
			String answer="";
			Book nodeptr3=headBook;
			while(nodeptr3!=null)
			{
				System.out.print(nodeptr3.getAuthor()+"             "+nodeptr3.getCheckedOut()+"          "+nodeptr3.getDueDate().toString()+
					       "            "+nodeptr3.getCheckOutID()+"\n");
				nodeptr3=nodeptr3.getNextBook();
			}
			return answer;
		}
		else if(shelfSortCriteria==SortCriteria.G)
		{
			System.out.println("Genre                Checked Out        Due Date      Checkout UserID");
			String answer="";
			Book nodeptr3=headBook;
			while(nodeptr3!=null)
			{
				System.out.print(nodeptr3.getGenre()+"             "+nodeptr3.getCheckedOut()+"          "+nodeptr3.getDueDate().toString()+
					       "            "+nodeptr3.getCheckOutID()+"\n");
				nodeptr3=nodeptr3.getNextBook();
			}
			return answer;
		}
		else if(shelfSortCriteria==SortCriteria.Y)
		{
			System.out.println("Year                Checked Out        Due Date      Checkout UserID");
			String answer="";
			Book nodeptr3=headBook;
			while(nodeptr3!=null)
			{
				System.out.print(nodeptr3.getYearPublished()+"             "+nodeptr3.getCheckedOut()+"          "+nodeptr3.getDueDate().toString()+
					       "            "+nodeptr3.getCheckOutID()+"\n");
				nodeptr3=nodeptr3.getNextBook();
			}
			return answer;
		}
		else if(shelfSortCriteria==SortCriteria.C)
		{
			System.out.println("Condition                Checked Out        Due Date      Checkout UserID");
			String answer="";
			Book nodeptr3=headBook;
			while(nodeptr3!=null)
			{
				System.out.print(nodeptr3.getCondition()+"             "+nodeptr3.getCheckedOut()+"          "+nodeptr3.getDueDate().toString()+
					       "            "+nodeptr3.getCheckOutID()+"\n");
				nodeptr3=nodeptr3.getNextBook();
			}
			return answer;
		}
		return "";

	}
}
