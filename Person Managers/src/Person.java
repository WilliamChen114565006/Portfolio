public class Person extends Exception{
	private int age;
	private double height;
	private double weight;
	private String name;
	private String gender;
	
	public Person()
	{
		age=0;
		height=0;
		weight=0;
		name="";
		gender="";
	}
	
	public Person(int x, int y, int j, String k, String g)
	{
		age=x;
		height=y;
		weight=j;
		name=k;
		gender=g;
	}
	
	public int getAge()
	{
		return this.age;
	}
	
	public double getHeight()
	{
		return this.height;
	}
	
	public double getWeight()
	{
		return this.weight;
	}
	
	
	public String getName()
	{
		return this.name;
	}
	
	public String getGender()
	{
		return this.gender;
	}
	
	public void setAge(int ageIn)
	{
		this.age=ageIn;
	}
	
	
	public void setHeight(double heightIn)
	{
		this.height=heightIn;
	}
	
	public void setWeight(double weightIn)
	{
		this.weight=weightIn;
	}
	
	public void setName(String nameIn)
	{
		this.name=nameIn;
	}
	
	public void setGender(String genderIn)
	{
		this.gender=genderIn;
	}
	

	
	public String toString()
	{
		if (this.gender.equals("M"))
		{
			return (this.name+" is a "+this.age+" year old "+this.gender+"ale who is "+this.height+" inches tall and weighs "+this.weight+" pounds.");
		}
		return (this.name+" is a "+this.age+" year old "+this.gender+"emale who is "+this.height+" inches tall and weighs "+this.weight+" pounds.");
	}
	
	
}
