import java.lang.Math;

import InvalidLineCountException.InvalidLineCountException;
public class SecurityCheck extends Exception{
	private Line headLine;
	private Line tailLine;
	private Line cursorLine;

	public SecurityCheck()
	{
		headLine=new Line();
		tailLine=headLine;
		cursorLine=headLine;
	}
	
	
	public Line getHead()
	{
		return headLine;
	}
	
	public void addPerson(String Name, int seatNumber) throws TakenSeatException
	{
				
		Person tempP=new Person(Name,seatNumber);
		
		Line nodeptr=headLine;
		Person nodeptrTwo=nodeptr.getHead();
		while(nodeptr!=null)                           //checks if seat wasn't taken
		{
			while(nodeptrTwo!=null)
			{
				if(nodeptrTwo.getSeat()==tempP.getSeat())
				{
					throw new TakenSeatException();
				}
					nodeptrTwo=nodeptrTwo.getLink();
			}
			nodeptr=nodeptr.getLineLink();
		}
		
			headLine.addPerson(tempP);
			reorganizeLine();
			System.out.println(tempP.getName()+" successfully added to line");
		
	}
	
	public Person removeNextAttendee() throws AllLinesEmptyException
	{
		int tempCount=0;
		int count=0;
		Line nodeptrTwo=headLine;
		while(nodeptrTwo!=null)           //counts if all lines are empty
		{
			if (nodeptrTwo.getLength()==0)
			{
				tempCount++;
			}
			count++;
			nodeptrTwo=nodeptrTwo.getLineLink();
		}
		
		if (tempCount==count)
		{
			throw new AllLinesEmptyException(); 
		}
		
		if (headLine.getLineLink()!=null)           //removes attendee from a line that isn't the first line
		{
			Line nodeptr=headLine.getLineLink();
			while(nodeptr!=null)
			{
				if (nodeptr.getLength()!=0)
				{
					Person tempPerson2=nodeptr.removeFrontPerson();
					reorganizeLine();
					return tempPerson2;
				}
				nodeptr=nodeptr.getLineLink();
			}
			return headLine.removeFrontPerson();
		}
		else
		{
			return headLine.removeFrontPerson();    //removes attendee from head of the list given that there is only one line
		}
	}
	
	
	public void addNewLines(int newLines) throws InvalidLineCountException
	{
		if (newLines<=0)
		{
			throw new InvalidLineCountException();   
		}
		
		while (newLines>0)           //adds lines to the tail of the list till all lines are added
		{
				Line newNode=new Line();
				tailLine.setLineLink(newNode);
				tailLine=newNode;
				newLines--;
		}		
		reorganizeLine();
	}

	
	public void removeLines(int[] removedLines) throws SingleLineRemovalException, LineDoesNotExistException
	{
			int i=0;
			cursorLine=headLine;
			Line prevptr=null;
			int count=1;
			
			int count2=0;
			Line nodeptrFour=headLine;                    //Lines 112 to 118 are meant to count amount of items in Line list
			while(nodeptrFour!=null)
			{
				count2++;
				nodeptrFour=nodeptrFour.getLineLink();
			}
			
			int targetCount=0;                                 
			for(int b=count2;b>0;b--)
			{
				for (int j=0;j<removedLines.length;j++)
				{
					if(removedLines[j]==b)
					{
						targetCount++;                //determines if there was an instance of a line being able to be removed
					}
				}
			}
			
			if(targetCount!=removedLines.length)        //determines if there is a line that was inputted that can not have existed to be removed by checking if the instances match the amount of lines wanted to be removed
			{
				throw new LineDoesNotExistException();       
			}
			
			
			while((cursorLine!=null) && (i<removedLines.length))
			{
				
				if(count==removedLines[i])
				{
					if(removedLines[i]==1)
					{
						if (cursorLine.getLineLink()==null)
						{
							throw new SingleLineRemovalException();   
						}
						
						else           //removing nodes when asked to remove head
						{
							Person nodeptr=cursorLine.getHead();
							while(nodeptr!=null)
							{
								cursorLine.getLineLink().addPerson(nodeptr);
								nodeptr=nodeptr.getLink();
							}
							cursorLine=cursorLine.getLineLink();
							headLine=cursorLine;
							if (cursorLine.getLineLink()==null)   //if there were only two nodes
							{
								tailLine=cursorLine;
							}
							count++;
							i++;
						}
					}
					
					else if(cursorLine.getLineLink()==null)    //removes nodes from when asked to remove from tail Line
					{
						while(cursorLine.getLength()>0)
						{
							Person temp=cursorLine.removeFrontPerson();
							prevptr.addPerson(temp);
						}
						prevptr.setLineLink(null);
						cursorLine=prevptr;
						tailLine=prevptr;
						if(prevptr==headLine)
						{
							headLine=cursorLine;
						}
						count++;
						i++;
					}
					
					else            //for any removal of any lines inbetween two lines
					{
						while(cursorLine.getLength()>0)
						{
							Person temp=cursorLine.removeFrontPerson();
							cursorLine.getLineLink().addPerson(temp);
						}
						prevptr.setLineLink(cursorLine.getLineLink());
						cursorLine=prevptr.getLineLink();
						count++;
						i++;
					}
				}
				
				else                  //moves on if it didn't detect that the cursor line was on a line that wanted to be removed
				{
					prevptr=cursorLine;
					cursorLine=cursorLine.getLineLink();
					count++;
				}
				reorganizeLine();   //reorganizes the lines
		
		}

	
		
	}
	
	public void reorganizeLine()              //way to organize the lines so that it is evenly balanced
	{
		Line nodeptr=headLine;
		while (nodeptr!=null)
		{
			Line nodeptrTwo=headLine;
			while(nodeptrTwo!=null)
			{
				if (Math.abs( nodeptr.getLength()-nodeptrTwo.getLength() )<=1)
				{

				}
				else
				{
					while(Math.abs( nodeptr.getLength()-nodeptrTwo.getLength() )>1)   //creates a loop till lines are balanced next to each other
					{
						if (nodeptr.getLength()>nodeptrTwo.getLength())     //checks which ones (the node or the node next to it) needs some people to balance
						{
							Person tempGuy=nodeptr.removeFrontPerson();
							nodeptrTwo.addPerson(tempGuy);
						
						}
						else if (nodeptr.getLength()<nodeptrTwo.getLength())
						{
							Person tempGuyTwo=nodeptrTwo.removeFrontPerson();
							nodeptr.addPerson(tempGuyTwo);
						}
					}
				
				}
				nodeptrTwo=nodeptrTwo.getLineLink();
			}
			nodeptr=nodeptr.getLineLink();
		}
			
	}
	
	
	public int listLength()
	{
		Line nodePtr=headLine;
		int answer=0;
		while (nodePtr!=null)
		{
			answer++;
			nodePtr=nodePtr.getLineLink();
		}
		return answer;
	}
	
	

	
	public void printTable()
	{
		System.out.println("Line         Name          Seat Number"); 
		Line nodePtr=headLine;
		int count=1;
		while(nodePtr!=null)
		{
			for(int i=0;i<nodePtr.getLength();i++)    //count is used to keep track of which line they are on. Only when every person is counted in the line with the for loop does count go up
			{
				System.out.println(count+"          "+nodePtr.getCursorPerson().getName()+"          "
				+nodePtr.getCursorPerson().getSeat());
				nodePtr.advanceCursor();
			}
			nodePtr.resetCursor();
			nodePtr=nodePtr.getLineLink();
			count++;
		}
		
		
	}
}





