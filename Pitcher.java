import java.util.*;
import java.io.*;
public class Pitcher extends Batter {
	
	int pStrikeout;
	int pWalk;
	int pSingle;
	int pDouble;
	int pTriple;
	int pHomerun;
	int pGroundout;
	int pFlyout;
	
	PitcherStats stats;
	
	//ArrayList<PitcherStats> critical;
	
	boolean active;
	
	public Pitcher(Scanner fs) throws IOException
	{
		super(fs);
		pStrikeout = Integer.parseInt(temp[10]);
		pWalk = Integer.parseInt(temp[11]);
		pSingle = Integer.parseInt(temp[12]);
		pDouble = Integer.parseInt(temp[13]);
		pTriple = Integer.parseInt(temp[14]);
		pHomerun = Integer.parseInt(temp[15]);
		pGroundout = Integer.parseInt(temp[16]);
		pFlyout = Integer.parseInt(temp[17]);
		
		stats = new PitcherStats();
		
		active = false;
		
		//critical = new ArrayList<PitcherStats>();
		
		//critical.add(stats.IP)
		
	}
	
	public PitcherStats getStats()
	{
		return this.stats;
	}
	
	public int compareTo(Pitcher p, int x)
	{
		switch(x)
		{
			default: 
				return 0;
			case 1:
			{
				if(getStats().permIP>p.getStats().permIP)
					return -1;
	
				else if(getStats().permIP<p.getStats().permIP)
					return 1;
				else
					return 0;
			}
			case 2:
			{
				if(getStats().permpH>p.getStats().permpH)
					return -1;
	
				else if(getStats().permpH<p.getStats().permpH)
					return 1;
				else
					return 0;
			}
			case 3:
			{
				if(getStats().permptwoB>p.getStats().permptwoB)
					return -1;
	
				else if(getStats().permptwoB<p.getStats().permptwoB)
					return 1;
				else
					return 0;
			}
			case 4:
			{
				if(getStats().permpthreeB>p.getStats().permpthreeB)
					return -1;
	
				else if(getStats().permpthreeB<p.getStats().permpthreeB)
					return 1;
				else
					return 0;
			}
			case 5:
			{
				if(getStats().permpHR>p.getStats().permpHR)
					return -1;
	
				else if(getStats().permpHR<p.getStats().permpHR)
					return 1;
				else
					return 0;
			}
			case 6:
			{
				if(getStats().permpR>p.getStats().permpR)
					return -1;
	
				else if(getStats().permpR<p.getStats().permpR)
					return 1;
				else
					return 0;
			}
			case 7:
			{
				if((getStats().permpR/getStats().permIP)>(p.getStats().permpR/p.getStats().permIP))
					return -1;
	
				else if((getStats().permpR/getStats().permIP)<(p.getStats().permpR/p.getStats().permIP))
					return 1;
				else
					return 0;
			}
			case 8:
			{
				if(getStats().permpBB>p.getStats().permpBB)
					return -1;
	
				else if(getStats().permpBB<p.getStats().permpBB)
					return 1;
				else
					return 0;
			}
			case 9:
			{
				if(getStats().permpK>p.getStats().permpK)
					return -1;
	
				else if(getStats().permpK<p.getStats().permpK)
					return 1;
				else
					return 0;
			}
			
		}
		
	}
	
	public void resetPitcher()
	{
		active = false;
	}

}
