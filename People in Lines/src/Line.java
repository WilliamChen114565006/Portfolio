
public class Line {
	private Person headPerson;
	private Person tailPerson;
	private Person cursorPerson;
	private int length;
	private Line lineLink;
	
	public Line()
	{
		length=0;
		headPerson=null;
		tailPerson=null;
		lineLink=null;
		cursorPerson=headPerson;
	}
	
	public Person getHead()
	{
		return headPerson;
	}
	
	public Person getCursorPerson()
	{
		return cursorPerson;
	}
	
	public void advanceCursor()
	{
		if(cursorPerson!=tailPerson)
		{
			cursorPerson=cursorPerson.getLink();
		}
	}
	
	public void resetCursor()
	{
		cursorPerson=headPerson;
	}
	
	public String getNameTwo()
	{
		String tempP=headPerson.getName();
		return tempP;
		
	}
	
	public int getLength()
	{
		return length;
	}
	
	public Line getLineLink()
	{
		return lineLink;
	}
	
	public void setLineLink(Line x)
	{
		lineLink=x;
	}
	
	public void addPerson(Person attendee)
	{
		if (headPerson==null)
		{
			headPerson=attendee;
			cursorPerson=attendee;
			tailPerson=attendee;
		}
		else if (headPerson.getLink()==null)           //if there is only one node, it compares the attendee and the node
		{
			if(attendee.getSeat()>headPerson.getSeat())
			{
				headPerson.setLink(attendee);
				cursorPerson=headPerson;
				tailPerson=attendee;
			}
			else if (attendee.getSeat()<headPerson.getSeat())
			{
				attendee.setLink(headPerson);
				tailPerson=headPerson;
				headPerson=attendee;
				cursorPerson=attendee;
			}
		}
		else if (tailPerson.getSeat()<attendee.getSeat())  //checks if attendee has a greater seat number than the end(which should be the highest already)
		{
			tailPerson.setLink(attendee);
			tailPerson=attendee;
		}
		else if (attendee.getSeat()<headPerson.getSeat())  //checks if attendee has lowest seat number
		{
			attendee.setLink(headPerson);
			headPerson=attendee;
			cursorPerson=attendee;
		}
		else
		{
			Person nodeptr=headPerson;
			Person prevptr=null;
			while (nodeptr!=null)
			{
				if(attendee.getSeat()<nodeptr.getSeat())         //puts attendee in an ordered way of lowest to highest seats given that they aren't the lowest or highest seat
				{
					attendee.setLink(nodeptr);
					prevptr.setLink(attendee);	
					break;
				}
				prevptr=nodeptr;
				nodeptr=nodeptr.getLink();	
			}
			
		}
		
		length++;
	}
	
	public Person removeFrontPerson()
	{
		if(headPerson!=null)
		{
			Person tempP=headPerson;
			headPerson=headPerson.getLink();
			cursorPerson=headPerson;
			length--;
			return tempP;
		}
		else
		{
			return headPerson;
		}
	}
	
	
	
}




