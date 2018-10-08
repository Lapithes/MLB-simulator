import java.util.*;
import java.io.*;
public class NationalTeam extends Team {

	
	public NationalTeam(Scanner fs) throws IOException//modified constructor
	{
		super(fs);
		batterList.add(pitcherList.get(0));
		nationalRules = true;
		isNationalLeague = true;
		setRules();
	}
	
	
	
	public boolean getisNationalLeague()
	{
		return isNationalLeague;
	}
}
