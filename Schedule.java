import java.util.*;
import java.io.*;
public class Schedule {
	
	Team[][] National;
	Team[][] American;
	Team[][] NationalSchedule;
	Team[][] AmericanSchedule;
	ArrayList<String> NationalHome;
	ArrayList<String> NationalAway;
	ArrayList<String> AmericanHome;
	ArrayList<String> AmericanAway;
	
	int block;
	int series;
	int day;
	int gamenumber;
	
	public Schedule(MLB mlb)
	{
		National = new Team[30][16];
		American = new Team[30][16];
		createSets(mlb);
		NationalSchedule = generateSchedule(National);
		AmericanSchedule = generateSchedule(American);
		NationalHome = new ArrayList<String>();
		NationalAway = new ArrayList<String>();
		AmericanHome = new ArrayList<String>();
		AmericanAway = new ArrayList<String>();
		
		//out.println("\n\n\nDIVIDER\n\n\n");
		
		block = 0;
		series = 0;
		day = 1;
		gamenumber = 0;
		
	}
	
	public void createSets(MLB mlb)
	{
		Team[] temp1 = new Team[16];
		temp1[0] = null;
		Team[] temp2 = new Team[16];
		temp2[0] = null;
		
		ArrayList<Integer> nums1 = new ArrayList<Integer>();
		ArrayList<Integer> nums2 = new ArrayList<Integer>();
		
		for(int x=1; x<16; x++)
		{
			int index1 = (int)(Math.random()*15);
			while(nums1.contains(index1)&&nums1.size()<15)
			{
				index1 = (int)(Math.random()*15);
			}
			
			int index2 = (int)(Math.random()*15);
			while(nums2.contains(index2)&&nums2.size()<15)
			{
				index2 = (int)(Math.random()*15);
			}
			
			temp1[x] = mlb.league1.teamList.get(index1);
			nums1.add(index1);
			temp2[x] = mlb.league2.teamList.get(index2);
			nums2.add(index2);
			
		}
		//for checking purposes only
		/*for(int x=1; x<16; x++)
		{
			out.println(temp1[x].name+"--------"+temp2[x].name);
		}*/
		
		for(int x=0; x<30; x+=2)
		{
				National[x]=(temp1);
				American[x]=(temp2); 
			
				National[x+1]=(swapHomeAway(temp1));
				American[x+1]=(swapHomeAway(temp2));
			
			temp1 = rotate(temp1);
			temp2 = rotate(temp2);
			
		}
	}
	
	public Team[] swapHomeAway(Team[] arr)
	{
		Team[] swap = new Team[16];
		
		swap[0] = null;
		swap[15] = arr[15];
		int bot = 1;
		int top = arr.length-2;
		
		while(bot<top)
		{
			Team temp = arr[bot];
			swap[bot] = arr[top];
			swap[top] = temp;
			bot++;
			top--;
		}
		
		return swap;
	}
	
	public Team[] rotate(Team[] arr)
	{
		Team[] rotated = new Team[16];
		
		for(int x=2; x<16; x++)
		{
			rotated[x] = arr[x-1];
		}
		rotated[1] = arr[15];
		return rotated;
	}
	
	public Team[][] generateSchedule(Team[][] mat)
	{
		Team[][] temp = new Team[30][16];
		Set<Integer> used = new TreeSet<Integer>();
		int random = (int)(Math.random()*30);
		for(int x=0; x<30; x++)
		{
			while(used.contains(random))
			{
				random = (int)(Math.random()*30);
			}
			
			temp[x]=mat[random];
			used.add(random);
		}
		return temp;
		
	}
	
	public void playGame(int daysPlayed) throws IOException
	{	
		Team[][] teams = new Team[2][2];
		
		if(daysPlayed==0)
		{//implement filename
			teams = generateMatch();
			String filename = "day "+day+" "+teams[0][0].name+" vs. "+teams[0][1].name;
			PrintWriter output = new PrintWriter(new File("C:\\Users\\rzhon\\eclipse-workspace\\Baseball\\played_games\\regular\\"+filename+".txt"));
			Game single1 = new Game(output, teams[0][0], teams[0][1]);
			single1.run();
			NationalHome.add("@ "+teams[0][0].name+" ");
			NationalHome.add(""+single1.HRhome);
			NationalAway.add(teams[0][1].name+" ");
			NationalAway.add(""+single1.HRvisitor);
			output.close();
			
			filename = "day "+day+" "+teams[1][0].name+" vs. "+teams[1][1].name;
			output = new PrintWriter(new File("C:\\Users\\rzhon\\eclipse-workspace\\Baseball\\played_games\\"+filename+".txt"));
			Game single2 = new Game(output, teams[1][0], teams[1][1]);
			single2.run();
			AmericanHome.add("@ "+teams[1][0].name+" ");
			AmericanHome.add(""+single2.HRhome);
			AmericanAway.add(teams[1][1].name+" ");
			AmericanAway.add(""+single2.HRvisitor);
			output.close();
			check();
		}
		else
		{
			check();
			int n = gamenumber;
			for(int i = n; i<7; i++)
				playGame(0);
			for(int x=0; x<daysPlayed-1; x++)
			{
				for(int i = 0; i<7; i++)
					playGame(0);
			}
			check();
		}
		//for(int x = block; x<block+days; block++)
	}
	
	public Team[][] generateMatch()
	{
		check();
		Team [][] matchups = new Team[2][2];
		int bot = 1;
		int top = 14;
		
		while(bot<gamenumber+1)
		{
			bot++; 
			top--;
		}

		matchups[0][0] = NationalSchedule[block][bot];
		matchups[0][1] = NationalSchedule[block][top];
		matchups[1][0] = AmericanSchedule[block][bot];
		matchups[1][1] = AmericanSchedule[block][top];
		
		gamenumber++;
		return matchups;
	}
		
	public void printLeagueSchedule(String str)throws IOException
	{
		PrintWriter out;
		if(str.equals("1"))
		{
			out = new PrintWriter(new File("National Schedule.txt"));
			out.println("National League");
			printLeagueScheduleAlgorithm(NationalHome, NationalAway, 0, out);
			out.close();
		}
		else if(str.equals("2"))
		{
			out = new PrintWriter(new File("American Schedule.txt"));
			out.println("American League");
			printLeagueScheduleAlgorithm(AmericanHome, AmericanAway, 1, out);
			out.close();
		}
		
		else if(str.equals("3"))
		{
			out = new PrintWriter(new File("MLB Schedule.txt"));
			out.println("MLB");
			int p = 0;

			for(int x=0; x<NationalHome.size(); x+=2)
			{
				if(p%7==0)
				{
					if((p/7+1)%4 == 0)
					{
						p+=7;
					}
					out.println("\nDay "+(p/7+1));
				}
				
				String temp1 = NationalHome.get(x);
				String temp2 = NationalAway.get(x);
				int HomeScore = Integer.parseInt(NationalHome.get(x+1));
				int AwayScore = Integer.parseInt(NationalAway.get(x+1));
				if(HomeScore > AwayScore)
					out.println(temp1+HomeScore+" "+temp2+AwayScore+" (N)");
				else
					out.println(temp2+AwayScore+" "+temp1+HomeScore+" (N)");
				temp1 = AmericanHome.get(x);
				temp2 = AmericanAway.get(x);
				HomeScore = Integer.parseInt(AmericanHome.get(x+1));
				AwayScore = Integer.parseInt(AmericanAway.get(x+1));
				if(HomeScore > AwayScore)
					out.println(temp1+HomeScore+" "+temp2+AwayScore+" (A)");
				else
					out.println(temp2+AwayScore+" "+temp1+HomeScore+" (A)");
				
				p++;
				
			}
			if(day<119)
				printLeagueMatchups(2, out);
			out.close();
		}
		
	}
	
	public void printLeagueScheduleAlgorithm(ArrayList<String> home, ArrayList<String> away, int i, PrintWriter out)
	{
		int p = 0;

		for(int x=0; x<home.size(); x+=2)
		{
			if(p%7==0)
			{
				if((p/7+1)%4 == 0)
				{
					p+=7;
				}
				out.println("\nDay "+(p/7+1));
			}
			
			String temp1 = home.get(x);
			String temp2 = away.get(x);
			int HomeScore = Integer.parseInt(home.get(x+1));
			int AwayScore = Integer.parseInt(away.get(x+1));
			if(HomeScore > AwayScore)
			{
				out.println(temp1+HomeScore+" "+temp2+AwayScore);
			}
			else
			{
				out.println(temp2+AwayScore+" "+temp1+HomeScore);
			}
			p++;
			
		}
		if(day<119)
			printLeagueMatchups(i, out);
		
	}
	
	public void printLeagueMatchups(int i, PrintWriter out)
	{
		//out.println("bye"+"---VS---"+NationalSchedule[index][15].name);
		
		int b = block;
		int s = series;
		int d = day;
		int g = gamenumber;
		if(i==0)
		{
			printLeagueMatchupsAlgorithm(NationalSchedule, out);
		}
		
		else if(i==1)
		{
			printLeagueMatchupsAlgorithm(AmericanSchedule, out);
		}
		else
		{
			if(g>0)
			{
				int bot = 1+g;
				int top = 14-g;
				while(bot<top)
				{
					out.println(NationalSchedule[b][top].name+" @ "+NationalSchedule[b][bot].name+" (N)");
					out.println(AmericanSchedule[b][top].name+" @ "+AmericanSchedule[b][bot].name+" (A)");
					bot++;
					top--;
				}
				s++;
				if(s>2)
				{
					s=0; b++; d++;
				}
				d++;
			}
			
			while(b<30)
			{
				out.println("\nDay "+d);
				int bot = 1;
				int top = 14;
				while(bot<top)
				{
					out.println(NationalSchedule[b][top].name+" @ "+NationalSchedule[b][bot].name+" (N)");
					out.println(AmericanSchedule[b][top].name+" @ "+AmericanSchedule[b][bot].name+" (A)");
					bot++;
					top--;
				}
				s++;
				if(s>2)
				{
					s=0; b++; d++;
				}
				d++;	
			}
			
		}
		
		
		/*int bot = 1;
		int top = 14;
		while(bot<top)
		{
			out.println(NationalSchedule[index][bot].name+"---VS---"+NationalSchedule[index][top].name);
			bot++;
			top--;
		}*/
	}
	
	public void printLeagueMatchupsAlgorithm(Team[][] schedule, PrintWriter out)
	{
		int b = block;
		int s = series;
		int d = day;
		int g = gamenumber;
		
		if(g>0)
		{
			int bot = 1+g;
			int top = 14-g;
			while(bot<top)
			{
				out.println(schedule[b][top].name+" @ "+schedule[b][bot].name);
				bot++;
				top--;
			}
			s++;
			if(s>2)
			{
				s=0; b++; d++;
			}
			d++;
		}
		
		while(b<30)
		{
			out.println("\nDay "+d);
			int bot = 1;
			int top = 14;
			while(bot<top)
			{
				out.println(schedule[b][top].name+" @ "+schedule[b][bot].name);
				bot++;
				top--;
			}
			s++;
			if(s>2)
			{
				s=0; b++; d++;
			}
			d++;	
		}
	}
	
	public void printTeamSchedule(int i, MLB mlb) throws IOException
	{	
		String teamname = "";
		PrintWriter out;
		if(i>0&&i<=15)
		{
			Team team = mlb.league1.teamList.get(i-1);
			teamname = team.name;
			out = new PrintWriter(new File(teamname+" Schedule.txt"));
			out.println(teamname+" ("+team.wins+"-"+team.losses+")");
			printTeamScheduleAlgorithm(teamname, NationalSchedule, NationalHome, NationalAway, 0, out);
			out.close();
		
		}
			
		else if(i>15&&i<=30)
		{
			Team team = mlb.league2.teamList.get(i-16);
			teamname = team.name;
			out = new PrintWriter(new File(teamname+" Schedule.txt"));
			out.println(teamname+" ("+team.wins+"-"+team.losses+")");
			printTeamScheduleAlgorithm(teamname, AmericanSchedule, AmericanHome, AmericanAway, 1, out);
			out.close();
		}
		
	}
	
	public void printTeamScheduleAlgorithm(String teamname, Team[][] schedule, ArrayList<String> home, ArrayList<String> away, int i, PrintWriter out)
	{
		int b = block;
		int p = 0;

		for(int x=0; x<home.size(); x+=2)
		{
			if(p%7==0)
			{
				if((p/7+1)%4 == 0)
				{
					p+=7;
				}
				out.print("\nDay "+(p/7+1)+": "); 
				boolean bye = true;
				if(x<=home.size()-14)
				{
					for(int y=x; y<x+14; y+=2)
					{
						if(home.get(y).contains(teamname)||away.get(y).contains(teamname))
						{
							bye = false;
							break;
						}
					}
				}
				else
				{
					int bot = 1;
					int top = 14;
					while(bot<top)
					{
						if(schedule[b][top].name.contains(teamname)||schedule[b][bot].name.contains(teamname))
						{
							bye=false;
							break;
						}
						bot++;
						top--;
					}
					/*s++;
					if(s>2)
					{
						s=0; b++; d++;
					}
					d++;*/
				}
					
				if(bye)
					out.println("Bye");		
					
			}
				
				
			if(home.get(x).contains(teamname)||away.get(x).contains(teamname))
			{
				String temp1 = home.get(x);
				String temp2 = away.get(x);
				int HomeScore = Integer.parseInt(home.get(x+1));
				int AwayScore = Integer.parseInt(away.get(x+1));
				if(temp1.contains(teamname))
				{
					if(HomeScore > AwayScore)
						out.println("W "+temp2+AwayScore+" "+temp1+HomeScore);
					else
						out.println("L "+temp2+AwayScore+" "+temp1+HomeScore);
				}
				else
				{
					if(HomeScore > AwayScore)
						out.println("L "+temp2+AwayScore+" "+temp1+HomeScore);
					else
						out.println("W "+temp2+AwayScore+" "+temp1+HomeScore);
				}
				
			}
			
			p++;
				
		}
		if(day<119)
			printTeamMatchups(i, teamname, out);
	}
	
	public void printTeamMatchups(int i, String name, PrintWriter out)
	{
		if(i==0)
			printTeamMatchupsAlgorithm(NationalSchedule, name, out);
		
		else if(i==1)
			printTeamMatchupsAlgorithm(AmericanSchedule, name, out);
	}
	
	public void printTeamMatchupsAlgorithm(Team[][] schedule, String name, PrintWriter out)
	{
		int b = block;
		int s = series;
		int d = day;
		int g = gamenumber;
		
		if(g>0)
		{
			int bot = 1+g;
			int top = 14-g;
			while(bot<top)
			{
				if(schedule[b][top].name.contains(name)||schedule[b][bot].name.contains(name))
				{
					out.println(schedule[b][top].name+" @ "+schedule[b][bot].name);
					break;
				}
					
				bot++;
				top--;
			}
			s++;
			if(s>2)
			{
				s=0; b++; d++;
			}
			d++;
		}
		
		while(b<30)
		{
			out.print("\nDay "+d+": ");
			int bot = 1;
			int top = 14;
			boolean bye = true;
			while(bot<top)
			{
				if(schedule[b][top].name.contains(name)||schedule[b][bot].name.contains(name))
				{
					out.println(schedule[b][top].name+" @ "+schedule[b][bot].name);
					bye = false;
					break;
				}
					
				bot++;
				top--;
			}
			if(bye)
				out.println("Bye");
			s++;
			if(s>2)
			{
				s=0; b++; d++;
			}
			d++;	
		}
	}
	
	public void printTeamVTeam(int i, int x, int y, MLB mlb) throws IOException
	{
		PrintWriter out;
		if(i==0)
		{
			String team1 = mlb.league1.teamList.get(x-1).name;
			String team2 = mlb.league1.teamList.get(y-1).name;
			out = new PrintWriter(new File(team1+" vs. "+team2+".txt"));
			out.println(printTeamVTeamAlgorithm(i, team1, team2, NationalHome, NationalAway, out));
			out.close();
		}
		else if(i==1)
		{
			String team1 = mlb.league2.teamList.get(x-1).name;
			String team2 = mlb.league2.teamList.get(y-1).name;
			out = new PrintWriter(new File(team1+" vs. "+team2+".txt"));
			out.println(printTeamVTeamAlgorithm(i, team1, team2, AmericanHome, AmericanAway, out));
			out.close();
		}
		
	}
	
	public String printTeamVTeamAlgorithm(int i, String team1, String team2, ArrayList<String> home, ArrayList<String> away, PrintWriter out)
	{
		out.println(team1+" VS "+team2);
		String score = "";
		int team1wins = 0;
		int team2wins = 0;
		int p = 0;

		for(int x=0; x<home.size(); x+=2)
		{
			if(p%7==0)
			{
				if((p/7+1)%4 == 0)
				{
					p+=7;
				}
			}
			if((home.get(x).contains(team1)&&away.get(x).contains(team2))||(away.get(x).contains(team1)&&home.get(x).contains(team2)))
			{
				
				out.print("\nDay "+(p/7+1)+": ");
				
				String temp1 = home.get(x);
				String temp2 = away.get(x);
				int HomeScore = Integer.parseInt(home.get(x+1));
				int AwayScore = Integer.parseInt(away.get(x+1));
				if(HomeScore > AwayScore)
				{
					out.println(temp1+HomeScore+" "+temp2+AwayScore);
					if(home.get(x).contains(team1))
						team1wins++;
					else
						team2wins++;
				}
				else
				{
					out.println(temp2+AwayScore+" "+temp1+HomeScore);
					if(home.get(x).contains(team1))
						team2wins++;
					else
						team1wins++;
				}
			}
			
			p++;
			
		}
		if(team1wins>team2wins)
			score = "\n"+team1+" lead series "+team1wins+"-"+team2wins;
		else if(team2wins>team1wins)
			score = "\n"+team2+" lead series "+team2wins+"-"+team1wins;
		else
			score = "\nSeries tied "+team1wins+"-"+team2wins;
		
		if(day<119)
			printTeamVTeamMatchups(i, team1, team2, out);
		
		return score;
	}
	
	public void printTeamVTeamMatchups(int i, String team1, String team2, PrintWriter out)
	{
		if(i==0)
			printTeamVTeamMatchupsAlgorithm(NationalSchedule, team1, team2, out);
		else
			printTeamVTeamMatchupsAlgorithm(AmericanSchedule, team1, team2, out);
	}
	
	public void printTeamVTeamMatchupsAlgorithm(Team[][] schedule, String team1, String team2, PrintWriter out)
	{
		int b = block;
		int s = series;
		int d = day;
		int g = gamenumber;
		
		if(g>0)
		{
			int bot = 1+g;
			int top = 14-g;
			while(bot<top)
			{
				if((schedule[b][top].name.contains(team1)&&schedule[b][bot].name.contains(team2))||(schedule[b][top].name.contains(team2)&&schedule[b][bot].name.contains(team1)))
				{
					out.println(schedule[b][top].name+" @ "+schedule[b][bot].name);
					break;
				}
					
				bot++;
				top--;
			}
			s++;
			if(s>2)
			{
				s=0; b++; d++;
			}
			d++;
		}
		
		while(b<30)
		{
			int bot = 1;
			int top = 14;
			while(bot<top)
			{
				if((schedule[b][top].name.contains(team1)&&schedule[b][bot].name.contains(team2))||(schedule[b][top].name.contains(team2)&&schedule[b][bot].name.contains(team1)))
				{
					out.print("\nDay "+d+": ");
					out.println(schedule[b][top].name+" @ "+schedule[b][bot].name);
					break;
				}
					
				bot++;
				top--;
			}
			s++;
			if(s>2)
			{
				s=0; b++; d++;
			}
			d++;	
		}
	}
	
	
	public void check()
	{
		if(gamenumber > 6)
		{
			gamenumber = 0; series++; day++;
		}
		if(series > 2)
		{
			series = 0; block++; day++;
		}
		if(block > 29)
			block = 0;
	}

}
