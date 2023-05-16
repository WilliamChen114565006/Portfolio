package homework3;

import homework3.Book.Condition;
import homework3.Shelf.SortCriteria;

public class BookRepository extends Exception {
	private Shelf[] shelves=new Shelf[10];
	
	public BookRepository()
	{
		for (int i=0;i<10;i++)
		{
			shelves[i]=new Shelf();
		}
	}
	
	public Shelf[] getShelves()
	{
		return shelves;
	}
	
	public void checkInBook(long checkedInISBN)
	{
		for (int j=0;j<shelves.length;j++)      //searches each shelves through a for loop and then each linked list to find the ISBN Value
		{
			Book nodeptr3=shelves[j].getHeadBook();
			while(nodeptr3!=null && nodeptr3.getISBN()!=checkedInISBN)
			{
				nodeptr3=nodeptr3.getNextBook();                     
			}
			
			if(nodeptr3!=null)               //if they found the ISBN value, nodeptr would not have been null
			{
				nodeptr3.setCheckedOut(false);            //sets the node so that the book is registered as not checked out as it was checked back in
				nodeptr3.setCheckOutID(-1);
				Date tempDate=new Date();
				nodeptr3.setDate(tempDate);
			}
		}
		
	}
	
	public void checkOutBook(long checkedOutISBN, long checkOutUserID, Date dueDate) throws InvalidISBNException, InvalidUserIDException, BookCheckedOutBySomeoneElseException
	{
		String tempISBNID=String.valueOf(checkedOutISBN);
		String tempUserIDP=String.valueOf(checkOutUserID);
	
		if(tempISBNID.length()>13)
		{
			throw new InvalidISBNException();     //checks length of the ISBN to make sure it is valid
		}
		
		if(tempUserIDP.length()>10)
		{
			throw new InvalidUserIDException();       //checks if userID is correct length
		}
		
		
		for (int b=0;b<shelves.length;b++)
		{
			Book nodeptr4=shelves[b].getHeadBook();
			while(nodeptr4!=null && nodeptr4.getISBN()!=checkedOutISBN)   //searches for correct ISBN to check out
			{
				nodeptr4=nodeptr4.getNextBook();
			}
			
			if(nodeptr4!=null)
			{
				if(nodeptr4.getCheckedOut()==true)
				{
					throw new BookCheckedOutBySomeoneElseException();    //checks first if book already is checked out by seeing if getCheckedOut is true

				}
				else {
					nodeptr4.setCheckOutID(checkOutUserID);    //sets the userid to the node and the date it was checked out and then sets the book to being checked out
					nodeptr4.setDate(dueDate);
					nodeptr4.setCheckedOut(true);
					System.out.println("The book has successfully been checked out");
					break;
				}
			}
			
		}
	}
	
	public void addBook(long addISBN, String addName, String addAuthor, String addGenre, int addYear, Condition addCondition) throws InvalidISBNException, BookAlreadyExistsException
	{
		Book temp=new Book(addName, addAuthor, addGenre, addISBN, addYear, addCondition);	
		temp.setCheckedOut(false);      //whenever a book is added, it automatically cannot be checked out
		
		
		String checker= String.valueOf(temp.getISBN());
		if(checker.length()>13)
		{
			throw new InvalidISBNException();
		}
		

		boolean added=false;	//sees if book was sucessfully added	
		
		if (checker.length()<13)     //if the ISBN is less than 13, it automatically means the leading digit is 0
		{
			
			shelves[0].addBook(temp);
			added=true;
		}
		
		else if (Long.parseLong(checker.substring(0,1))==1)   //this checks if the front digit is the same for a corresponding book shelf and assigns the book the correct shelf
		{
		
			shelves[1].addBook(temp);
			added=true;
		}
		
		else if (Long.parseLong(checker.substring(0,1))==2)
		{
			
			shelves[2].addBook(temp);
			added=true;
		}
		
		else if (Long.parseLong(checker.substring(0,1))==3)
		{
			
			shelves[3].addBook(temp);
			added=true;
		}
		
		else if (Long.parseLong(checker.substring(0,1))==4)
		{
			
			shelves[4].addBook(temp);
			added=true;
		}
		
		else if (Long.parseLong(checker.substring(0,1))==5)
		{
			
			shelves[5].addBook(temp);
			added=true;
		}
		
		else if (Long.parseLong(checker.substring(0,1))==6)
		{
			
			shelves[6].addBook(temp);
			added=true;
		}
		
		else if (Long.parseLong(checker.substring(0,1))==7)
		{
			
			shelves[7].addBook(temp);
			added=true;
		}
		
		else if (Long.parseLong(checker.substring(0,1))==8)
		{
			
			shelves[8].addBook(temp);
			added=true;
		}
		
		else if (Long.parseLong(checker.substring(0,1))==9)
		{
			
			shelves[9].addBook(temp);
			added=true;
		}
		
		
		if (added==true)  //if book has been added, it will print it out
		{
			System.out.println(temp.getName()+" has successfully been added.");
		}
	}
	
	public void removeBook(long removeISBN) throws InvalidISBNException, BookDoesNotExistException
	{
		 String tempCheckISBN=String.valueOf(removeISBN);
		 if(tempCheckISBN.length()>13)
		 {
			 throw new InvalidISBNException();    //checks if ISBN value is invalid
		 }
		 
		 
		 if (tempCheckISBN.length()<13)     //finds out the leading digits so it knows what shelf to go through to remove a book
			{
				
				shelves[0].removeBook(removeISBN);   //calls removebook to find a remove the specific book with the isbn value
			}
			
			else if (Long.parseLong(tempCheckISBN.substring(0,1))==1)
			{
				shelves[1].removeBook(removeISBN);
				
			}
			
			else if (Long.parseLong(tempCheckISBN.substring(0,1))==2)
			{
				shelves[2].removeBook(removeISBN);
				
			}
			
			else if (Long.parseLong(tempCheckISBN.substring(0,1))==3)
			{
				shelves[3].removeBook(removeISBN);
				
			}
			
			else if (Long.parseLong(tempCheckISBN.substring(0,1))==4)
			{
				shelves[4].removeBook(removeISBN);
				
			}
			
			else if (Long.parseLong(tempCheckISBN.substring(0,1))==5)
			{
				shelves[5].removeBook(removeISBN);
				
			}
			
			else if (Long.parseLong(tempCheckISBN.substring(0,1))==6)
			{
				shelves[6].removeBook(removeISBN);
				
			}
			
			else if (Long.parseLong(tempCheckISBN.substring(0,1))==7)
			{
				shelves[7].removeBook(removeISBN);
				
			}
			
			else if (Long.parseLong(tempCheckISBN.substring(0,1))==8)
			{
				shelves[8].removeBook(removeISBN);
				
			}
			
			else if (Long.parseLong(tempCheckISBN.substring(0,1))==9)
			{
				shelves[9].removeBook(removeISBN);
				
			}
		 
	}
	
	public void sortShelf(int shelfInd, String sortCriteria) throws InvalidSortCriteraException
	{
		if (!(sortCriteria.equals("I")) &&    !(sortCriteria.equals("N")) &&    !(sortCriteria.equals("A")) &&    !(sortCriteria.equals("G")) &&    !(sortCriteria.equals("Y")) &&    !(sortCriteria.equals("C")))
		{
			throw new InvalidSortCriteraException();   //throws if the input is not one of the corresponding criterias
		}
		shelves[shelfInd].sort(SortCriteria.valueOf(sortCriteria));   //calls upon the sort method in shelf to reorganize the shelf given a critieria
	}
	
	public String toString(int index)
	{
		return shelves[index].toString();  //calls to print the specific shelf through the given index
	}
	
}
