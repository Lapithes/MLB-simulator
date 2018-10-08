import java.util.*;
import java.io.*;
public class GameRunner {//fix exception in Scanner 

	public static void main(String[] args) throws IOException {
		
		MLB mlb = new MLB();
		
		Schedule regular = new Schedule(mlb);
		
		Leaderboard board = new Leaderboard(mlb);
		
		PostSeason post = new PostSeason(mlb, board);
		
		mainMenu(mlb, regular, board, post, false);
		
		//board.printLeagueLeaders(0, 30, Category.peHR);
		
	}
	
	
	
	
	//changed the "play" submenu to give a postseason option after all regular season games are played
	public static void mainMenu(MLB mlb, Schedule regular, Leaderboard board, PostSeason post, boolean postinit) throws IOException
	{
		Scanner kb = new Scanner(System.in);
		
		System.out.println("\nMain Menu:\n1.  Schedule\n2.  Play\n3.  Standings\n4.  League Leaders\n5.  Quit");
		System.out.print("Enter: ");
		
		String s = kb.nextLine();
		
		if(s.equals("1"))
		{
			System.out.println("\n1.  Team\n2.  League\n3.  Team vs. Team\n4.  Back");
			System.out.print("Enter: ");
			String s1 = kb.nextLine();
			if(s1.equals("1"))
			{
				System.out.println("\nNL:\t\t\tAL:\n1.  Braves\t\t16. Orioles\n2.  Cubs\t\t17. Red Sox\n3.  Diamondbacks\t18. Yankees\n4.  Marlins\t\t19. Bay Rays\n5.  Reds\t\t20. Blue Jays\n6.  Rockies\t\t21. White Sox"
						+ "\n7.  Mets\t\t22. Indians\n8.  Brewers\t\t23. Tigers\n9.  Dodgers\t\t24. Royals\n10. Phillies\t\t25. Twins\n11. Pirates\t\t26. Astros\n12. Padres\t\t27. Angels\n13. Nationals\t\t28. A's\n14. Cardinals\t\t29. Mariners\n15. Giants\t\t30. Rangers");
				System.out.print("Enter: ");
				regular.printTeamSchedule(Integer.parseInt(kb.nextLine()), mlb);
				System.out.println("Execution complete");
			}
			else if(s1.equals("2"))
			{
				System.out.println("\n1.  National\n2.  American\n3.  Major League");
				System.out.print("Enter: ");
				String l = kb.nextLine();
				regular.printLeagueSchedule(l);
				System.out.println("Execution complete");
			}
			else if(s1.equals("3"))
			{
				System.out.println("\n1.  National\n2.  American");
				System.out.print("Enter: ");
				String s2 = kb.nextLine();
				if(s2.equals("1"))
				{
					System.out.println("\n1.  Braves\n2.  Cubs\n3.  Diamondbacks\n4.  Marlins\n5.  Reds\n6.  Rockies"
							+ "\n7.  Mets\n8.  Brewers\n9.  Dodgers\n10. Phillies\n11. Pirates\n12. Padres\n13. Nationals\n14. Cardinals\n15. Giants");
					System.out.print("Enter 1: ");
					int teamnum1 = Integer.parseInt(kb.nextLine());
					System.out.print("Enter 2: ");
					int teamnum2 = Integer.parseInt(kb.nextLine());
					if((teamnum1>0&&teamnum1<16)&&(teamnum2>0&&teamnum2<16))
					{
						regular.printTeamVTeam(0, teamnum1, teamnum2, mlb);
					}
						
				}
				else if(s2.equals("2"))
				{
					System.out.println("\n1.  Orioles\n2.  Red Sox\n3.  Yankees\n4.  Bay Rays\n5.  Blue Jays\n6.  White Sox"
							+ "\n7.  Indians\n8.  Tigers\n9.  Royals\n10. Twins\n11. Astros\n12. Angels\n13. A's\n14. Mariners\n15. Rangers");
					System.out.print("Enter 1: ");
					int teamnum1 = Integer.parseInt(kb.nextLine());
					System.out.print("Enter 2: ");
					int teamnum2 = Integer.parseInt(kb.nextLine());
					if((teamnum1>0&&teamnum1<16)&&(teamnum2>0&&teamnum2<16))
					{
						regular.printTeamVTeam(1, teamnum1, teamnum2, mlb);
					}
						
				}
				System.out.println("Execution complete");
			}
			else if(s1.equals("4"))
			{
				mainMenu(mlb, regular, board, post, postinit);
			}
			mainMenu(mlb, regular, board, post, postinit);
		}
		else if(s.equals("2"))
		{
			boolean seasoncomplete = regular.day>119;
			if(seasoncomplete)
			{
				System.out.println("\n1.  Game	\n2.  Days\n3.  Season\n4.  PostSeason\n5.  Back");
			}
			else
			{
				System.out.println("\n1.  Game	\n2.  Days\n3.  Season\n4.  Back");
			}
			System.out.print("Enter: ");
			
			String s1 = kb.nextLine();
			if(s1.equals("1"))
			{
				regular.playGame(0);
			}
			else if(s1.equals("2"))
			{
				System.out.println(91-(regular.day-(regular.day/4))+" playable days left in this season");
				System.out.print("Enter number of days: ");
				int num = Integer.parseInt(kb.nextLine());
				
				if(num>0)
					regular.playGame(num);
			}
			else if(s1.equals("3"))
			{
				regular.playGame(91 -(regular.day-(regular.day/4)));
			}
			else if(s1.equals("4"))
			{
				if(seasoncomplete)
				{
					if(!postinit)
					{
						post = new PostSeason(mlb, board);//needs edit
						postinit = true;
					}
					
					System.out.println("\n1.  NLT\n2.  ALT");
					System.out.print("Enter: ");
					String s2 = kb.nextLine();
				
					if(s2.equals("1"))
					{
						post.runNLT(kb);
						
					}
					else if(s2.equals("2"))
					{
						post.runALT(kb);		
					}
					
				}
				else
					mainMenu(mlb, regular, board, post, postinit);
			}
			mainMenu(mlb, regular, board, post, postinit);
		}
		else if(s.equals("3"))
		{
			System.out.println("\n1.  National League\n2.  American League\n3.  MLB\n4.  Back");
			System.out.print("Enter: ");
			String s1 = kb.nextLine();
			if(s1.equals("1"))
			{
				board.printStandings(0, 15);
				System.out.println("Execution complete");
			}
			else if(s1.equals("2"))
			{
				board.printStandings(15, 30);
				System.out.println("Execution complete");
			}
			else if(s1.equals("3"))
			{
				board.printStandings(0, 30);
				System.out.println("Execution complete");
			}
			else if(s1.equals("4"))
			{
				mainMenu(mlb, regular, board, post, postinit);
			}
			mainMenu(mlb, regular, board, post, postinit);
		}
		else if(s.equals("4"))
		{
			System.out.println("\n1.  Batters\n2.  Pitchers\n3.  Back");
			System.out.print("Enter: ");
			String s1 = kb.nextLine();
			
			if(s1.equals("3"))
			{
				mainMenu(mlb, regular, board, post, postinit);
			}
			System.out.println("\n1.  National League\n2.  American League\n3.  MLB");
			System.out.print("Enter: ");
			String s2 = kb.nextLine();
			if(s1.equals("1"))
			{
				System.out.println("\n1.  AB\n2.  H\n3.  2B\n4.  3B\n5.  HR\n6.  R\n7.  RBI\n8.  BB\n9.  K\n10. AVG");
				System.out.print("Enter: ");
				int i = Integer.parseInt(kb.nextLine());
				
				if(s2.equals("1"))
				{
					String filename = "";
					if(i==1)
						filename = "NL AB Rankings";
					else if(i==2)
						filename = "NL H Rankings";
					else if(i==3)
						filename = "NL 2B Rankings";
					else if(i==4)
						filename = "NL 3B Rankings";
					else if(i==5)
						filename = "NL HR Rankings";
					else if(i==6)
						filename = "NL R Rankings";
					else if(i==7)
						filename = "NL RBI Rankings";
					else if(i==8)
						filename = "NL BB Rankings";
					else if(i==9)
						filename = "NL K Rankings";
					else if(i==10)
						filename = "NL AVG Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("National League Rankings");
					board.printBatterLeagueLeaders(0, 135, i, out);
					out.close();
				}
					
				else if(s2.equals("2"))
				{
					String filename = "";
					if(i==1)
						filename = "AL AB Rankings";
					else if(i==2)
						filename = "AL H Rankings";
					else if(i==3)
						filename = "AL 2B Rankings";
					else if(i==4)
						filename = "AL 3B Rankings";
					else if(i==5)
						filename = "AL HR Rankings";
					else if(i==6)
						filename = "AL R Rankings";
					else if(i==7)
						filename = "AL RBI Rankings";
					else if(i==8)
						filename = "AL BB Rankings";
					else if(i==9)
						filename = "AL K Rankings";
					else if(i==10)
						filename = "AL AVG Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("American League Rankings");
					board.printBatterLeagueLeaders(135, 270, i, out);
					out.close();
				}
					
				else if(s2.equals("3"))
				{
					String filename = "";
					if(i==1)
						filename = "MLB AB Rankings";
					else if(i==2)
						filename = "MLB H Rankings";
					else if(i==3)
						filename = "MLB 2B Rankings";
					else if(i==4)
						filename = "MLB 3B Rankings";
					else if(i==5)
						filename = "MLB HR Rankings";
					else if(i==6)
						filename = "MLB R Rankings";
					else if(i==7)
						filename = "MLB RBI Rankings";
					else if(i==8)
						filename = "MLB BB Rankings";
					else if(i==9)
						filename = "MLB K Rankings";
					else if(i==10)
						filename = "MLB AVG Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("MLB Rankings");
					board.printBatterLeagueLeaders(0, 270, i, out);
					out.close();
				}
				System.out.println("Execution Complete");
			}
			else if(s1.equals("2"))
			{
				System.out.println("\n1.  IP\n2.  pH\n3.  p2B\n4.  p3B\n5.  pHR\n6.  pR\n7.  ERA\n8.  pBB\n9.  pK");
				System.out.print("Enter: ");
				int i = Integer.parseInt(kb.nextLine());
				if(s2.equals("1"))
				{
					String filename = "";
					if(i==1)
						filename = "NL IP Rankings";
					else if(i==2)
						filename = "NL pH Rankings";
					else if(i==3)
						filename = "NL p2B Rankings";
					else if(i==4)
						filename = "NL p3B Rankings";
					else if(i==5)
						filename = "NL pHR Rankings";
					else if(i==6)
						filename = "NL pR Rankings";
					else if(i==7)
						filename = "NL ERA Rankings";
					else if(i==8)
						filename = "NL pBB Rankings";
					else if(i==9)
						filename = "NL pK Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("National League Rankings");
					board.printPitcherLeagueLeaders(0, 75, i, out);
					out.close();
				}
					
				else if(s2.equals("2"))
				{
					String filename = "";
					if(i==1)
						filename = "AL IP Rankings";
					else if(i==2)
						filename = "AL pH Rankings";
					else if(i==3)
						filename = "AL p2B Rankings";
					else if(i==4)
						filename = "AL p3B Rankings";
					else if(i==5)
						filename = "AL pHR Rankings";
					else if(i==6)
						filename = "AL pR Rankings";
					else if(i==7)
						filename = "AL ERA Rankings";
					else if(i==8)
						filename = "AL pBB Rankings";
					else if(i==9)
						filename = "AL pK Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("American League Rankings");
					board.printPitcherLeagueLeaders(75, 150, i, out);
					out.close();
				}
					
				else if(s2.equals("3"))
				{
					String filename = "";
					if(i==1)
						filename = "MLB IP Rankings";
					else if(i==2)
						filename = "MLB pH Rankings";
					else if(i==3)
						filename = "MLB p2B Rankings";
					else if(i==4)
						filename = "MLB p3B Rankings";
					else if(i==5)
						filename = "MLB pHR Rankings";
					else if(i==6)
						filename = "MLB pR Rankings";
					else if(i==7)
						filename = "MLB ERA Rankings";
					else if(i==8)
						filename = "MLB pBB Rankings";
					else if(i==9)
						filename = "MLB pK Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("MLB Rankings");
					board.printPitcherLeagueLeaders(0, 150, i, out);
					out.close();
				}
					System.out.println("Execution complete");
			}
					
			//board.printBatterLeagueLeaders(0, 270);//Category.peHR
			mainMenu(mlb, regular, board, post, postinit);
		}
		else if(s.equals("5"))
		{
			System.out.println("Exit");
			System.exit(0);
		}
		
		else
		{
			System.out.println("Invalid input. Please try again.");
			mainMenu(mlb, regular, board, post, postinit);
		}
		kb.close();
	}
	
	/*public static void postSeasonMainMenu(mlb, regular, board, post)
	{
		Scanner kb = new Scanner(System.in);
		
		System.out.println("\nMain Menu:\n1.  Schedule\n2.  Play\n3.  Standings\n4.  League Leaders\n5.  Quit");
		System.out.print("Enter: ");
		String s = kb.nextLine();
		
		if(s.equals("1"))
		{
			System.out.println("\n1.  Team\n2.  League\n3.  Team vs. Team\n4.  Back");
			System.out.print("Enter: ");
			String s1 = kb.nextLine();
			if(s1.equals("1"))
			{
				System.out.println("\nNL:\t\t\tAL:\n1.  Braves\t\t16. Orioles\n2.  Cubs\t\t17. Red Sox\n3.  Diamondbacks\t18. Yankees\n4.  Marlins\t\t19. Bay Rays\n5.  Reds\t\t20. Blue Jays\n6.  Rockies\t\t21. White Sox"
						+ "\n7.  Mets\t\t22. Indians\n8.  Brewers\t\t23. Tigers\n9.  Dodgers\t\t24. Royals\n10. Phillies\t\t25. Twins\n11. Pirates\t\t26. Astros\n12. Padres\t\t27. Angels\n13. Nationals\t\t28. A's\n14. Cardinals\t\t29. Mariners\n15. Giants\t\t30. Rangers");
				System.out.print("Enter: ");
				regular.printTeamSchedule(Integer.parseInt(kb.nextLine()), mlb);
				System.out.println("Execution complete");
			}
			else if(s1.equals("2"))
			{
				System.out.println("\n1.  National\n2.  American\n3.  Major League");
				System.out.print("Enter: ");
				String l = kb.nextLine();
				regular.printLeagueSchedule(l);
				System.out.println("Execution complete");
			}
			else if(s1.equals("3"))
			{
				System.out.println("\n1.  National\n2.  American");
				System.out.print("Enter: ");
				String s2 = kb.nextLine();
				if(s2.equals("1"))
				{
					System.out.println("\n1.  Braves\n2.  Cubs\n3.  Diamondbacks\n4.  Marlins\n5.  Reds\n6.  Rockies"
							+ "\n7.  Mets\n8.  Brewers\n9.  Dodgers\n10. Phillies\n11. Pirates\n12. Padres\n13. Nationals\n14. Cardinals\n15. Giants");
					System.out.print("Enter 1: ");
					int teamnum1 = Integer.parseInt(kb.nextLine());
					System.out.print("Enter 2: ");
					int teamnum2 = Integer.parseInt(kb.nextLine());
					if((teamnum1>0&&teamnum1<16)&&(teamnum2>0&&teamnum2<16))
					{
						regular.printTeamVTeam(0, teamnum1, teamnum2, mlb);
					}
						
				}
				else if(s2.equals("2"))
				{
					System.out.println("\n1.  Orioles\n2.  Red Sox\n3.  Yankees\n4.  Bay Rays\n5.  Blue Jays\n6.  White Sox"
							+ "\n7.  Indians\n8.  Tigers\n9.  Royals\n10. Twins\n11. Astros\n12. Angels\n13. A's\n14. Mariners\n15. Rangers");
					System.out.print("Enter 1: ");
					int teamnum1 = Integer.parseInt(kb.nextLine());
					System.out.print("Enter 2: ");
					int teamnum2 = Integer.parseInt(kb.nextLine());
					if((teamnum1>0&&teamnum1<16)&&(teamnum2>0&&teamnum2<16))
					{
						regular.printTeamVTeam(1, teamnum1, teamnum2, mlb);
					}
						
				}
				System.out.println("Execution complete");
			}
			else if(s1.equals("4"))
			{
				mainMenu(mlb, regular, board);
			}
			mainMenu(mlb, regular, board);
		}
		else if(s.equals("2"))
		{
			System.out.println("\n1.  Game	\n2.  Days\n3.  Season\n4.  Back");
			System.out.print("Enter: ");
			String s1 = kb.nextLine();
			if(s1.equals("1"))
			{
				regular.playGame(0);
			}
			else if(s1.equals("2"))
			{
				System.out.println(91-(regular.day-(regular.day/4))+" playable days left in this season");
				System.out.print("Enter number of days: ");
				int num = Integer.parseInt(kb.nextLine());
				
				if(num>0)
					regular.playGame(num);
			}
			else if(s1.equals("3"))
			{
				regular.playGame(91 -(regular.day-(regular.day/4)));
			}
			else if(s1.equals("4"))
			{
				mainMenu(mlb, regular, board);
			}
			mainMenu(mlb, regular, board);
		}
		else if(s.equals("3"))
		{
			System.out.println("\n1.  National League\n2.  American League\n3.  MLB\n4.  Back");
			System.out.print("Enter: ");
			String s1 = kb.nextLine();
			if(s1.equals("1"))
			{
				board.printStandings(0, 15);
				System.out.println("Execution complete");
			}
			else if(s1.equals("2"))
			{
				board.printStandings(15, 30);
				System.out.println("Execution complete");
			}
			else if(s1.equals("3"))
			{
				board.printStandings(0, 30);
				System.out.println("Execution complete");
			}
			else if(s1.equals("4"))
			{
				mainMenu(mlb, regular, board);
			}
			mainMenu(mlb, regular, board);
		}
		else if(s.equals("4"))
		{
			System.out.println("\n1.  Batters\n2.  Pitchers\n3.  Back");
			System.out.print("Enter: ");
			String s1 = kb.nextLine();
			
			if(s1.equals("3"))
			{
				mainMenu(mlb, regular, board);
			}
			System.out.println("\n1.  National League\n2.  American League\n3.  MLB");
			System.out.print("Enter: ");
			String s2 = kb.nextLine();
			if(s1.equals("1"))
			{
				System.out.println("\n1.  AB\n2.  H\n3.  2B\n4.  3B\n5.  HR\n6.  R\n7.  RBI\n8.  BB\n9.  K\n10. AVG");
				System.out.print("Enter: ");
				int i = Integer.parseInt(kb.nextLine());
				
				if(s2.equals("1"))
				{
					String filename = "";
					if(i==1)
						filename = "NL AB Rankings";
					else if(i==2)
						filename = "NL H Rankings";
					else if(i==3)
						filename = "NL 2B Rankings";
					else if(i==4)
						filename = "NL 3B Rankings";
					else if(i==5)
						filename = "NL HR Rankings";
					else if(i==6)
						filename = "NL R Rankings";
					else if(i==7)
						filename = "NL RBI Rankings";
					else if(i==8)
						filename = "NL BB Rankings";
					else if(i==9)
						filename = "NL K Rankings";
					else if(i==10)
						filename = "NL AVG Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("National League Rankings");
					board.printBatterLeagueLeaders(0, 135, i, out);
					out.close();
				}
					
				else if(s2.equals("2"))
				{
					String filename = "";
					if(i==1)
						filename = "AL AB Rankings";
					else if(i==2)
						filename = "AL H Rankings";
					else if(i==3)
						filename = "AL 2B Rankings";
					else if(i==4)
						filename = "AL 3B Rankings";
					else if(i==5)
						filename = "AL HR Rankings";
					else if(i==6)
						filename = "AL R Rankings";
					else if(i==7)
						filename = "AL RBI Rankings";
					else if(i==8)
						filename = "AL BB Rankings";
					else if(i==9)
						filename = "AL K Rankings";
					else if(i==10)
						filename = "AL AVG Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("American League Rankings");
					board.printBatterLeagueLeaders(135, 270, i, out);
					out.close();
				}
					
				else if(s2.equals("3"))
				{
					String filename = "";
					if(i==1)
						filename = "MLB AB Rankings";
					else if(i==2)
						filename = "MLB H Rankings";
					else if(i==3)
						filename = "MLB 2B Rankings";
					else if(i==4)
						filename = "MLB 3B Rankings";
					else if(i==5)
						filename = "MLB HR Rankings";
					else if(i==6)
						filename = "MLB R Rankings";
					else if(i==7)
						filename = "MLB RBI Rankings";
					else if(i==8)
						filename = "MLB BB Rankings";
					else if(i==9)
						filename = "MLB K Rankings";
					else if(i==10)
						filename = "MLB AVG Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("MLB Rankings");
					board.printBatterLeagueLeaders(0, 270, i, out);
					out.close();
				}
				System.out.println("Execution Complete");
			}
			else if(s1.equals("2"))
			{
				System.out.println("\n1.  IP\n2.  pH\n3.  p2B\n4.  p3B\n5.  pHR\n6.  pR\n7.  ERA\n8.  pBB\n9.  pK");
				System.out.print("Enter: ");
				int i = Integer.parseInt(kb.nextLine());
				if(s2.equals("1"))
				{
					String filename = "";
					if(i==1)
						filename = "NL IP Rankings";
					else if(i==2)
						filename = "NL pH Rankings";
					else if(i==3)
						filename = "NL p2B Rankings";
					else if(i==4)
						filename = "NL p3B Rankings";
					else if(i==5)
						filename = "NL pHR Rankings";
					else if(i==6)
						filename = "NL pR Rankings";
					else if(i==7)
						filename = "NL ERA Rankings";
					else if(i==8)
						filename = "NL pBB Rankings";
					else if(i==9)
						filename = "NL pK Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("National League Rankings");
					board.printPitcherLeagueLeaders(0, 75, i, out);
					out.close();
				}
					
				else if(s2.equals("2"))
				{
					String filename = "";
					if(i==1)
						filename = "AL IP Rankings";
					else if(i==2)
						filename = "AL pH Rankings";
					else if(i==3)
						filename = "AL p2B Rankings";
					else if(i==4)
						filename = "AL p3B Rankings";
					else if(i==5)
						filename = "AL pHR Rankings";
					else if(i==6)
						filename = "AL pR Rankings";
					else if(i==7)
						filename = "AL ERA Rankings";
					else if(i==8)
						filename = "AL pBB Rankings";
					else if(i==9)
						filename = "AL pK Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("American League Rankings");
					board.printPitcherLeagueLeaders(75, 150, i, out);
					out.close();
				}
					
				else if(s2.equals("3"))
				{
					String filename = "";
					if(i==1)
						filename = "MLB IP Rankings";
					else if(i==2)
						filename = "MLB pH Rankings";
					else if(i==3)
						filename = "MLB p2B Rankings";
					else if(i==4)
						filename = "MLB p3B Rankings";
					else if(i==5)
						filename = "MLB pHR Rankings";
					else if(i==6)
						filename = "MLB pR Rankings";
					else if(i==7)
						filename = "MLB ERA Rankings";
					else if(i==8)
						filename = "MLB pBB Rankings";
					else if(i==9)
						filename = "MLB pK Rankings";
					
					PrintWriter out = new PrintWriter(new File(filename+".txt"));
					out.println("MLB Rankings");
					board.printPitcherLeagueLeaders(0, 150, i, out);
					out.close();
				}
					System.out.println("Execution complete");
			}
					
			//board.printBatterLeagueLeaders(0, 270);//Category.peHR
			mainMenu(mlb, regular, board);
		}
		else if(s.equals("5"))
		{
			System.out.println("Exit");
			System.exit(0);
		}
		
		else
		{
			System.out.println("Invalid input. Please try again.");
			mainMenu(mlb, regular, board);
		}
		kb.close();
	}*/
	
}
