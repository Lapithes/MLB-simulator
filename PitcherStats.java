import java.util.*;
public class PitcherStats extends Stats {
	
	double IP;
	//double ER;
	//double ERA = ER/IP;
	double pH;
	int ptwoB;
	int pthreeB;
	int pHR;
	double pR;
	int pBB;
	int pK;
	
	double permIP;
	double permpH;
	int permptwoB;
	int permpthreeB;
	int permpHR;
	double permpR;
	int permpBB;
	int permpK;
	
	int wins;
	int losses;
	//String WL = wins+"-"+loss;
			
	//double AVG = H/AB;
	
	public PitcherStats()
	{
		super();
		POS = "SP";
		IP = 0;
		pH = 0;
		ptwoB = 0;
		pthreeB = 0;
		pHR = 0;
		pR = 0;
		pBB = 0;
		pK = 0;
		wins = 0;
		losses = 0;
		
		permIP = 0;
		permpH = 0;
		permptwoB = 0;
		permpthreeB = 0;
		permpHR = 0;
		permpR = 0;
		//ERA
		permpBB = 0;
		permpK = 0;
		//ER = 0;
	}
	
	public void updateP()
	{
		permIP += IP;
		permpH += pH;
		permptwoB += ptwoB;
		permpthreeB += pthreeB;
		permpHR += pHR;
		permpR += pR;
		permpBB += pBB;
		permpK += pK;
	}
	
	public void reset()
	{
		super.reset();
		IP = 0;
		pH = 0;
		ptwoB = 0;
		pthreeB = 0;
		pHR = 0;
		pR = 0;
		pBB = 0;
		pK = 0;
		//wins = 0;
		//losses = 0;
		//ER = 0;
	}

}
