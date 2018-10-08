import java.util.*;
import java.io.*;
import java.text.*;

public class Leaderboard{
	
	ArrayList<Team> teams; 
	ArrayList<Batter> batters; 
	ArrayList<Pitcher> pitchers; 
	DecimalFormat ff;
	
	public Leaderboard(MLB mlb)
	{
		teams = new ArrayList<Team>();
		batters = new ArrayList<Batter>();
		pitchers = new ArrayList<Pitcher>();
		for(int x=0; x<15; x++)
		{
			teams.add(mlb.league1.teamList.get(x));
			for(Batter b: mlb.league1.teamList.get(x).batterList)
			{
				batters.add(b);
			}
			for(Pitcher p: mlb.league1.teamList.get(x).pitcherList)
			{
				pitchers.add(p);
			}
		}
		for(int x=0; x<15; x++)
		{
			teams.add(mlb.league2.teamList.get(x));
			for(Batter b: mlb.league2.teamList.get(x).batterList)
			{
				batters.add(b);
			}
			for(Pitcher p: mlb.league2.teamList.get(x).pitcherList)
			{
				pitchers.add(p);
			}
		}
		ff = new DecimalFormat("0.000");
		
	}
	
	public void printStandings(int start, int end) throws IOException
	{
		PrintWriter out;
		List<Team> temp = new ArrayList<Team>();
		for(int x=start; x<end; x++)
		{
			temp.add(teams.get(x));
		}
		//temp = teams.subList(start, end);
		Collections.sort(temp);
		
		if(end==15)
		{
			out = new PrintWriter(new File("National Standings.txt"));
			out.println("National League");
		}
		else if(start==15)
		{
			out = new PrintWriter(new File("American Standings.txt"));
			out.println("American League");	
		}
		else
		{
			out = new PrintWriter(new File("MLB Standings.txt"));
			out.println("MLB");	
		}
		out.println();
		out.println("Team\t\t\tW\tL\tGB\tPCT");
		out.println();
		for(int x=0; x<temp.size(); x++)//implement GB
		{
			if(x>0)
			{
				int gb = temp.get(0).wins-temp.get(x).wins;
				if(temp.get(x).name.length()<16)
				{
					out.println(temp.get(x).name+"\t\t"+temp.get(x).wins+"\t"+temp.get(x).losses+"\t"+gb+"\t"+
					ff.format((double)temp.get(x).wins/((double)temp.get(x).wins+(double)temp.get(x).losses)));
				}	
					
				else
				{
					out.println(temp.get(x).name+"\t"+temp.get(x).wins+"\t"+temp.get(x).losses+"\t"+gb+"\t"+
					ff.format((double)temp.get(x).wins/((double)temp.get(x).wins+(double)temp.get(x).losses)));
				}
					
			}
			else
			{
				if(temp.get(x).name.length()<16)
				{
					out.println(temp.get(x).name+"\t\t"+temp.get(x).wins+"\t"+temp.get(x).losses+"\t-\t"+
					ff.format((double)temp.get(x).wins/((double)temp.get(x).wins+(double)temp.get(x).losses)));
				}
					
				else
				{
					out.println(temp.get(x).name+"\t"+temp.get(x).wins+"\t"+temp.get(x).losses+"\t-\t"+
					ff.format((double)temp.get(x).wins/((double)temp.get(x).wins+(double)temp.get(x).losses)));
				}
			
			}
			
		}
		out.close();
	}
	
	public Team[][] getStandingsArray()
	{
		Team[][] temp = new Team[2][15];
		for(int x=0; x<15; x++)
		{
			temp[0][x] = teams.get(x);
			temp[1][x] = teams.get(x+15);
		}
		Arrays.sort(temp[0]);
		Arrays.sort(temp[1]);
		
		return temp;
	}
	
	public void printBatterLeagueLeaders(int start, int end, int statnum, PrintWriter out)
	{
		List<Batter> temp = new ArrayList<Batter>();
		
		for(int x=start; x<end; x++)
		{
			temp.add(batters.get(x));
		}
		
		/*else
		{
			temp = batters.subList(start, end);
		}*/
		if(statnum==10)
		{
			for(int x=temp.size()-1; x>-1; x--)
			{
				if(temp.get(x).getStats().permAB==0)
				{
					temp.remove(x);
				}
			}
		}
		sort(temp, statnum);
		
			if(statnum==1)
			{
				out.println("Player\t\t\tAB\tRank\tTeam\n");
				
				for(int x=0; x<10; x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permAB, -1, x+1, false, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permAB, temp.get(x-1).getStats().permAB, x+1, false, out);
					}
					
				}
			}
			else if(statnum==2)
			{
				out.println("Player\t\t\tH\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permH, -1, x+1, false, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permH, temp.get(x-1).getStats().permH, x+1, false, out);
					}
					
				}
				
			}
			else if(statnum==3)
			{
				out.println("Player\t\t\t2B\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permtwoB, -1, x+1, false, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permtwoB, temp.get(x-1).getStats().permtwoB, x+1, false, out);
					}
					
				}
			}
			else if(statnum==4)
			{
				out.println("Player\t\t\t3B\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permthreeB, -1, x+1, false, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permthreeB, temp.get(x-1).getStats().permthreeB, x+1, false, out);
					}
					
				}
			}
			else if(statnum==5)
			{
				out.println("Player\t\t\tHR\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permHR, -1, x+1, false, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permHR, temp.get(x-1).getStats().permHR, x+1, false, out);
					}
				
				}
			}
			else if(statnum==6)
			{
				out.println("Player\t\t\tR\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permR, -1, x+1, false, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permR, temp.get(x-1).getStats().permR, x+1, false, out);
					}
					
				}
			}
			else if(statnum==7)
			{
				out.println("Player\t\t\tRBI\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permRBI, -1, x+1, false, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permRBI, temp.get(x-1).getStats().permRBI, x+1, false, out);
					}
					
				}
			}
			else if(statnum==8)
			{
				out.println("Player\t\t\tBB\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permBB, -1, x+1, false, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permBB, temp.get(x-1).getStats().permBB, x+1, false, out);
					}
					
				}
			}
			else if(statnum==9)
			{
				out.println("Player\t\t\tK\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permK, -1, x+1, false, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permK, temp.get(x-1).getStats().permK, x+1, false, out);
					}
					
				}
			}
			else if(statnum==10)
			{
				out.println("Player\t\t\tAVG\tRank\tTeam\n");
				for(int x=0; x<temp.size(); x++)
				{
					Batter b = temp.get(x);
					int pos = getTeamIndex(b);
					String team = teams.get(pos/9).name;
					if(x>9)
						break;
					if(x==0)
					{
						printBatter(team, b, b.getStats().permH/b.getStats().permAB, -1, x+1, true, out);
					}
					else
					{
						printBatter(team, b, b.getStats().permH/b.getStats().permAB, temp.get(x-1).getStats().permH/temp.get(x-1).getStats().permAB, x+1, true, out);
					}
					
				}
			}
		
	}
	public void printPitcherLeagueLeaders(int start, int end, int statnum, PrintWriter out)
	{
		List<Pitcher> temp = new ArrayList<>();
		for(int x=start; x<end; x++)
		{
			temp.add(pitchers.get(x));
		}
		
		/*else
		{
			temp = pitchers.subList(start, end);
		}*/
		
		if(statnum==7)
		{
			for(int x=temp.size()-1; x>-1; x--)
			{
				if(temp.get(x).getStats().permIP==0)
				{
					temp.remove(x);
				}
			}
		}
		psort(temp, statnum);
			
			if(statnum==1)
			{
				out.println("Player\t\t\tIP\tRank\tTeam\n");	
				for(int x=0; x<10; x++)
				{
					Pitcher b = temp.get(x);
					int pos = pgetTeamIndex(b);
					String team = teams.get(pos/5).name;
					if(x==0)
					{
						printPitcher(team, b, b.getStats().permIP, -1, x+1, false, out);
					}
					else
					{
						printPitcher(team, b, b.getStats().permIP, temp.get(x-1).getStats().permIP, x+1, false, out);
					}
					
				}
			}
			else if(statnum==2)
			{
				out.println("Player\t\t\tpH\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Pitcher b = temp.get(x);
					int pos = pgetTeamIndex(b);
					String team = teams.get(pos/5).name;
					if(x==0)
					{
						printPitcher(team, b, b.getStats().permpH, -1, x+1, false, out);
					}
					else
					{
						printPitcher(team, b, b.getStats().permpH, temp.get(x-1).getStats().permpH, x+1, false, out);
					}
					
				}
				
			}
			else if(statnum==3)
			{
				out.println("Player\t\t\tp2B\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Pitcher b = temp.get(x);
					int pos = pgetTeamIndex(b);
					String team = teams.get(pos/5).name;
					if(x==0)
					{
						printPitcher(team, b, b.getStats().permptwoB, -1, x+1, false, out);
					}
					else
					{
						printPitcher(team, b, b.getStats().permptwoB, temp.get(x-1).getStats().permptwoB, x+1, false, out);
					}
					
				}
			}
			else if(statnum==4)
			{
				out.println("Player\t\t\tp3B\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Pitcher b = temp.get(x);
					int pos = pgetTeamIndex(b);
					String team = teams.get(pos/5).name;
					if(x==0)
					{
						printPitcher(team, b, b.getStats().permpthreeB, -1, x+1, false, out);
					}
					else
					{
						printPitcher(team, b, b.getStats().permpthreeB, temp.get(x-1).getStats().permpthreeB, x+1, false, out);
					}
					
				}
			}
			else if(statnum==5)
			{
				out.println("Player\t\t\tpHR\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Pitcher b = temp.get(x);
					int pos = pgetTeamIndex(b);
					String team = teams.get(pos/5).name;
					if(x==0)
					{
						printPitcher(team, b, b.getStats().permpHR, -1, x+1, false, out);
					}
					else
					{
						printPitcher(team, b, b.getStats().permpHR, temp.get(x-1).getStats().permpHR, x+1, false, out);
					}
					
				}
			}
			else if(statnum==6)
			{
				out.println("Player\t\t\tpR\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Pitcher b = temp.get(x);
					int pos = pgetTeamIndex(b);
					String team = teams.get(pos/5).name;
					if(x==0)
					{
						printPitcher(team, b, b.getStats().permpR, -1, x+1, false, out);
					}
					else
					{
						printPitcher(team, b, b.getStats().permpR, temp.get(x-1).getStats().permpR, x+1, false, out);
					}
					
				}
			}
			else if(statnum==7)
			{
				out.println("Player\t\t\tERA\tRank\tTeam\n");
				for(int x=0; x<temp.size(); x++)
				{
					Pitcher b = temp.get(x);
					int pos = pgetTeamIndex(b);
					String team = teams.get(pos/5).name;
					if(x>9)
						break;
					
					if(x==0)
					{
						printPitcher(team, b, 9*b.getStats().permpR/b.getStats().permIP, -1, x+1, true, out);
					}
					else
					{
						printPitcher(team, b, 9*b.getStats().permpR/b.getStats().permIP, 9*temp.get(x-1).getStats().permpR/temp.get(x-1).getStats().permIP, x+1, true, out);
					}
					
				}
			}
			else if(statnum==8)
			{
				out.println("Player\t\t\tpBB\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Pitcher b = temp.get(x);
					int pos = pgetTeamIndex(b);
					String team = teams.get(pos/5).name;
					if(x==0)
					{
						printPitcher(team, b, b.getStats().permpBB, -1, x+1, false, out);
					}
					else
					{
						printPitcher(team, b, b.getStats().permpBB, temp.get(x-1).getStats().permpBB, x+1, false, out);
					}
					
				}
			}
			else if(statnum==9)
			{
				out.println("Player\t\t\tpK\tRank\tTeam\n");
				for(int x=0; x<10; x++)
				{
					Pitcher b = temp.get(x);
					int pos = pgetTeamIndex(b);
					String team = teams.get(pos/5).name;
					if(x==0)
					{
						printPitcher(team, b, b.getStats().permpK, -1, x+1, false, out);
					}
					else
					{
						printPitcher(team, b, b.getStats().permpK, temp.get(x-1).getStats().permpK, x+1, false, out);
					}
					
				}
			}
		
	}
	public void printBatter(String team, Batter b, double stat, double otherstat, int rank, boolean isDouble, PrintWriter out)
	{
		if(isDouble)
		{
			if(stat==otherstat)
			{
				if(b.name.length()<16)
					out.println(b.name+"\t\t"+ff.format(stat)+"\t-\t"+team);
				else
					out.println(b.name+"\t"+ff.format(stat)+"\t-\t"+team);
			}
			else
			{
				if(b.name.length()<16)
					out.println(b.name+"\t\t"+ff.format(stat)+"\t"+rank+"\t"+team);
				else
					out.println(b.name+"\t"+ff.format(stat)+"\t"+rank+"\t"+team);
			}
		}
		else
		{
			if(stat==otherstat)
			{
				if(b.name.length()<16)
					out.println(b.name+"\t\t"+(int)stat+"\t-\t"+team);
				else
					out.println(b.name+"\t"+(int)stat+"\t-\t"+team);
			}
			else
			{
				if(b.name.length()<16)
					out.println(b.name+"\t\t"+(int)stat+"\t"+rank+"\t"+team);
				else
					out.println(b.name+"\t"+(int)stat+"\t"+rank+"\t"+team);
			}
		}
		
	}
	
	public void printPitcher(String team, Pitcher p, double stat, double otherstat, int rank, boolean isDouble, PrintWriter out)
	{
		if(isDouble)
		{
			if(stat==otherstat)
			{
				if(p.name.length()<16)
					out.println(p.name+"\t\t"+ff.format(stat)+"\t-\t"+team);
				else
					out.println(p.name+"\t"+ff.format(stat)+"\t-\t"+team);
			}
			else
			{
				if(p.name.length()<16)
					out.println(p.name+"\t\t"+ff.format(stat)+"\t"+rank+"\t"+team);
				else
					out.println(p.name+"\t"+ff.format(stat)+"\t"+rank+"\t"+team);
			}
		}
		else
		{
			if(stat==otherstat)
			{
				if(p.name.length()<16)
					out.println(p.name+"\t\t"+(int)stat+"\t-\t"+team);
				else
					out.println(p.name+"\t"+(int)stat+"\t-\t"+team);
			}
			else
			{
				if(p.name.length()<16)
					out.println(p.name+"\t\t"+(int)stat+"\t"+rank+"\t"+team);
				else
					out.println(p.name+"\t"+(int)stat+"\t"+rank+"\t"+team);
			}
		}
	}
	
	public int pgetTeamIndex(Pitcher p)
	{
		int pos = 0;
		for(int y=0; y<pitchers.size(); y++)
		{
			if(p.equals(pitchers.get(y)))
			{
				pos = y;
				break;
			}
		}
		return pos;
	}
	public int getTeamIndex(Batter b)
	{
		int pos = 0;
		for(int y=0; y<batters.size(); y++)
		{
			if(b.equals(batters.get(y)))
			{
				pos = y;
				break;
			}
		}
		return pos;
	}
	
	
	List<Batter>helper = new ArrayList<Batter>();
	List<Pitcher>phelper = new ArrayList<Pitcher>();
	public void sort(List<Batter> list, int statnum) {
		
	    //list = batterList.toArray(new Batter[batterList.size()]);
	    mergeSort(list, 0, list.size() - 1, statnum);
	}
	public void psort(List<Pitcher> list, int statnum) {
		
	    //list = batterList.toArray(new Batter[batterList.size()]);
	    pmergeSort(list, 0, list.size() - 1, statnum);
	}
	private void mergeSort(List<Batter>list, int low, int high, int statnum) {
	    if(low < high) {
	        int middle = low + (high - low) / 2;
	        mergeSort(list, low, middle, statnum);
	        mergeSort(list, middle + 1, high, statnum); 
	        merge(list, low, middle, high, statnum); 
	    }
	}
	private void pmergeSort(List<Pitcher>list, int low, int high, int statnum) {
	    if(low < high) {
	        int middle = low + (high - low) / 2;
	        pmergeSort(list, low, middle, statnum);
	        pmergeSort(list, middle + 1, high, statnum); 
	        pmerge(list, low, middle, high, statnum); 
	    }
	}

	private void merge(List<Batter>list, int low, int middle, int high, int statnum) {
	    for(int i=low; i<= high; i++) {
	        helper.add(i, list.get(i));
	    }

	    int helperLeft = low;
	    int helperRight = middle + 1;
	    int current = low;

	    while(helperLeft <= middle && helperRight <= high) {
	        if(helper.get(helperLeft).compareTo(helper.get(helperRight), statnum) < 1) {
	            list.set(current, helper.get(helperLeft));
	            helperLeft++;
	        } else {
	            list.set(current, helper.get(helperRight));
	            helperRight++;
	        }
	        current++;
	    }

	    int remaining = middle - helperLeft;
        for(int j=0; j <= remaining; j++) 
            list.set(current+j, helper.get(helperLeft+j));

	}
	
	private void pmerge(List<Pitcher>list, int low, int middle, int high, int statnum) {
	    for(int i=low; i<= high; i++) {
	        phelper.add(i, list.get(i));
	    }

	    int helperLeft = low;
	    int helperRight = middle + 1;
	    int current = low;

	    while(helperLeft <= middle && helperRight <= high) {
	        if(phelper.get(helperLeft).compareTo(phelper.get(helperRight), statnum) < 1) {
	            list.set(current, phelper.get(helperLeft));
	            helperLeft++;
	        } else {
	            list.set(current, phelper.get(helperRight));
	            helperRight++;
	        }
	        current++;
	    }

	    int remaining = middle - helperLeft;
        for(int j=0; j <= remaining; j++) 
            list.set(current+j, phelper.get(helperLeft+j));

	}

}
