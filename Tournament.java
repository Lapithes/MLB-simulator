import java.util.*;
import java.io.*;
public class Tournament {//fix consolation seeding, store losing teams somewhere, seeding bug?
	//when second consol round if filled, activate consolseed
	Team[] main1; //array template for tournament
	//maybe use a dynamic list/set etc
	Team[] main2;
	Team[] main3;
	Team[] main4;
	Team[] consolseed;
	//Team[] consolseed2;
	Team[] consol1;
	Team[] consol2;
	Team[] consol3;
	Team[] consol4;
	ArrayList<Team> losers1;
	ArrayList<Team> losers2;
	ArrayList<Team> losers3;
	ArrayList<Team> winners;
	int roundnum;
	int consolnum;
	Team[][] standings;
	boolean complete;
	ArrayList<Integer> completed1;
	ArrayList<Integer> completed2;
	String type;
	
	String rules;
	
	//differentiate NLT and ALT
	//then implement round robin
	//then sort and print draft pick
	
	public Tournament(MLB mlb, Team[][] st, String r)
	{
		rules = r;
		type = "";
		main1 = new Team[15];
		main2 = new Team[8];
		main3 = new Team[4];
		main4 = new Team[2];
		consol1 = new Team[7];
		consol2 = new Team[8];
		consol3 = new Team[4];
		consol4 = new Team[2];
		roundnum = 0;
		consolnum = 0;
		completed1 = new ArrayList<Integer>();
		completed2 = new ArrayList<Integer>();
		losers1 = new ArrayList<Team>();
		losers2 = new ArrayList<Team>();
		winners = new ArrayList<Team>();
		//series9 = new Team[2];
		/*main1 = new Team[8][2];
		main2 = new Team[4][2];
		main3 = new Team[4][2];
		main4 = new Team[2][2];
		consol1 = new Team[4][2];
		consol2 = new Team[1][2];
		consol3 = new Team[1][2];
		round = new Team[2][2];
		series9 = new Team[1][2];*/
		standings = st;
		complete = false;
	}
	
	public void play(Scanner kb) throws IOException //create another array for seeding,
	//rearrange main1 to reflect tournament bracket, compare main1 to seeding array
	{
		/*while(roundnum<2)
		{
			
			playRound(kb, roundnum++);
		}*/
		
		while(!complete)
		{
			System.out.println("\nChoose Bracket");
			if(roundnum<2||consolnum==4)
			{
				System.out.println("1.  Main bracket\n2.  Consolation bracket--UNAVAILABLE\n3.  Back");
				System.out.print("Enter: ");
				String in = kb.nextLine();
				if(in.equals("1"))
				{
					if(playRound(kb, roundnum))
						roundnum++;
					
				}
				else if(in.equals("3"))
				{
					break;
				}
		
			}
			else if(roundnum==4)
			{
				System.out.println("\n1.  Main bracket--UNAVAILABLE\n2.  Consolation bracket\n3.  Back");
				System.out.print("Enter: ");
				String in = kb.nextLine();
				if(in.equals("2"))
				{
					if(playConsol(kb, consolnum))
						consolnum++;
				}
				else if(in.equals("3"))
				{
					break;
				}
			}
			else
			{
				System.out.println("\n1.  Main bracket\n2.  Consolation bracket\n3.  Back");
				System.out.print("Enter: ");
				String in = kb.nextLine();
				if(in.equals("1"))
				{
					if(playRound(kb, roundnum))
						roundnum++;
				}
				else if(in.equals("2"))
				{
					if(playConsol(kb, consolnum))
						consolnum++;
				}
				else if(in.equals("3"))
				{
					break;
				}
			}
			
			check();
		}
		
		
		/*for(int x=2; x<5; x++)
		{
			if()
			incomplete = true;
			completed.clear();
		}*/
		
		
	}
	
	public boolean playRound(Scanner kb, int r) throws IOException
	{
		boolean incomplete = true;
		
		if(r == 0)
		{
			boolean playall = false;
			while(incomplete)
			{
				int input;
				
				if(playall)
					input = 8;
					
				else
				{
					printNext(r);
					System.out.println("8.  Play all");
					System.out.println("9.  Back");
					System.out.print("Enter: ");
					input = Integer.parseInt(kb.nextLine());
				}
				
				if(input == 9)
				{
					return false;
				}
				
				else if(input == 8)
				{
					playall = true;
					int num = 0;
					for(int x=1; x<8; x++)
					{
						if(!completed1.contains(x))
						{
							num = x;
							break;
						}
					}
					input = num;
				}
				if(completed1.contains(input))
				{
					System.out.println("Series has already been played.");
				}
				else
				{
					Team team1;
					Team team2;
					Team[] results;
					switch(input)
					{
						default: 
							System.out.println("Invalid input, please try again");
							break;
						case 1:
							team1 = main1[7];
							team2 = main1[8];
							results = playSeries(team1, team2, "1");
							main2[1] = results[0];
							consol1[0] = results[1];//
							completed1.add(input);
							break;
							
						case 2:
							team1 = main1[4];
							team2 = main1[11];
							results = playSeries(team1, team2, "1");
							main2[2] = results[0];
							consol1[1] = results[1];
							completed1.add(input);
							break;
							
						case 3:
							team1 = main1[3];
							team2 = main1[12];
							results = playSeries(team1, team2, "1");
							main2[3] = results[0];
							consol1[2] = results[1];
							completed1.add(input);
							break;
							
						case 4:
							team1 = main1[2];
							team2 = main1[13];
							results = playSeries(team1, team2, "1");
							main2[4] = results[0];
							consol1[3] = results[1];
							completed1.add(input);
							break;
							
						case 5:
							team1 = main1[5];
							team2 = main1[10];
							results = playSeries(team1, team2, "1");
							main2[5] = results[0];
							consol1[4] = results[1];
							completed1.add(input);
							break;
							
						case 6:
							team1 = main1[6];
							team2 = main1[9];
							results = playSeries(team1, team2, "1");
							main2[6] = results[0];
							consol1[5] = results[1];
							completed1.add(input);
							break;
							
						case 7:
							team1 = main1[1];
							team2 = main1[14];
							results = playSeries(team1, team2, "1");
							main2[7] = results[0];
							consol1[6] = results[1];
							completed1.add(input);
							break;
						
					}
					if(!playall&&completed1.size()<7)
					{
						for(int y=0; y<8; y++)
						{
							printNext(y);
						}
						System.out.println();
					}
					
				}
				
				if(completed1.size()>=7)
				{
					incomplete = false;
					completed1.clear();
					Arrays.sort(consol1);
					consol2[0] = consol1[0];
					for(int y=0; y<8; y++)
					{
						printNext(y);
					}
					System.out.println();
					return true;
				}
				//break;//remove from finished product
			}
		}
		else if(r == 1)
		{
			while(incomplete)
			{
				int input = 0;
				
				printNext(r);
				System.out.println("5.  Play all");
				System.out.println("6.  Back");
				System.out.print("Enter: ");
				input = Integer.parseInt(kb.nextLine());
				
				if(input==6)
				{
					return false;
				}
				
				else if(input == 5)
				{
					for(int x=0; x<8; x+=2)
					{
						if(!completed1.contains(x/2+1))
						{
							Team h;
							Team v;
							if(Arrays.binarySearch(main1, main2[x])<Arrays.binarySearch(main1, main2[x+1]))
							{
								h = main2[x];
								v = main2[x+1];
							}
							else
							{
								h = main2[x+1];
								v = main2[x];
							}
							Team[] results = playSeries(h, v, "2");
							main3[x/2] = results[0];
							consol2[x+1] = results[1];
							completed1.add(input);
						}
					}
					for(int y=0; y<8; y++)
					{
						printNext(y);
					}
					System.out.println();
				}
				else if(input<5)
				{
					if(completed1.contains(input))
					{
						System.out.println("Series has already been played.");
					}
					else
					{
						int x = 2*(input-1);
						Team h;
						Team v;
						if(Arrays.binarySearch(main1, main2[x])<Arrays.binarySearch(main1, main2[x+1]))
						{
							h = main2[x];
							v = main2[x+1];
						}
						else
						{
							h = main2[x+1];
							v = main2[x];
						}
						Team[] results = playSeries(h, v, "2");
						main3[x/2] = results[0];
						consol2[x+1] = results[1];
						completed1.add(input);
						for(int y=0; y<8; y++)
						{
							printNext(y);
						}
						System.out.println();
					}
					
				}
				if(completed1.size()>=4)
				{
					incomplete = false;
					completed1.clear();
					return true;
				}
				
		
			}
		}
		else if(r==2)
		{
			while(incomplete)
			{
				int input = 0;
				
				printNext(r);
				System.out.println("3.  Play all");
				System.out.println("4.  Back");
				System.out.print("Enter: ");
				input = Integer.parseInt(kb.nextLine());
				
				if(input==4)
				{
					return false;
				}
				
				else if(input == 3)
				{
					for(int x=0; x<4; x+=2)
					{
						if(!completed1.contains(x/2+1))
						{
							Team h;
							Team v;
							if(Arrays.binarySearch(main1, main3[x])<Arrays.binarySearch(main1, main3[x+1]))
							{
								h = main3[x];
								v = main3[x+1];
							}
							else
							{
								h = main3[x+1];
								v = main3[x];
							}
							Team[] results = playSeries(h, v, "2");
							main4[x/2] = results[0];
							completed1.add(input);
						}
					}
					for(int y=0; y<8; y++)
					{
						printNext(y);
					}
					System.out.println();
				}
				else if(input<3)
				{
					if(completed1.contains(input))
					{
						System.out.println("Series has already been played.");
					}
					else
					{
						int x = 2*(input-1);
						Team h;
						Team v;
						if(Arrays.binarySearch(main1, main3[x])<Arrays.binarySearch(main1, main3[x+1]))
						{
							h = main3[x];
							v = main3[x+1];
						}
						else
						{
							h = main3[x+1];
							v = main3[x];
						}
						Team[] results = playSeries(h, v, "3");
						main4[x/2] = results[0];
						winners.add(results[1]);
						completed1.add(input);
						for(int y=0; y<8; y++)
						{
							printNext(y);
						}
						System.out.println();
					}
					
				}
				if(completed1.size()>=2)
				{
					incomplete = false;
					completed1.clear();
					return true;
				}
				
		
			}
		}
		else if(r==3)
		{
			while(incomplete)
			{
				int input = 0;
				
				printNext(r);
				System.out.println("2.  Back");
				System.out.print("Enter: ");
				input = Integer.parseInt(kb.nextLine());
				
				if(input==2)
				{
					return false;
				}
				
				if(input<2)
				{
					if(completed1.contains(input))
					{
						System.out.println("Series has already been played.");
					}
					else
					{
						int x = 2*(input-1);
						Team h;
						Team v;
						if(Arrays.binarySearch(main1, main4[x])<Arrays.binarySearch(main1, main4[x+1]))
						{
							h = main4[x];
							v = main4[x+1];
						}
						else
						{
							h = main4[x+1];
							v = main4[x];
						}
						Team[] results = playSeries(h, v, "4");
						winners.add(results[1]);
						winners.add(results[0]);
						System.out.println(results[0].name+" WINS!");
						completed1.add(input);
						for(int y=0; y<8; y++)
						{
							printNext(y);
						}
						System.out.println();
					}
					
				}
				if(completed1.size()>=2)
				{
					incomplete = false;
					completed1.clear();
					return true;
				}
				
		
			}
		}
		return false;
	}
	
	public boolean playConsol(Scanner kb, int r) throws IOException
	{
		boolean incomplete = true;
		if(r==0)
		{
			while(incomplete)
			{
				int input = 0;
				
				printNext(4);
				System.out.println("4.  Play all");
				System.out.println("5.  Back");
				System.out.print("Enter: ");
				input = Integer.parseInt(kb.nextLine());
				
				if(input==5)
				{
					return false;
				}
				
				else if(input == 4)
				{
					for(int x=2; x<8; x+=2)
					{
						if(!completed2.contains(x/2+1))
						{
							Team h;
							Team v;
							if(Arrays.binarySearch(consol1, consol1[x])<Arrays.binarySearch(consol1, consol1[x+1]))
							{
								h = consol1[x-1];
								v = consol1[x-1+1];
							}
							else
							{
								h = consol1[x-1+1];
								v = consol1[x-1];
							}
							Team[] results = playSeries(h, v, "C1");
							consol2[x/2] = results[0];
							completed2.add(input);
						}
					}
					for(int y=0; y<8; y++)
					{
						printNext(y);
					}
					System.out.println();
				}
				else if(input<4)
				{
					if(completed2.contains(input))
					{
						System.out.println("Series has already been played.");
					}
					else
					{
						int x = 2*(input-1)+2;
						Team h;
						Team v;
						if(Arrays.binarySearch(main1, main3[x])<Arrays.binarySearch(main1, main3[x+1]))
						{
							h = consol1[x-1];
							v = consol1[x-1+1];
						}
						else
						{
							h = consol1[x-1+1];
							v = consol1[x-1];
						}
						Team[] results = playSeries(h, v, "C1");
						consol2[x/2] = results[0];
						winners.add(results[1]);
						completed2.add(input);
						for(int y=0; y<8; y++)
						{
							printNext(y);
						}
						System.out.println();
					}
					
				}
				if(completed2.size()>=2)
				{
					incomplete = false;
					completed2.clear();
					return true;
				}
				
		
			}
		}
		
		if(r==1)
		{
			while(incomplete)
			{
				int input = 0;
				
				printNext(4);
				System.out.println("5.  Play all");
				System.out.println("6.  Back");
				System.out.print("Enter: ");
				input = Integer.parseInt(kb.nextLine());
				
				if(input==6)
				{
					return false;
				}
				
				else if(input == 5)
				{
					for(int x=0; x<8; x+=2)
					{
						if(!completed2.contains(x/2+1))
						{
							Team h;
							Team v;
							if(Arrays.binarySearch(consol1, consol1[x])<Arrays.binarySearch(consol1, consol1[x+1]))
							{
								h = consol1[x];
								v = consol1[x+1];
							}
							else
							{
								h = consol1[x+1];
								v = consol1[x];
							}
							Team[] results = playSeries(h, v, "C1");
							consol2[x/2] = results[0];
							completed2.add(input);
						}
					}
					for(int y=0; y<8; y++)
					{
						printNext(y);
					}
					System.out.println();
				}
				else if(input<4)
				{
					if(completed2.contains(input))
					{
						System.out.println("Series has already been played.");
					}
					else
					{
						int x = 2*(input-1)+2;
						Team h;
						Team v;
						if(Arrays.binarySearch(main1, main3[x])<Arrays.binarySearch(main1, main3[x+1]))
						{
							h = consol1[x-1];
							v = consol1[x-1+1];
						}
						else
						{
							h = consol1[x-1+1];
							v = consol1[x-1];
						}
						Team[] results = playSeries(h, v, "C1");
						consol2[x/2] = results[0];
						winners.add(results[1]);
						completed2.add(input);
						for(int y=0; y<8; y++)
						{
							printNext(y);
						}
						System.out.println();
					}
					
				}
				if(completed2.size()>=2)
				{
					incomplete = false;
					completed2.clear();
					return true;
				}
				
		
			}
		}
		return false;//false
		//consolseed = consol2;
		//Arrays.sort(consolseed);
	}
	
	public Team[] playSeries(Team t1, Team t2, String round) throws IOException
	{
		Team[] result = new Team[2];
		Team winner;
		Team loser;
		int t1points = 0;
		int t2points = 0;
		String temp = "";
		if(rules.equals("n"))
		{
			temp += "NLT round "+round;
		}
		else
		{
			temp += "ALT round "+round;
		}
		
		for(int x=1; x<6; x++)
		{
			
			if(x==1||x==2||x==5)
			{
				String filename = temp+" "+t1.name+" vs. "+t2.name+" game("+x+")";
				PrintWriter out = new PrintWriter(new File("C:\\Users\\rzhon\\eclipse-workspace\\Baseball\\played_games\\post\\"+filename+".txt"));
				Game g = new Game(out, t1, t2, rules);
				g.run();
				if(g.HRhome>g.HRvisitor)
				{
					t1points++;
					System.out.println("\tWinner: "+g.home.name);
				}
					
				else
				{
					t2points++;
					System.out.println("\tWinner: "+g.visitor.name);
				}
					
				out.close();
			}
			else
			{
				String filename = temp+" "+t2.name+" vs. "+t1.name+" game("+x+")";
				PrintWriter out = new PrintWriter(new File("C:\\Users\\rzhon\\eclipse-workspace\\Baseball\\played_games\\post\\"+filename+".txt"));
				Game g = new Game(out, t2, t1, rules);
				g.run();
				if(g.HRhome>g.HRvisitor)
				{
					t2points++;
					System.out.println("\tWinner: "+g.home.name);
				}
					
				else
				{
					t1points++;
					System.out.println("\tWinner: "+g.visitor.name);
				}
			}
			if(t1points==3||t2points==3)
			{
				break;
			}
			
		}
		
		if(t1points>t2points)
		{
			winner = t1;
			loser = t2;
		}
		else
		{
			winner = t2;
			loser = t1;
		}
		result[0] = winner;
		result[1] = loser;
		System.out.println(winner.name+" wins series");
		System.out.println();
		return result;
	}
	
	public void printNext(int roundnum)
	{
		if(roundnum==0)
		{
			System.out.println();
			System.out.println("Round 1");
			System.out.println("(1)"+main1[0].name+" vs. bye");
			System.out.println("1:  (9)"+main1[8].name+" vs. (8)"+main1[7].name);
			System.out.println("2:  (12)"+main1[11].name+" vs. (5)"+main1[4].name);
			System.out.println("3:  (13)"+main1[12].name+" vs. (4)"+main1[3].name);
			System.out.println("4:  (14)"+main1[13].name+" vs. (3)"+main1[2].name);
			System.out.println("5:  (11)"+main1[10].name+" vs. (6)"+main1[5].name);
			System.out.println("6:  (10)"+main1[9].name+" vs. (7)"+main1[6].name);
			System.out.println("7:  (15)"+main1[14].name+" vs. (2)"+main1[1].name);
		}
		
		if(roundnum==1)
		{
			System.out.println("--------------------------------");
			System.out.println("Round 2\n");
			printRound(main1, main2);
		}
		
		if(roundnum==2)
		{
			System.out.println("--------------------------------");
			System.out.println("Round 3\n");
			printRound(main1, main3);
		}
		
		if(roundnum==3)
		{
			System.out.println("--------------------------------");
			System.out.println("Round 4\n");
			printRound(main1, main4);
		}
		
		
		boolean filled = true;
		for(Team t : consol1)
		{
			if(t==null)
			{
				filled = false;
				break;
			}
		}
		if(filled)
		{
			if(roundnum==4)
			{
				System.out.println("--------------------------------");
				System.out.println("Consol 1\n");
				System.out.println("(1)"+consol1[0].name+" vs. bye");
				System.out.println("1:  (5)"+consol1[4].name+" vs. (4)"+consol1[3].name);
				System.out.println("2:  (6)"+consol1[5].name+" vs. (3)"+consol1[2].name);
				System.out.println("3:  (7)"+consol1[6].name+" vs. (2)"+consol1[1].name);
			}
			
			if(roundnum==5)
			{
				System.out.println("--------------------------------");
				System.out.println("Consol 2\n");
				boolean filled2 = true;
				for(int x=0; x<consol2.length; x++)
				{
					if(consol2[x]==null)
					{
						filled2 = false;
						break;
					}
				}
				if(filled2)
				{
					printRound(consolseed, consol2);
				}
				else
				{
					printRound(consol1, consol2);
				}
			}
			
			if(roundnum==6)
			{
				System.out.println("--------------------------------");
				System.out.println("Consol 3\n");
				printRound(consolseed, consol3);
			}
			
			if(roundnum==7)
			{
				System.out.println("--------------------------------");
				System.out.println("Consol 4\n");
				printRound(consolseed, consol4);
			}
			
		}
		
	}
	
	public void printRound(Team[] original, Team[] round)
	{
		
		for(int x=0; x<round.length; x+=2)
		{
			int option = x/2+1;
			if(round[x]!=null && round[x+1]!=null)
			{
				int seed1 = Arrays.binarySearch(original, round[x])+1;
				if(seed1<0)
				{
					seed1 = 0;
				}
					
				int seed2 = Arrays.binarySearch(original, round[x+1])+1;
				if(seed2<0)
				{
					seed2 = 0;
				}
					
				System.out.println(option+":  ("+seed1+")"+round[x].name+" vs. "+
						"("+seed2+")"+round[x+1].name);
			}
			else if(round[x]!=null && round[x+1]== null)
			{
				int seed1 = Arrays.binarySearch(original, round[x])+1;
				if(seed1<0)
				{
					seed1 = 0;
				}
					
				System.out.println(option+":  ("+seed1+")"+round[x].name+" vs. <Empty slot>");
			}
			else if(round[x]==null && round[x+1]!= null)
			{
				int seed2 = Arrays.binarySearch(original, round[x+1])+1;
				if(seed2<0)
				{
					seed2 = 0;
				}	
				System.out.println(option+":  <Empty slot> vs. ("+seed2+")"+round[x+1].name);
			}
			else
			{
				System.out.println(option+":  <Empty slot> vs. <Empty slot>");
			}
		}
	}
	
	public void check()
	{
		if(roundnum == 4 && consolnum == 4)
		{
			complete = true;
		}
	}
	
	public void playNext()
	{
		
	}

}
