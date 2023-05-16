public class Person {
	private String name;
	private int seatNumber;
	private Person nextPerson;
	
	public Person()
	{
		name="";
		seatNumber=Integer.MAX_VALUE;
		nextPerson=null;
	}
	
	public Person(String giveName, int initialSeat)
	{
		name=giveName;
		seatNumber=initialSeat;
		nextPerson=null;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getSeat()
	{
		return seatNumber;
	}

	public Person getLink()
	{
		return nextPerson;
	}
	
	public void setName(String nameGive)
	{
		name=nameGive;
	}
	
	public void setSeat(int seat)
	{
		seatNumber=seat;
	}
	
	public void setLink(Person next)
	{
		nextPerson=next;
	}
	
	
}

