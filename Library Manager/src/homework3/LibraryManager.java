package homework3;

import java.util.Scanner;
import homework3.Book.Condition;

public class LibraryManager extends Exception{
	private static BookRepository bookRepository=new BookRepository();
	private static ReturnStack stackUse=new ReturnStack();
	
	
	public static void main(String[] arg) throws InvalidISBNException, InvalidUserIDException, BookCheckedOutBySomeoneElseException, BookAlreadyExistsException, BookDoesNotExistException, InvalidSortCriteraException, BookNotCheckedOutException, EmptyStackException
	{
		int thebreaker=0;
		while(thebreaker<1)
		{
			System.out.println("");
			Scanner myObj=new Scanner(System.in);
			System.out.print("(B) - Manage Book Repository \n	(C)- Checkout Book"
					+ "\n	(N) - Add New Book \n	(R) - Remove Book \n	(P) - Print Repository \n	(S) Sort Shelf"
					+ "\n		(I) - ISBN Number \n		(N) - Name \n		(A) - Author"
					+ "\n		(G) - Genre \n		(Y) - Year \n		(C) - Condition"
					+ "\n(R) - Manage Return Stack \n	(R) - Return Book \n	(S) - See Last Return "
					+ "\n	(C) - Check In Last Return \n	(P) - Print Return Stack\n(Q) - Quit"
					+ "\nPlease Select what to manage: ");
			String temp=myObj.nextLine();
			if(temp.equals("B"))
			{
				Scanner myObj2=new Scanner(System.in);
				System.out.print("Please select an option: ");
				String temp2=myObj2.nextLine();
				if (temp2.equals("C"))
				{
					Scanner myObj11=new Scanner(System.in);
					System.out.print("Please provide a library ID: ");
					long temp11=myObj11.nextLong();
					
					Scanner myObj12=new Scanner(System.in);
					System.out.print("Please provide an ISBN Number: ");
					long temp12=myObj12.nextLong();
					
					Scanner myObj13=new Scanner(System.in);
					System.out.print("Please provide a due date (MM/DY/XXXX): ");
					String temp13=myObj13.nextLine();
					
					Date tempDate=new Date();   //fix date
					tempDate.changeToDate(temp13);
					
					bookRepository.checkOutBook(temp12, temp11, tempDate);
					
				}
				
				else if (temp2.equals("N"))
				{
					Scanner myObj10=new Scanner(System.in);
					System.out.print("Please provide an ISBN number: ");
					long temp10=myObj10.nextLong();
					
					Scanner myObj4=new Scanner(System.in);
					System.out.print("Please provide a name of the book: ");
					String temp4=myObj4.nextLine();
					
					Scanner myObj5=new Scanner(System.in);
					System.out.print("Please provide an author: ");
					String temp5=myObj5.nextLine();
					
					Scanner myObj6=new Scanner(System.in);
					System.out.print("Please provide a genre: ");
					String temp6=myObj6.nextLine();
					
					Scanner myObj7=new Scanner(System.in);
					System.out.print("Please provide a publishing year: ");
					int temp7=myObj.nextInt();
					
					Scanner myObj8=new Scanner(System.in);
					System.out.print("Please provide a condition (all caps please): ");
					String temp8=myObj8.nextLine();
					
					bookRepository.addBook(temp10, temp4, temp5, temp6, temp7, Condition.valueOf(temp8)); 
					
					
				}
				
				else if (temp2.equals("R"))
				{
					
					Scanner myObj9=new Scanner(System.in);
					System.out.print("Please provide an ISBN Number: ");
					long temp9=myObj9.nextLong();
					
					bookRepository.removeBook(temp9);
					
				}
				
				else if (temp2.equals("P"))
				{
					Scanner myObj19=new Scanner(System.in);
					System.out.print("Please select a shelf: ");
					int temp19=myObj19.nextInt();
					
					System.out.print(bookRepository.toString(temp19));
				}
				
				else if (temp2.equals("S"))
				{
					Scanner myObj14=new Scanner(System.in);
					System.out.print("Please select a shelf: ");
					int temp14=myObj14.nextInt();
					
					Scanner myObj15=new Scanner(System.in);
					System.out.print("Please select a sorting criteria: ");
					String temp15=myObj15.nextLine();
					
					bookRepository.sortShelf(temp14, temp15);
				}
				
				else
				{
					System.out.println("That is an invalid character to use. Please try again.");
				}
			}
			
			else if(temp.equals("R"))
			{
				Scanner myObj3=new Scanner(System.in);
				System.out.print("Please select an option: ");
				String temp3=myObj.nextLine();
				if (temp3.equals("R"))
				{
					Scanner myObj16=new Scanner(System.in);
					System.out.print("Please provide the ISBN of the book you're returning: ");
					long temp16=myObj16.nextLong();
					
					Scanner myObj17=new Scanner(System.in);
					System.out.print("Please provide your Library UserID: ");
					long temp17=myObj.nextLong();
					
					Scanner myObj18=new Scanner(System.in);
					System.out.print("Please enter the current date: ");
					String temp18=myObj18.nextLine();
					
					Date temperDate=new Date();
					temperDate.changeToDate(temp18);
					
					boolean tempAns=stackUse.pushLog(temp16, temp17, temperDate, bookRepository); 
					
					if (tempAns==true)  //if true, then book was returned late so it creates a loop to pop every value till it is empty
					{
						System.out.println(temp16+" was returned late! Checking in all books...");
						int nodeptr9=stackUse.getManyItems();
						for(int i=0;i<nodeptr9;i++)
						{
							ReturnLog temperItem=stackUse.popLog();
							long tempISBNVal=temperItem.getISBNLog();
							bookRepository.checkInBook(tempISBNVal);

						}
					}
					else if(tempAns==false)
					{
						System.out.println(temp16+" was returned on time!");
					}
					
				}
				
				else if(temp3.equals("S"))
				{
					//reminder: get the title through book repository
					System.out.println(stackUse.peek().getISBNLog()+" is the next book to be checked in");
				}
				
				else if(temp3.equals("C"))
				{
					ReturnLog tempItem=stackUse.popLog();
					long tempISBNValue=tempItem.getISBNLog();
					bookRepository.checkInBook(tempISBNValue);
					System.out.println(tempISBNValue+" has been checked in!"); //add the title of the book too!
				}
				
				else if(temp3.equals("P"))
				{
					System.out.println(stackUse.toString());
				}
				
				else
				{
					System.out.println("Please enter a valid character. So, try again please.");
				}
				
			}
			
			else if(temp.equals("Q"))
			{
				System.out.println("Sorry to see you go!");
				thebreaker++;
			}
			else
			{
				System.out.println("Sorry, the input you entered is not possible. Please try again");
			}
		}
	}
}
