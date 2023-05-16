import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.PrintWriter;

public class PersonManager extends Exception implements Cloneable {
	private static PersonDataManager guy=new PersonDataManager();
	
	public static void main(String args[])
	{
		int thebreaker=0;                             //sets a while loop till the person wants to exit
		while(thebreaker!=1) {
		Scanner myObjTwo=new Scanner(System.in);
		System.out.println("(I) - Import from File"
				+ " \n(A) - Add Person \n(R) - Remove Person \n(G) - Get Info on Person \n"
				+ "(P) - Print Table \n(S) - Save File \n(Q) - Quit\n \nPlease select an Option: ");
		String tempTwo=myObjTwo.nextLine();
		
		if (tempTwo.equals("I"))
		{
			Scanner myObjThree=new Scanner(System.in);
			System.out.print("Please enter a location: ");
			String tempThree=myObjThree.nextLine();
			guy.buildFromFile(tempThree);
		}
		else if(tempTwo.equals("A"))
		{
			Person tempP=new Person();									//creates name, gender, etc of input and stores it into Person P
			try {
			Scanner myObjFour=new Scanner(System.in);
			System.out.print("Please enter the name of the person: ");
			String tempFour=myObjFour.nextLine();
			
			char firstLetter=tempFour.charAt(0);
			if(!(Character.isUpperCase(firstLetter)))
			{
				throw new IllegalArgumentException("The first letter in a name needs to be capitalized");
			}
			for(int i=0;i<tempFour.length();i++)                //checks if there is a number in the name
			{
				char ch=tempFour.charAt(i);
				if((Character.isDigit(ch))  )
				{
					throw new IllegalArgumentException("Names cannot have numbers");
				}
			}
			for(int i=1;i<tempFour.length();i++)      //checks if there is an uppercase letter in the name besides the first letter
			{
				char ch2=tempFour.charAt(i);
				if( (Character.isUpperCase(ch2)) )
				{
					throw new IllegalArgumentException("Names cannot have uppercase");
				}
			}
			
			
			Scanner myObj5=new Scanner(System.in);
			System.out.print("Please enter the age: ");
			int temp5=myObj5.nextInt();
			
			Scanner myObj6=new Scanner(System.in);
			System.out.print("Please enter the gender (M or F): ");
			String temp6=myObj6.nextLine();
			if(!(temp6.equals("M"))&&!(temp6.equals("F")))
			{
				throw new IllegalArgumentException("Input for gender has to be either M(male) or F(female)");
			}
			
			Scanner myObj7=new Scanner(System.in);
			System.out.print("Please enter the height(in inches): ");
			double temp7=myObj7.nextDouble();
			
			Scanner myObj8=new Scanner(System.in);
			System.out.print("Please enter the weight(in lbs): ");
			double temp8=myObj8.nextDouble();
			
			System.out.println("");
			tempP.setName(tempFour);
			tempP.setAge(temp5);
			tempP.setGender(temp6);
			tempP.setHeight(temp7);
			tempP.setWeight(temp8);
			guy.addPerson(tempP);
			}
			catch(InputMismatchException e) {
				System.out.println("The input you entered is incorrect; please try again");
			}
			
		}
		else if (tempTwo.equals("R"))
		{
			Scanner myObj11=new Scanner(System.in);
			System.out.print("Please enter the name of the person: ");
			String temp11=myObj11.nextLine();
			guy.removePerson(temp11);
		}
		else if (tempTwo.equals("G"))
		{
			Scanner myObj10=new Scanner(System.in);
			System.out.print("Please enter the name of the person: ");
			String temp10=myObj10.nextLine();
			guy.getPerson(temp10);
			
		}
		else if (tempTwo.equals("P"))
		{
			guy.printTable();
		}
		else if (tempTwo.equals("S"))
		{
			Scanner myObj11=new Scanner(System.in);
			System.out.print("Please enter a name for the file: ");
			String temp11=myObj11.nextLine();
			Person[] tempPeople=guy.getPeople();
			
			try
			{
				PrintWriter thing= new PrintWriter(new File(temp11));
				StringBuilder sb=new StringBuilder();
				
				sb.append("Name");                                  //sets the catagories
				sb.append(",     ");
				sb.append("Sex");
				sb.append(", ");
				sb.append("Age");
				sb.append(", ");
				sb.append("Height(in)");
				sb.append(", ");
				sb.append("Weight (lbs)");
				sb.append("\r\n");
				
				for(int i=0;i<tempPeople.length;i++)         //adds each element from the variable guy into the file, storing each of their features before moving onto the next row
				{
					sb.append("\""+tempPeople[i].getName()+"\"");
					sb.append(",     ");
					sb.append("\""+tempPeople[i].getGender()+"\"");
					sb.append(",   ");
					sb.append(String.valueOf(tempPeople[i].getAge()));
					sb.append(",       ");
					sb.append(String.valueOf(tempPeople[i].getHeight()));
					sb.append(",      ");
					sb.append(String.valueOf(tempPeople[i].getWeight()));
					sb.append("\r\n");
				}
				
				thing.write(sb.toString());
				thing.close();
				System.out.println("Saved!");
			}
			catch (Exception e){
				System.out.println("Error with loading the file");
			}
			
			
		}
		else if (tempTwo.equals("Q"))                         //ends the program
		{
			System.out.println("Sorry to see you go mate!");
			break;
		}
		else
		{
			System.out.println("error: The letter does not exist to a Command");    //they entered a letter that does not exist for a feature
		}
	}
	}
}
