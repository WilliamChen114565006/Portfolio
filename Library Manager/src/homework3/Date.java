package homework3;

public class Date {
	private int day;
	private int month;
	private int year;
	
	public Date()
	{
		day=0;
		month=0;
		year=0;
	}
	
	public Date(int tempDay, int tempMonth, int tempYear)
	{
		day=tempDay;
		month=tempMonth;
		year=tempYear;
	}
	
	public void setDay(int x)
	{
		day=x;
	}
	
	public void setMonth(int y)
	{
		month=y;
	}
	
	public void setYear(int z)
	{
		year=z;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public static int compare(Date x, Date y)
	{
		
		if (x.getYear()==y.getYear())  //checks if year is equal to then see if the months are equal to then see if the days are equal
		{
			if(x.getMonth()==y.getMonth())
			{
				if (x.getDay()==y.getDay())
				{
					return 0;
				}
				
				else if(x.getDay()<y.getDay())
				{
					return -1;
				}
				
				else
				{
					return 1;
				}
				
			}
			
			else if(x.getMonth()<y.getMonth())
			{
				return -1;
			}
			else
			{
				return 1;
			}
				
		}
		else if(x.getYear()  <  y.getYear())
		{
			return -1;
		}
		else
		{
			return 1;
		}

	}
	
	public void changeToDate(String x)
	{
		String tempMonth=x.substring(0, x.indexOf("/"));    //finds the first instance of the / mark so it can create a substring with the month, this is repeated for also the day and year
		month=Integer.parseInt(tempMonth);  //converts the substring to the month
		
		String tempDay=x.substring(x.indexOf("/")+1 ,     x.indexOf("/", x.indexOf("/")+1)    );
		day=Integer.parseInt(tempDay);
		
		String tempYear=x.substring(x.indexOf("/", x.indexOf("/")+2)+1);
		year=Integer.parseInt(tempYear);
	}
	
	public String toString()
	{
		String answer=month+"/"+day+"/"+year;
		return answer;
	}
	
}
