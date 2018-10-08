import java.util.*;
import java.io.*;
public class AmericanTeam extends Team{
	
	
	public AmericanTeam(Scanner fs) throws IOException//modified constructor
	{
		super(fs);
		batterList.add(dh);
		nationalRules = false;
		isNationalLeague = false;
		setRules();
	}
	
	
	
	public boolean getisNationalLeague()
	{
		return isNationalLeague;
	}
	
}
