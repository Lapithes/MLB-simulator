import java.util.*;
import java.io.*;
public class PostSeason {//use getStandingsArray to import standings
	
	Leaderboard board;
	MLB mlb;
	NLT nlt;
	ALT alt;
	
	//Roundrobin class in the future
	
	
	public PostSeason(MLB m, Leaderboard b)
	{
		mlb = m;
		board = b;
		nlt = new NLT(mlb, board.getStandingsArray(), "n");
		alt = new ALT(mlb, board.getStandingsArray(), "a");
		
	}
	
	public void runNLT(Scanner kb) throws IOException
	{
		nlt.play(kb);
	}
	public void runALT(Scanner kb) throws IOException
	{
		alt.play(kb);
	}
	//public void runRoundRobin()

}
