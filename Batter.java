import java.util.*;
import java.io.*;
public class Batter{
	
	String name;
	String[] temp;
	int Strikeout;
	int Walk;
	int Single;
	int Double;
	int Triple;
	int Homerun;
	int Groundout;
	int Flyout;
	boolean onBase;
	
	
	Stats stats;
	Category statC;
	
	int Currentbase;
	public Batter(Scanner fs) throws IOException
	{

		onBase = false;
		if(fs.hasNextLine())
		{
			temp = fs.nextLine().split(" ");
		}
		name = temp[0]+" "+temp[1];
		Strikeout = Integer.parseInt(temp[2]);
		Walk = Integer.parseInt(temp[3]);
		Single = Integer.parseInt(temp[4]);
		Double = Integer.parseInt(temp[5]);
		Triple = Integer.parseInt(temp[6]);
		Homerun = Integer.parseInt(temp[7]);
		Groundout = Integer.parseInt(temp[8]);
		Flyout = Integer.parseInt(temp[9]);
		
		stats = new Stats();
		
		Currentbase = 0;
		
	}
	
	public void check()
	{
	
		if(Currentbase != 0)
		{
			onBase = true;
		}
		else
		{
			onBase = false;
		}
	}
	
	public Stats getStats() {
		return stats;
	}
	
	public int compareTo(Batter b, int x)
	{
		switch(x)
		{
			default: 
				return 0;
			case 1:
			{
				if(getStats().permAB>b.getStats().permAB)
					return -1;
	
				else if(getStats().permAB<b.getStats().permAB)
					return 1;
				else
					return 0;
			}
			case 2:
			{
				if(getStats().permH>b.getStats().permH)
					return -1;
	
				else if(getStats().permH<b.getStats().permH)
					return 1;
				else
					return 0;
			}
			case 3:
			{
				if(getStats().permtwoB>b.getStats().permtwoB)
					return -1;
	
				else if(getStats().permtwoB<b.getStats().permtwoB)
					return 1;
				else
					return 0;
			}
			case 4:
			{
				if(getStats().permthreeB>b.getStats().permthreeB)
					return -1;
	
				else if(getStats().permthreeB<b.getStats().permthreeB)
					return 1;
				else
					return 0;
			}
			case 5:
			{
				if(getStats().permHR>b.getStats().permHR)
					return -1;
	
				else if(getStats().permHR<b.getStats().permHR)
					return 1;
				else
					return 0;
			}
			case 6:
			{
				if(getStats().permR>b.getStats().permR)
					return -1;
	
				else if(getStats().permR<b.getStats().permR)
					return 1;
				else
					return 0;
			}
			case 7:
			{
				if(getStats().permRBI>b.getStats().permRBI)
					return -1;
	
				else if(getStats().permRBI<b.getStats().permRBI)
					return 1;
				else
					return 0;
			}
			case 8:
			{
				if(getStats().permBB>b.getStats().permBB)
					return -1;
	
				else if(getStats().permBB<b.getStats().permBB)
					return 1;
				else
					return 0;
			}
			case 9:
			{
				if(getStats().permK>b.getStats().permK)
					return -1;
	
				else if(getStats().permK<b.getStats().permK)
					return 1;
				else
					return 0;
			}
			case 10:
			{
				if((getStats().permH/getStats().permAB)>(b.getStats().permH/b.getStats().permAB))
					return -1;
	
				else if((getStats().permH/getStats().permAB)<(b.getStats().permH/b.getStats().permAB))
					return 1;
				else
					return 0;
			}
			
		}
		
	}

}
