import java.io.File;
import java.util.Scanner ;

public class PersonDataManager extends Exception implements Cloneable{
	private static Person[] people;

	public PersonDataManager()
	{
		people=new Person[0];
	}
	
	
	public static void buildFromFile(String location)
	{
		int count2=0;
		int count=0;
		String []bruh=new String[0];
		
		File file=new File(location);      //reads file and stores number of objects in it
		Scanner s=null;
		try {
			System.out.println("..Loading..");
			s=new Scanner(file);
		
			while (s.hasNextLine())
				{
				String line= s.nextLine();
				count2++;
				bruh=new String[count2];
				}
			}
		catch(Exception ex) {
			System.out.println("Person data has not been loaded successfully");
		}
		
		File file2=new File(location);
		Scanner s2=null;
		try {
			s2=new Scanner(file);
		
			while (s2.hasNextLine())
				{
				String line= s2.nextLine();
				bruh[count]=line;
				count++;
				}
			}
		catch(Exception ex) {
			System.out.println("did not apply it to the array");
		}
		
		try {
		for (int u=1;u<bruh.length-1;u++)    //collects each of the data (i.e. name, gender..) by collecting the substring between each commas. Then adds it to the array people
		{
			
			
					Person tempK=new Person();
					int firstComma=bruh[u].indexOf(",");           //finds first comma and so forth to then find values between values and assign them to the correct variable to be stored
					int secondComma=bruh[u].indexOf(",", firstComma+1);
					int thirdComma=bruh[u].indexOf(",", secondComma+1);
					int fourthComma=bruh[u].indexOf(",",thirdComma+1);
					String word=((bruh[u].substring(0,firstComma)).replace("\"", "")).replaceAll("\\s", "");
					char firstLetter2=word.charAt(0);
					if(!(Character.isUpperCase(firstLetter2)))
					{
						throw new IllegalArgumentException("The first letter in a name needs to be capitalized");
					}
					for(int i=0;i<word.length();i++)
					{
						char ch=word.charAt(i);
						if((Character.isDigit(ch))  )     //checks if there is a number in the name
						{
							throw new IllegalArgumentException("Names cannot have numbers");
						}
					}
					for(int i=1;i<word.length();i++)    //tranverses and checks if there an uppercase besides the first letter in the name
					{
						char ch2=word.charAt(i);
						if( (Character.isUpperCase(ch2)) )
						{
							throw new IllegalArgumentException("Names cannot have uppercase");
						}
					}
					
					tempK.setName(((bruh[u].substring(0,firstComma)).replace("\"", "")).replaceAll("\\s", ""));	
				
						
					if (  !((((bruh[u].substring(firstComma+1,secondComma)).replace("\"", "")).replaceAll("\\s", "" )).equals("M"))
							&& !((((bruh[u].substring(firstComma+1,secondComma)).replace("\"", "")).replaceAll("\\s", "" )).equals("F"))  )                //checks if the gender is possible
					{
						throw new IllegalArgumentException("The gender must be inputted as M(male) or F(female).");
					}
					tempK.setGender(((bruh[u].substring(firstComma+1,secondComma)).replace("\"", "")).replaceAll("\\s", "" ));
					
					tempK.setAge(Integer.valueOf((bruh[u].substring(secondComma+1,thirdComma)).replaceAll("\\s", "")));
					tempK.setHeight(Double.valueOf((bruh[u].substring(thirdComma+1,fourthComma)).replaceAll("\\s", "")));
					tempK.setWeight(Double.valueOf((bruh[u].substring(fourthComma+1,bruh[u].length()-1)).replaceAll("\\s", "")));

					

					Person biggerArray[]=new Person[people.length+1];              //stores it into a bigger array, with each person added increasing the size of the array to be stored
					System.arraycopy(people,0,biggerArray,0,people.length);
					people=biggerArray;
					people[people.length-1]=tempK;				                  //sets each last value to the next person	
			}
		
		System.out.println("Data loaded successfully.");
		}
		catch(Exception e){
			System.out.println("Data has not been loaded");
		}
	}
	
	
	public void addPerson(Person newPerson)
	{
		int breaker=0;
		for (int w=0;w<people.length;w++)  //determines if person exists with breaker. If breaker>0, it means that person exists
		{
			if ((newPerson.getName().equals(people[w].getName()) && (newPerson.getGender().equals(people[w].getGender())) &&
					(newPerson.getAge()==people[w].getAge()) && (newPerson.getHeight()==people[w].getHeight()) && (newPerson.getWeight()==people[w].getWeight())     ))
			{
				breaker++;
			}
		}
		if (breaker>0)
		{
			System.out.println("You can't enter the same person twice");
		}
		else
		{
			Person biggerArray[]=new Person[people.length+1];     //creates bigger array to add item (every time)
			System.arraycopy(people,0,biggerArray,0,people.length);
			people=biggerArray;
			people[people.length-1]=newPerson;
			for(int i=0;i<people.length;i++)                      //sorts alphabetically
			{
				for (int j=1;j<people.length;j++)
				{
					if((people[i].getName().compareTo(people[j].getName())>0)       )
					{
						Person tempK=people[i];
						Person tempI=people[j];
						people[j]=tempK;					
						people[i]=tempI;
					}
					
				}
			}
						
			
			System.out.println((people[people.length-1].getName())+" has been added.");
		}
	}

	
	public void getPerson(String name)
	{
		int index=0;
		while(index<people.length && !(people[index].getName().equals(name)))
		{
			index++;
		}
		if (index==people.length)      //went through the whole array and didn't find the person
		{
			System.out.println("The person does not exist");
		}
		else
		{
			System.out.println(people[index].toString());
		}

	}
	
	public void removePerson(String name)
	{
		int index=0;
		Person [] trimArray;
		while ((index<people.length)&&!(people[index].getName().equals(name)))
		{
			index++;
		}
		if (index==people.length)
		{
			System.out.println("The person is not in the list");
		}
		else
		{
		
			for (int i=index+1; i<people.length;i++)      //shifts the order back to remove the said item then trims array to correct size
			{
				people[i-1]=people[i];
			}
			trimArray=new Person[people.length-1];
			System.arraycopy(people,0,trimArray,0,trimArray.length);
			people=trimArray;
			System.out.println(name+" has been removed");
		}
	}
	
	public void printTable()
	{
		System.out.println("Name    Age  Gender Height   Weight");
		for(int s=0;s<people.length;s++)
		{
			System.out.println(people[s].getName()+"     "+people[s].getAge()+"    "+people[s].getGender()+
					"    "+people[s].getHeight()+"    "+people[s].getWeight());
		}
	}
	
	public Person[] getPeople()
	{
		return people;
	}

}
