import java.io.File;
import java.util.Scanner;

import InvalidLineCountException.InvalidLineCountException;
public class SecurityManager {
	private static SecurityCheck securityCheck=new SecurityCheck();
	
	
	public static void main(String[] args) throws TakenSeatException, AllLinesEmptyException, SingleLineRemovalException, LineDoesNotExistException, InvalidLineCountException
	{
		int theBreaker=0;
		while(theBreaker<1)
		{
			System.out.println("");
			int count=1;
			Line nodeptr=securityCheck.getHead();
			while(nodeptr!=null)
			{
				System.out.println("Line "+count+": "+nodeptr.getLength()+" People waiting");
				count++;
				nodeptr=nodeptr.getLineLink();
			}
			
			
			Scanner myObj=new Scanner(System.in);
			System.out.print("(A) - Add Person \n(N) - Next Person \n(R) - Remove Lines \n"
					+ "(L) - Add Lines \n(P) - Print All Lines \n(Q) - Quit\n \nPlease select an Option: ");
			String temp=myObj.nextLine();
			
			if (temp.equals("A"))
			{
				Scanner myObj2=new Scanner(System.in);
				System.out.print("Please enter a name: ");
				String temp2=myObj2.nextLine();
				
				Scanner myObj3=new Scanner(System.in);
				System.out.print("Please enter a seat number: ");
				int temp3=myObj3.nextInt();
				securityCheck.addPerson(temp2,temp3);
			}
			else if(temp.equals("N"))
			{
				Line nodeptrTwo=securityCheck.getHead();

				Person tempG=securityCheck.removeNextAttendee();
				System.out.println(tempG.getName()+" has been removed from line");

			}
			else if(temp.equals("R"))
			{
				Scanner myObj5=new Scanner(System.in);
				System.out.print("Lines to remove(add commas for more lines to remove): ");
				String temp5=myObj5.nextLine();
				
				
				int counter=0;
				char commas=',';
				int commaspace=0;
				int[]storage=new int[0];
				
				if(temp5.indexOf(commas)<0)
				{
					storage=new int[1];
					storage[0]=Integer.valueOf(temp5);
				}
				else
				{
					for (int j=0;j<temp5.length();j++)
					{
						if(temp5.charAt(j)==commas)
						{
							counter++;
						}
					}
				
					storage=new int[counter+1];
				
					for(int i=0;i<counter;i++)
					{
						int location=temp5.indexOf(commas, commaspace);				
						storage[i]=(   Integer.valueOf( temp5.substring(commaspace, location).replaceAll("\\s", "")  )   );
						System.out.println(temp5.substring(commaspace, location).replaceAll("\\s", ""));
						commaspace=location+1;
					}
						storage[counter]= Integer.valueOf(temp5.substring(commaspace+1,  temp5.length()).replaceAll("\\s", ""));
						System.out.println(temp5.substring(commaspace+1,  temp5.length()).replaceAll("\\s", ""));
				}
				securityCheck.removeLines(storage);
				
				
				System.out.print("Lines ");
				for(int j=0;j<storage.length;j++)
				{
					System.out.print(storage[j]+", ");
				}
				System.out.println("were removed");
				
			}
			else if(temp.equals("L"))
			{
				Scanner myObj4=new Scanner(System.in);
				System.out.print("Add how many more lines? ");
				int temp4=myObj4.nextInt();
				securityCheck.addNewLines(temp4);
			}
			
			else if(temp.equals("P"))
			{
				securityCheck.printTable();
			}
			
			else if(temp.equals("Q"))
			{
				System.out.println("Sorry to see you go!");
				break;
			}
			else
			{
				System.out.println("Please enter a valid character.");
			}
		}
		
	}
	
}





