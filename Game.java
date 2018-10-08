import java.util.*;
import java.io.*;
import java.text.*;
import java.lang.Math;

public class Game {//fix switchrules
	
	Team home;
	Team visitor;
	int HRhome; 
	int HRvisitor;
	String score = HRhome+"-"+HRvisitor;
	ArrayList<Pitcher> activePitchers;
	PrintWriter out;
	String type;
	
	
	public Game(PrintWriter output, Team h, Team v)
	{
		out = output;
		home = h;
		visitor = v; 
		HRhome = 0;
		HRvisitor = 0;
		type = "q";
		//activePitchers = new ArrayList<Pitcher>();
	}
	public Game(PrintWriter output, Team h, Team v, String t) //there are now 2 constructors, one for regular games, another for postseason games.
	{
		out = output;
		home = h;
		visitor = v; 
		HRhome = 0;
		HRvisitor = 0;
		type = t;
		//activePitchers = new ArrayList<Pitcher>();
		
		if(type.equals("i"))// this should be for round robin tournaments
		{
			if((home.nationalRules != visitor.nationalRules))
			{
				visitor.switchRules();
			}
		}
		else if(type.equals("n"))// this should be for national league tournament
		{
			if(home.nationalRules==false)
				home.switchRules();
			if(visitor.nationalRules==false)
				visitor.switchRules();
		}
		else if(type.equals("a"))// for american league tourney
		{
			if(home.nationalRules== true)
				home.switchRules();
			if(visitor.nationalRules==true)
				visitor.switchRules();
		}
	}
	
	
	public void run()
	{
		home.resetStats();
		visitor.resetStats();
		
		out.println(home.name+"("+home.wins+"-"+home.losses+")"+" vs. "+visitor.name+"("+visitor.wins+"-"+visitor.losses+")"+"\n");
		 
		int homePos = 0;
		int visitorPos = 0;
		int x = 1;
		
		while(x<10 || HRhome==HRvisitor)
		{
			
			int outs = 0;
			String inning="";
			
			home.pitcherList.get(0).stats.IP=x;
			visitor.pitcherList.get(0).stats.IP=x;
			
			while(outs<3)
			{
				inning = "T"+x;
				
				if(visitorPos>=9)
				{
					visitorPos = 0;
				}
				
				String outcome = generateOutcome(visitor.batterList.get(visitorPos), home.pitcherList.get(0));
				
				if(outcome.equals("Strikeout"))
				{
					outs++;
					
					visitor.batterList.get(visitorPos).stats.AB++;
					visitor.batterList.get(visitorPos).stats.K++;
					home.pitcherList.get(0).stats.pK++;
					//home.pitcherList.get(0).stats.wins++;
					
					String str = score+" "+inning+" "+visitor.batterList.get(visitorPos).name+" "+"STRIKEOUT "+outs+" outs ";
					
					for(Batter b : visitor.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					
					out.println(str);
				}
				
				else if(outcome.equals("Groundout")||outcome.equals("GroundoutA"))
				{
					outs++;
					
					if(outcome.equals("GroundoutA"))
					{
						advance(visitor, 1);
						
					}
					
					visitor.batterList.get(visitorPos).stats.AB++;
					//home.pitcherList.get(0).stats.wins++;
					String str = inning+" "+visitor.batterList.get(visitorPos).name+" "+"GROUNDOUT "+outs+" outs ";
					
					for(Batter b : visitor.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					for(Batter b : visitor.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRvisitor++;
							b.stats.R++;
							visitor.batterList.get(visitorPos).stats.RBI++;
							home.pitcherList.get(0).stats.pR++;
						}
					}
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Flyout")||outcome.equals("FlyoutA"))
				{
					outs++;
					
					if(outcome.equals("FlyoutA"))
					{
						advance(visitor, 1);
						
					}
					visitor.batterList.get(visitorPos).stats.AB++;
					//home.pitcherList.get(0).stats.wins++;
					String str = inning+" "+visitor.batterList.get(visitorPos).name+" "+"FLYOUT "+outs+" outs ";
					
					for(Batter b : visitor.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					for(Batter b : visitor.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRvisitor++;
							b.stats.R++;
							visitor.batterList.get(visitorPos).stats.RBI++;
							home.pitcherList.get(0).stats.pR++;
						}
					}
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Walk"))
				{
					
					for(Batter b : visitor.batterList)
					{
				
						if(b.Currentbase==1)
						{
							
							for(Batter b1 : visitor.batterList)
							{
								
								if(b1.Currentbase==2)
								{
									
									for(Batter b2 : visitor.batterList)
									{
										if(b2.Currentbase==3)
										{
											b2.Currentbase++;
											break;
										}
									}
									b1.Currentbase++;
									break;
								}
							}
							b.Currentbase++;
							break;
						}
					}
					
					
					visitor.batterList.get(visitorPos).Currentbase+=1;
					visitor.batterList.get(visitorPos).onBase = true;
					
					visitor.batterList.get(visitorPos).stats.BB++;
					home.pitcherList.get(0).stats.pBB++;
					//home.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+visitor.batterList.get(visitorPos).name+" "+"WALK "+outs+" outs ";
					
					for(Batter b : visitor.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : visitor.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRvisitor++;
							b.stats.R++;
							count++;
							home.pitcherList.get(0).stats.pR++;
						}
					}
					visitor.batterList.get(visitorPos).stats.RBI+=count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Single"))
				{
					
					advance(visitor, 1);
					
					visitor.batterList.get(visitorPos).Currentbase+=1;
					visitor.batterList.get(visitorPos).onBase = true;
					
					visitor.batterList.get(visitorPos).stats.AB++;
					visitor.batterList.get(visitorPos).stats.H++;
					home.pitcherList.get(0).stats.pH++;
					//home.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+visitor.batterList.get(visitorPos).name+" "+"SINGLE "+outs+" outs ";
					
					for(Batter b : visitor.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : visitor.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRvisitor++;
							b.stats.R++;
							count++;
							home.pitcherList.get(0).stats.pR++;
						}
					}
					visitor.batterList.get(visitorPos).stats.RBI+=count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Double"))
				{
					
					advance(visitor, 2);
					
					visitor.batterList.get(visitorPos).Currentbase+=2;
					visitor.batterList.get(visitorPos).onBase = true;
					
					visitor.batterList.get(visitorPos).stats.AB++;
					visitor.batterList.get(visitorPos).stats.twoB++;
					visitor.batterList.get(visitorPos).stats.H++;
					home.pitcherList.get(0).stats.ptwoB++;
					home.pitcherList.get(0).stats.pH++;
					//home.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+visitor.batterList.get(visitorPos).name+" "+"DOUBLE "+outs+" outs ";
					
					for(Batter b : visitor.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : visitor.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRvisitor++;
							b.stats.R++;
							count++;
							home.pitcherList.get(0).stats.pR++;
						}
					}
					visitor.batterList.get(visitorPos).stats.RBI+=count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Triple"))
				{
					
					advance(visitor, 3);
					
					visitor.batterList.get(visitorPos).Currentbase+=3;
					visitor.batterList.get(visitorPos).onBase = true;
					
					visitor.batterList.get(visitorPos).stats.AB++;
					visitor.batterList.get(visitorPos).stats.threeB++;
					visitor.batterList.get(visitorPos).stats.H++;
					home.pitcherList.get(0).stats.pthreeB++;
					home.pitcherList.get(0).stats.pH++;
					//home.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+visitor.batterList.get(visitorPos).name+" "+"TRIPLE "+outs+" outs ";
					
					for(Batter b : visitor.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : visitor.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRvisitor++;
							b.stats.R++;
							count++;
							home.pitcherList.get(0).stats.pR++;
						}
					}
					visitor.batterList.get(visitorPos).stats.RBI+=count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else 
				{
					
					advance(visitor, 4);
					
					visitor.batterList.get(visitorPos).Currentbase+=4;
					
					
					visitor.batterList.get(visitorPos).stats.AB++;
					visitor.batterList.get(visitorPos).stats.HR++;
					visitor.batterList.get(visitorPos).stats.H++;
					home.pitcherList.get(0).stats.pHR++;
					home.pitcherList.get(0).stats.pH++;
					//home.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+visitor.batterList.get(visitorPos).name+" "+"HOMERUN "+outs+" outs ";
					
					for(Batter b : visitor.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : visitor.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRvisitor++;
							b.stats.R++;
							count++;
							home.pitcherList.get(0).stats.pR++;
							
						}
					}
					visitor.batterList.get(visitorPos).stats.RBI+=count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
					
				}
				visitorPos++;
			}
			
			out.println("END OF "+inning+" "+score);
			outs = 0;
			if(x==9 && HRhome>HRvisitor)
			{
				break;
			}
			
			while(outs<3)
			{
				inning = "B"+x;
				
				if(homePos>=9)
				{
					homePos = 0;
				}
				
				String outcome = generateOutcome(home.batterList.get(homePos), visitor.pitcherList.get(0));
				
				if(outcome.equals("Strikeout"))
				{
					outs++;
					
					home.batterList.get(homePos).stats.AB++;
					home.batterList.get(homePos).stats.K++;
					visitor.pitcherList.get(0).stats.pK++;
					//visitor.pitcherList.get(0).stats.wins++;
					
					String str = score+" "+inning+" "+home.batterList.get(homePos).name+" "+"STRIKEOUT "+outs+" outs ";
					
					for(Batter b : home.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					
					out.println(str);
				}
				
				else if(outcome.equals("Groundout")||outcome.equals("GroundoutA"))
				{
					outs++;
					
					if(outcome.equals("GroundoutA"))
					{
						advance(home, 1);
				
					}
					
					home.batterList.get(homePos).stats.AB++;
					//visitor.pitcherList.get(0).stats.wins++;
					
					String str = inning+" "+home.batterList.get(homePos).name+" "+"GROUNDOUT "+outs+" outs ";
					
					for(Batter b : home.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					for(Batter b : home.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRhome++;
							b.stats.R++;
							home.batterList.get(homePos).stats.RBI++;
							visitor.pitcherList.get(0).stats.pR++;
						}
					}
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Flyout")||outcome.equals("FlyoutA"))
				{
					outs++;
					//sacrifice flies?
					
					if(outcome.equals("FlyoutA"))
					{
						advance(home, 1);
					}
					
					home.batterList.get(homePos).stats.AB++;
					//visitor.pitcherList.get(0).stats.wins++;
					
					String str = inning+" "+home.batterList.get(homePos).name+" "+"FLYOUT "+outs+" outs ";
					
					for(Batter b : home.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					for(Batter b : home.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRhome++;
							b.stats.R++;
							home.batterList.get(homePos).stats.RBI++;
							visitor.pitcherList.get(0).stats.pR++;
						}
					}
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Walk"))
				{
					for(Batter b : home.batterList)
					{
				
						if(b.Currentbase==1)
						{
							
							for(Batter b1 : home.batterList)
							{
								
								if(b1.Currentbase==2)
								{
									
									for(Batter b2 : home.batterList)
									{
										if(b2.Currentbase==3)
										{
											b2.Currentbase++;
											break;
										}
									}
									b1.Currentbase++;
									break;
								}
							}
							b.Currentbase++;
							break;
						}
					}
							
					
					home.batterList.get(homePos).Currentbase+=1;
					home.batterList.get(homePos).onBase = true;
					
					home.batterList.get(homePos).stats.BB++;
					visitor.pitcherList.get(0).stats.pBB++;
					//visitor.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+home.batterList.get(homePos).name+" "+"WALK "+outs+" outs ";
					
					for(Batter b : home.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : home.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRhome++;
							b.stats.R++;
							count++;
							visitor.pitcherList.get(0).stats.pR++;
						}
					}
					home.batterList.get(homePos).stats.RBI+=count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Single"))
				{
					
					advance(home, 1);
					
					home.batterList.get(homePos).Currentbase+=1;
					home.batterList.get(homePos).onBase = true;
					
					home.batterList.get(homePos).stats.AB++;
					home.batterList.get(homePos).stats.H++;
					visitor.pitcherList.get(0).stats.pH++;
					//visitor.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+home.batterList.get(homePos).name+" "+"SINGLE "+outs+" outs ";
					
					for(Batter b : home.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : home.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRhome++;
							b.stats.R++;
							count++;
							visitor.pitcherList.get(0).stats.pR++;
						}
					}
					home.batterList.get(homePos).stats.RBI+=count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Double"))
				{
					
					advance(home, 2);
					
					home.batterList.get(homePos).Currentbase+=2;
					home.batterList.get(homePos).onBase = true;
					
					home.batterList.get(homePos).stats.AB++;
					home.batterList.get(homePos).stats.twoB++;
					home.batterList.get(homePos).stats.H++;
					visitor.pitcherList.get(0).stats.ptwoB++;
					visitor.pitcherList.get(0).stats.pH++;
					//visitor.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+home.batterList.get(homePos).name+" "+"DOUBLE "+outs+" outs ";
					
					for(Batter b : home.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : home.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRhome++;
							b.stats.R++;
							count++;
							visitor.pitcherList.get(0).stats.pR++;
						}
					}
					home.batterList.get(homePos).stats.RBI+=count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else if(outcome.equals("Triple"))
				{
					
					advance(home, 3);
					
					home.batterList.get(homePos).Currentbase+=3;
					home.batterList.get(homePos).onBase = true;
					
					home.batterList.get(homePos).stats.AB++;
					home.batterList.get(homePos).stats.threeB++;
					home.batterList.get(homePos).stats.H++;
					visitor.pitcherList.get(0).stats.pthreeB++;
					visitor.pitcherList.get(0).stats.pH++;
					//visitor.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+home.batterList.get(homePos).name+" "+"TRIPLE "+outs+" outs ";
					
					for(Batter b : home.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : home.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRhome++;
							b.stats.R++;
							
							visitor.pitcherList.get(0).stats.pR++;
						}
					}
					home.batterList.get(homePos).stats.RBI+=count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
				}
				
				else 
				{
					
					advance(home, 4);
					
					home.batterList.get(homePos).Currentbase+=4;
					
					
					home.batterList.get(homePos).stats.AB++;
					home.batterList.get(homePos).stats.HR++;
					home.batterList.get(homePos).stats.H++;
					visitor.pitcherList.get(0).stats.pHR++;
					visitor.pitcherList.get(0).stats.pH++;
					//visitor.pitcherList.get(0).stats.losses++;
				
					String str = inning+" "+home.batterList.get(homePos).name+" "+"HOMERUN "+outs+" outs ";
					
					for(Batter b : home.batterList)
					{
						if(b.Currentbase!=0 && b.Currentbase<4)
						{
							str += b.name+"@"+b.Currentbase+"B ";
						}
					}
					int count = 0;
					for(Batter b : home.batterList)
					{
						if (b.Currentbase>=4)
						{
							b.Currentbase = 0;
							b.check();
							str += b.name+" "+"scores ";
							HRhome++;
							b.stats.R++;
							
							visitor.pitcherList.get(0).stats.pR++;
						}
					}
					home.batterList.get(homePos).stats.RBI += count;
					score = HRhome+"-"+HRvisitor;
					String strF = score+" "+str;
					out.println(strF);
					
					
				}
				homePos++;
			}
			
			out.println("END OF "+inning+" "+score);
			
			home.reset();
			visitor.reset();
			/*for(Batter b : home.batterList)
			{
				b.Currentbase=0;
				b.check();
			}
			for(Batter b : visitor.batterList)
			{
				b.Currentbase=0;
				b.check();
			}*/
			
			x++;
			
		}
		out.println();
		out.println("END OF GAME "+score);
		
		if(type.equals("q"))
		{
			home.updateRuns(HRhome);
			visitor.updateRuns(HRvisitor);
		}
		
		if(HRhome>HRvisitor)
		{
			if(type.equals("q"))
			{
				home.wins++;
				visitor.losses++;
				home.pitcherList.get(0).stats.wins++;
				visitor.pitcherList.get(0).stats.losses++;
			}
			
			out.println(home.name+"("+home.wins+"-"+home.losses+")"+" win");
			out.println(visitor.name+"("+visitor.wins+"-"+visitor.losses+")"+" lose");
			
		}
		else if(HRhome<HRvisitor)
		{
			if(type.equals("q"))
			{
				visitor.wins++;
				home.losses++;
				home.pitcherList.get(0).stats.losses++;
				visitor.pitcherList.get(0).stats.wins++;
			}
			
			out.println(visitor.name+"("+visitor.wins+"-"+visitor.losses+")"+" win");
			out.println(home.name+"("+home.wins+"-"+home.losses+")"+" lose");
		}
		out.println();
		if(type.equals("q"))
		{
			for(Batter b: home.batterList)
				b.stats.update();
			for(Pitcher p: home.pitcherList)
				p.stats.updateP();
			for(Batter b: visitor.batterList)
				b.stats.update();
			for(Pitcher p: visitor.pitcherList)
				p.stats.updateP();
		}
		
		printOutput();
		
		if(home.getisNationalLeague() != home.getnationalRules())//resets to default
		{
			home.switchRules();
		}
		if(visitor.getisNationalLeague() != visitor.getnationalRules())
		{
			visitor.switchRules();
		}
		
		if(type.equals("q"))
			System.out.println("Game Complete--"+home.name+"("+home.wins+"-"+home.losses+")"+" vs. "+visitor.name+"("+visitor.wins+"-"+visitor.losses+")");
		else
			System.out.println("Game Complete--"+home.name+" vs. "+visitor.name);
		
	}
	
	public void advance(Team batting, int by)
	{
		for(Batter b: batting.batterList)
		{
			if(b.onBase)
			{
				b.Currentbase+=by;
			}
		}
	}
	
	public String generateOutcome(Batter b, Pitcher p)
	{
		ArrayList<String> outcomes = new ArrayList<String>();
		
		for(int x = 0; x<3; x++)
		{
			outcomes.add("Strikeout");
		}
		for(int x = 0; x<5; x++)
		{
			outcomes.add("Groundout");
		}
			outcomes.add("GroundoutA");
		
		for(int x = 0; x<4; x++)
		{
			outcomes.add("Flyout");
		}
			outcomes.add("FlyoutA");
			outcomes.add("FlyoutA");
		
		
		if(b.Walk>p.Walk+1)
		{
			for(int x=0; x<(b.Walk-p.Walk)/2; x++)
			{
				outcomes.add("Walk");
			}
		}
		else
		{
			outcomes.add("Walk");
		}
		
		if(b.Single>p.Single)
		{
			for(int x=0; x<b.Single-p.Single; x++)
			{
				outcomes.add("Single");
			}
		}
		else
		{
			outcomes.add("Single");
		}
		
		if(b.Double>p.Double)
		{
			for(int x=0; x<b.Double-p.Double; x++)
			{
				outcomes.add("Double");
			}
		}
		else
		{
			outcomes.add("Double");
		}
		
		if(b.Triple>p.Triple+1)
		{
			for(int x=0; x<(b.Triple-p.Triple)/2; x++)
			{
				outcomes.add("Triple");
			}
		}
		else
		{
			outcomes.add("Triple");
		}
		
		if(b.Homerun>p.Homerun+1)
		{
			for(int x=0; x<(b.Homerun-p.Homerun)/2; x++)
			{
				outcomes.add("Homerun");
			}
		}
		else
		{
			outcomes.add("Homerun");
		}
		
		if(p.Groundout>b.Groundout)
		{
			for(int x=0; x<p.Groundout-b.Groundout; x++)
			{
				outcomes.add("Groundout");
			}
		}
		else
		{
			outcomes.add("Groundout");
		}
		
		if(p.Flyout>b.Flyout)
		{
			for(int x=0; x<p.Flyout-b.Flyout; x++)
			{
				outcomes.add("Flyout");
			}
		}
		else
		{
			outcomes.add("Flyout");
		}
		
		if(p.Strikeout>b.Strikeout)
		{
			for(int x=0; x<p.Strikeout-b.Strikeout; x++)
			{
				outcomes.add("Strikeout");
			}
		}
		else
		{
			outcomes.add("Strikeout");
		}
		
		Random rand = new Random();
		int index = rand.nextInt(outcomes.size());
		
		//out.println(index);
		
		return outcomes.get(index);
		
		/*for(int x=0; x<5; x++)
		{
			if(p)
		}*/
	}
	
	public void printOutput()
	{
		NumberFormat formatter = new DecimalFormat("#0.000");
		
		
		
		out.println("\n\nPOST-GAME STATS\n");
		
		out.println(visitor.name+" Stats");
		
		out.println("Batter         POS  AB   H   2B  3B  HR  R      RBI     BB   K    AVG");
		
		for(Batter b: visitor.batterList)
		{
			if(b.name.length()<13)
			{
				String newName = b.name;
				for(int x=0; x<13-b.name.length(); x++)
				{
					newName+=" ";
				}
				out.print(newName+"  ");
			}
			else
			{
				out.print(b.name.substring(0, 13)+"  ");
			}
			
			
			out.println(b.stats.POS+"   "+(int)b.stats.AB+"    "+(int)b.stats.H+"   "+b.stats.twoB+"   "+b.stats.threeB+"   "+b.stats.HR+"   "+b.stats.R+"   "+"   "+b.stats.RBI+"       "+b.stats.BB+"    "+b.stats.K+"    "+formatter.format(b.stats.H/b.stats.AB));
		}
		
		out.println("Pitcher             IP   H   2B  3B  HR  R      ERA     BB   K    W-L");
			if(visitor.pitcherList.get(0).name.length()<13)
			{
				String newName = visitor.pitcherList.get(0).name;
				for(int x=0; x<13-visitor.pitcherList.get(0).name.length(); x++)
				{
					newName+=" ";
				}
				out.print(newName+"       ");
			}
			else
			{
				//Prints name, stats will follow later
				out.print(visitor.pitcherList.get(0).name.substring(0, 13)+"       ");
			}
			
			
			String criticalIP;
			if(visitor.pitcherList.get(0).stats.IP>9)
			{
				criticalIP = (int)visitor.pitcherList.get(0).stats.IP+"   ";
			}
			else
			{
				criticalIP = (int)visitor.pitcherList.get(0).stats.IP+"    ";
			}
			
			String criticalH;
			if(visitor.pitcherList.get(0).stats.pH>9)
			{
				criticalH = (int)visitor.pitcherList.get(0).stats.pH+"  ";
			}
			else
			{
				criticalH = (int)visitor.pitcherList.get(0).stats.pH+"   ";
			}
			
			String criticalR;
			if(visitor.pitcherList.get(0).stats.pR>9)
			{
				criticalR = (int)visitor.pitcherList.get(0).stats.pR+"     ";
			}
			else
			{
				criticalR = (int)visitor.pitcherList.get(0).stats.pR+"      ";
			}
			
			String criticalERA;
			if(visitor.pitcherList.get(0).stats.pR/(visitor.pitcherList.get(0).stats.IP/9)>9)
			{
				criticalERA = formatter.format(visitor.pitcherList.get(0).stats.pR/(visitor.pitcherList.get(0).stats.IP/9))+"  ";
			}
			else
			{
				criticalERA = formatter.format(visitor.pitcherList.get(0).stats.pR/(visitor.pitcherList.get(0).stats.IP/9))+"   ";
			}
			
			String criticalK;
			if(visitor.pitcherList.get(0).stats.pK>9)
			{
				criticalK = visitor.pitcherList.get(0).stats.pK+"   ";
			}
			else
			{
				criticalK = visitor.pitcherList.get(0).stats.pK+"    ";
			}
			
			
			out.println(criticalIP+criticalH+visitor.pitcherList.get(0).stats.ptwoB+"   "+visitor.pitcherList.get(0).stats.pthreeB+"   "+visitor.pitcherList.get(0).stats.pHR+"   "
			+criticalR+criticalERA+visitor.pitcherList.get(0).stats.pBB+"    "+criticalK+visitor.pitcherList.get(0).stats.wins+"-"+visitor.pitcherList.get(0).stats.losses);
			
			
			
			out.println("\n"+home.name+" Stats");
			
			out.println("Batter         POS  AB   H   2B  3B  HR  R      RBI     BB   K    AVG");
			
			for(Batter b: home.batterList)
			{
				if(b.name.length()<13)
				{
					String newName = b.name;
					for(int x=0; x<13-b.name.length(); x++)
					{
						newName+=" ";
					}
					out.print(newName+"  ");
				}
				else
				{
					out.print(b.name.substring(0, 13)+"  ");
				}
				
				
				out.println(b.stats.POS+"   "+(int)b.stats.AB+"    "+(int)b.stats.H+"   "+b.stats.twoB+"   "+b.stats.threeB+"   "+b.stats.HR+"   "+b.stats.R+"      "+b.stats.RBI+"       "+b.stats.BB+"    "+b.stats.K+"    "+formatter.format(b.stats.H/b.stats.AB));
			}
			
			out.println("Pitcher             IP   H   2B  3B  HR  R      ERA     BB   K    W-L");
				if(home.pitcherList.get(0).name.length()<13)
				{
					String newName = home.pitcherList.get(0).name;
					for(int x=0; x<13-home.pitcherList.get(0).name.length(); x++)
					{
						newName+=" ";
					}
					out.print(newName+"       ");
				}
				else
				{
					out.print(home.pitcherList.get(0).name.substring(0, 13)+"       ");
				}
				
				
				if(home.pitcherList.get(0).stats.IP>9)
				{
					criticalIP = (int)home.pitcherList.get(0).stats.IP+"   ";
				}
				else
				{
					criticalIP = (int)home.pitcherList.get(0).stats.IP+"    ";
				}
				
				if(home.pitcherList.get(0).stats.pH>9)
				{
					criticalH = (int)home.pitcherList.get(0).stats.pH+"  ";
				}
				else
				{
					criticalH = (int)home.pitcherList.get(0).stats.pH+"   ";
				}
				
				if(home.pitcherList.get(0).stats.pR>9)
				{
					criticalR = (int)home.pitcherList.get(0).stats.pR+"     ";
				}
				else
				{
					criticalR = (int)home.pitcherList.get(0).stats.pR+"      ";
				}
				
				if(home.pitcherList.get(0).stats.pR/(home.pitcherList.get(0).stats.IP/9)>9)
				{
					criticalERA = formatter.format(home.pitcherList.get(0).stats.pR/(home.pitcherList.get(0).stats.IP/9))+"  ";
				}
				else
				{
					criticalERA = formatter.format(home.pitcherList.get(0).stats.pR/(home.pitcherList.get(0).stats.IP/9))+"   ";
				}
				
				if(home.pitcherList.get(0).stats.pK>9)
				{
					criticalK = home.pitcherList.get(0).stats.pK+"   ";
				}
				else
				{
					criticalK = home.pitcherList.get(0).stats.pK+"    ";
				}	
				
				out.println(criticalIP+criticalH+home.pitcherList.get(0).stats.ptwoB+"   "+home.pitcherList.get(0).stats.pthreeB+"   "+home.pitcherList.get(0).stats.pHR+"   "
				+criticalR+criticalERA+home.pitcherList.get(0).stats.pBB+"    "+criticalK+home.pitcherList.get(0).stats.wins+"-"+home.pitcherList.get(0).stats.losses);
	}
	

}
