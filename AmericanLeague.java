import java.util.*;
import java.io.*;
public class AmericanLeague {
	String name;
	ArrayList<AmericanTeam> teamList;
	
	public AmericanLeague(Scanner fs) throws IOException
	{
		if(fs.hasNextLine())
		{
			name = fs.nextLine();
		}
		teamList = new ArrayList<AmericanTeam>();
		
		for(int x=0; x<15; x++)
		{
			teamList.add(new AmericanTeam(fs));
		}
		

	}

}