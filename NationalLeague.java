import java.util.*;
import java.io.*;
public class NationalLeague {
	String name;
	ArrayList<NationalTeam> teamList;
	
	public NationalLeague(Scanner fs) throws IOException
	{
		if(fs.hasNextLine())
		{
			name = fs.nextLine();
		}
		teamList = new ArrayList<NationalTeam>();
		
		for(int x=0; x<15; x++)
		{
			teamList.add(new NationalTeam(fs));
		}
		
		
	}

}
