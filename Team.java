import java.util.*;
import java.io.*;

public class Team implements Comparable<Team>{
	
	String name;
	ArrayList<Batter> batterList;
	ArrayList<Pitcher> pitcherList;
	int wins;
	int losses;
	int runs;
	boolean nationalRules;
	boolean isNationalLeague;
	Batter dh;//added dh to parent class
	
	public Team(Scanner fs) throws IOException
	{
		if(fs.hasNextLine())
		{
			name = fs.nextLine();
		}
		batterList = new ArrayList<Batter>();
		pitcherList = new ArrayList<Pitcher>();
		for(int x=0; x<8; x++)
		{
			batterList.add(new Batter(fs));
		}
		dh = new Batter(fs);
		for(int x=0; x<5; x++)
		{
			pitcherList.add(new Pitcher(fs));
		}
		setPosition();
	}
	
	public void setPosition()
	{
		batterList.get(0).stats.POS = "C ";
		batterList.get(1).stats.POS = "1B";
		batterList.get(2).stats.POS = "2B";
		batterList.get(3).stats.POS = "3B";
		batterList.get(4).stats.POS = "SS";
		batterList.get(5).stats.POS = "RF";
		batterList.get(6).stats.POS = "CF";
		batterList.get(7).stats.POS = "LF";
		dh.stats.POS = "DH";
		pitcherList.get(0).stats.POS = "SP";
	}
	
	public void switchRules()//establishes ruleset team is going to play with
	{
		if(nationalRules == true)
		{
			nationalRules = false;
		}
			
		else
		{
			nationalRules = true;
		}
		
		setRules();
			
	}
	
	public void setRules()//allows team to dynamically change team composition for NLT, ALT, roundrobin
	{
		if(nationalRules==true)
		{
			batterList.set(8, pitcherList.get(0));
			batterList.get(8).stats.POS = "SP";
		}
		else
		{
			batterList.set(8, dh);
			batterList.get(8).stats.POS = "DH";
		}
		
	}
	
	public void updateRuns(int score)
	{
		runs += score;
	}
	
	public void rotatePitcher()//revision required if ever implemented
	{
		Collections.rotate(pitcherList, 1);
		//batterList.set(8, pitcherList.get(0));
	}
	
	public void reset()
	{
		for(Batter b: batterList)
		{
			b.Currentbase = 0;
			b.check();
		}
	}
	
	public void resetStats()
	{
		for(Batter b : batterList)
		{
			b.stats.reset();
		}
		for(Pitcher p : pitcherList)
		{
			p.stats.reset();
		}
	}
	
	public boolean getnationalRules()
	{
		return nationalRules;
	}
	
	public boolean getisNationalLeague()
	{
		return isNationalLeague;
	}
	
	public int compareTo(Team t)
	{
		if(wins>t.wins)
			return -1;
		else if(t.wins>wins)
			return 1;
		else
		{
			if(runs>t.runs)
				return -1;
			else if(t.runs>runs)
				return 1;
			else 
				return 0;
		}
	}
	

}
